package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.SlidingBar;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.LinkedList;
import java.util.List;

public class CreatePlayerMenuState extends MenuState {

    /**
     * The gender of the character.
     */
    Gender playerGender = Gender.values()[0];
    /**
     * The type of the character.
     */
    Type characterType = Type.values()[0];
    /**
     * The chosen difficulty
     */
    Difficulty difficulty = Difficulty.values()[0];
    /**
     * The state to launch when the creation is done.
     */
    protected Class<? extends GameState> afterCreationState;
    /**
     * The state to launch when the loading is done.
     */
    protected Class<? extends GameState> afterLoadingState;

    protected boolean mustUseMultiplayer = false;

    /**
     * @param gs The game's graphical settings.
     */
    public CreatePlayerMenuState(GraphicalSettings gs) {
        super(gs);
        afterCreationState = LoadingState.class;
    }

    @Override
    public void init() {
        super.init();
        TextBoxMenuItem nameMi = new TextBoxMenuItem(gim, gs, GraphicalSettings.getStringFromId("charName"));
        TextBoxMenuItem levelMi = new NumberTextBoxMenuItem(gim, gs, GraphicalSettings.getStringFromId("charLevel"));
        SlidingBarMenuItem levelSBMI = new SlidingBarMenuItem(gim, gs,"");
        levelMi.getControl().setText("" + 1);
        levelMi.getControl().setOnTextChanged(() -> {
            if (levelMi.getControl().getText().equals(""))
                return;
            int val = Integer.parseInt(levelMi.getControl().getText());
            if (val < 1)
                levelMi.getControl().setText("" + 1);
            if (val > 40)
                levelMi.getControl().setText("" + 40);
            levelSBMI.getControl().setActualValue(val);
        });
        levelSBMI.getControl().setMinValue(1);
        levelSBMI.getControl().setMaxValue(40);
        levelSBMI.getControl().setOnValueChanged(() -> levelMi.getControl().setText("" + levelSBMI.getControl().getActualValue()));
        ScrollListChooserMenuItem genderMi = new ScrollListChooserMenuItem(gim, gs, GraphicalSettings.getStringFromId("charGender"));
        ScrollListChooserMenuItem typeMi = new ScrollListChooserMenuItem(gim, gs, GraphicalSettings.getStringFromId("charType"));
        ScrollListChooserMenuItem difficultyMi = new ScrollListChooserMenuItem(gim, gs, GraphicalSettings.getStringFromId("difficulty"));
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, GraphicalSettings.getStringFromId("newGame")),
                nameMi,
                levelMi,
                levelSBMI,
                genderMi,
                typeMi,
                difficultyMi,
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("newGame"), () -> initGame(nameMi.getControl().getText(), levelMi.getControl().getText())),
                new ButtonMenuItem(gim, gs, GraphicalSettings.getStringFromId("cancel"), () -> gsm.removeFirstState())
        });
        List<ScrollListChooser.ScrollListItem> slil = new LinkedList<>();
        for (Gender s : Gender.values())
            slil.add(new ScrollListChooser.ScrollListItem(GraphicalSettings.getStringFromId(s.toString()), () -> playerGender = s, slil.isEmpty()));
        genderMi.getControl().setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
        setScrollListProperties(genderMi, slil);
        slil = new LinkedList<>();
        for (Type t : Type.values())
            slil.add(new ScrollListChooser.ScrollListItem(GraphicalSettings.getStringFromId(t.toString()), () -> characterType = t, slil.isEmpty()));
        setScrollListProperties(typeMi, slil);
        slil = new LinkedList<>();
        for (Difficulty d : Difficulty.values())
            slil.add(new ScrollListChooser.ScrollListItem(GraphicalSettings.getStringFromId(d.toString()), () -> difficulty = d, slil.isEmpty()));
        setScrollListProperties(difficultyMi, slil);
    }

    public void initGame(String name, String level) {
        if (mustUseMultiplayer)
            SupervisorMultiPlayer.setPlayerOne(new People(name, // Use getSupervisor just to set the instance !
                    characterType, playerGender, difficulty));
        else
            Supervisor.getSupervisor().newParty(name,
                    characterType, playerGender, difficulty);

        if (! level.equals(""))
            for (int i = 1; i < Integer.parseInt(level); i++)
                Supervisor.getPeople().upLevel();
        Supervisor.setGraphic(gs);
        GameState g = gsm.setState(afterCreationState);
        if (afterCreationState.equals(LoadingState.class) && afterLoadingState != null)
            ((LoadingState)g).setAfterLoadingState(afterLoadingState);
    }

    /**
     * Set the <code>ScrollListChooser</code>'s properties for this state.
     * @param mi The <code>MenuItem</code> containing the <code>ScrollListChooser</code>
     * @param slil The list in which the <code>ScrollListChooser</code> must be added.
     */
    private void setScrollListProperties(ScrollListChooserMenuItem mi, List<ScrollListChooser.ScrollListItem> slil) {
        mi.getControl().setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
        mi.getSize().y = (int)((slil.size()) * (gs.getNormalFont().getLineHeight()) + topMargin);
    }

    /**
     * @param afterCreationState The state to launch when the creation is done.
     */
    public void setAfterCreationState(Class<? extends GameState> afterCreationState) {
        this.afterCreationState = afterCreationState;
    }

    /**
     * @param afterLoadingState The state to launch when the loading is done.
     */
    public void setAfterLoadingState(Class<? extends GameState> afterLoadingState) {
        this.afterLoadingState = afterLoadingState;
    }

    public void setMustUseMultiplayer(boolean mustUseMultiplayer) {
        this.mustUseMultiplayer = mustUseMultiplayer;
    }
}
