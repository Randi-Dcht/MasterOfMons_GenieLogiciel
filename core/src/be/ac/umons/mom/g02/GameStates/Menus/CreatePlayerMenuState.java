package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.ScrollListChooser;
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
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public CreatePlayerMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
        afterCreationState = LoadingState.class;
    }

    @Override
    public void init() {
        super.init();
        TextBoxMenuItem nameMi = new TextBoxMenuItem(gim, gs, gs.getStringFromId("charName"));
        TextBoxMenuItem levelMi = new NumberTextBoxMenuItem(gim, gs, gs.getStringFromId("charLevel"));
        levelMi.getControl().setText("" + 1);
        levelMi.getControl().setOnTextChanged(() -> {
            if (levelMi.getControl().getText().equals(""))
                return;
            if (Integer.parseInt(levelMi.getControl().getText()) < 1)
                levelMi.getControl().setText("" + 1);
            if (Integer.parseInt(levelMi.getControl().getText()) > 40)
                levelMi.getControl().setText("" + 40);
        });
        ScrollListChooserMenuItem genderMi = new ScrollListChooserMenuItem(gim, gs, gs.getStringFromId("charGender"));
        ScrollListChooserMenuItem typeMi = new ScrollListChooserMenuItem(gim, gs, gs.getStringFromId("charType"));
        ScrollListChooserMenuItem difficultyMi = new ScrollListChooserMenuItem(gim, gs, gs.getStringFromId("difficulty"));
        setMenuItems(new MenuItem[] {
                new TitleMenuItem(gs, gs.getStringFromId("newGame")),
                nameMi,
                levelMi,
                genderMi,
                typeMi,
                difficultyMi,
                new ButtonMenuItem(gim, gs, gs.getStringFromId("newGame"), () -> {
                    if (mustUseMultiplayer)
                        SupervisorMultiPlayer.setPlayerOne(new People(nameMi.getControl().getText(), // Use getSupervisor just to set the instance !
                                characterType, playerGender, difficulty));
                    else
                        Supervisor.getSupervisor().newParty(nameMi.getControl().getText(),
                                characterType, playerGender, difficulty);

                    if (! levelMi.getControl().getText().equals(""))
                        for (int i = 0; i < Integer.parseInt(levelMi.getControl().getText()); i++)
                            Supervisor.getPeople().upLevel();
                    Supervisor.setGraphic(gs);
                    GameState g = gsm.setState(afterCreationState);
                    if (afterCreationState.equals(LoadingState.class) && afterLoadingState != null)
                        ((LoadingState)g).setAfterLoadingState(afterLoadingState);
                }),
                new ButtonMenuItem(gim, gs, gs.getStringFromId("cancel"), () -> gsm.removeFirstState())
        });
        List<ScrollListChooser.ScrollListItem> slil = new LinkedList<>();
        for (Gender s : Gender.values())
            slil.add(new ScrollListChooser.ScrollListItem(gs.getStringFromId(s.toString()), () -> playerGender = s, slil.isEmpty()));
        genderMi.getControl().setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
        setScrollListProperties(genderMi, slil);
        slil = new LinkedList<>();
        for (Type t : Type.values())
            slil.add(new ScrollListChooser.ScrollListItem(gs.getStringFromId(t.toString()), () -> characterType = t, slil.isEmpty()));
        setScrollListProperties(typeMi, slil);
        slil = new LinkedList<>();
        for (Difficulty d : Difficulty.values())
            slil.add(new ScrollListChooser.ScrollListItem(gs.getStringFromId(d.toString()), () -> difficulty = d, slil.isEmpty()));
        setScrollListProperties(difficultyMi, slil);
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
