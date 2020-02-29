package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Gender;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.GameStates.LoadingState;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.TextBox;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.util.LinkedList;
import java.util.List;

public class CreatePlayerMenuState extends MenuState {

    Gender playerGender = Gender.values()[0];
    Type characterType = Type.values()[0];
    Difficulty difficulty = Difficulty.values()[0];
    protected Class<? extends GameState> afterCreationState;
    protected Class<? extends GameState> afterLoadingState;

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
        MenuItem nameMi = new MenuItem("Character's name", MenuItemType.TextBox);
        MenuItem genderMi = new MenuItem("Character's gender", MenuItemType.ScrollListChooser);
        MenuItem typeMi = new MenuItem("Character's type", MenuItemType.ScrollListChooser);
        MenuItem difficultyMi = new MenuItem("Difficulty", MenuItemType.ScrollListChooser);
        setMenuItems(new MenuItem[] {
                new MenuItem("Start a new game", MenuItemType.Title),
                nameMi,
                genderMi,
                typeMi,
                difficultyMi,
                new MenuItem("Start the game !", MenuItemType.Button, () -> {
                    SuperviserNormally.getSupervisor().newParty(((TextBox)nameMi.control).getText(),
                            characterType, gs, playerGender, difficulty);
                    GameState gs = gsm.setState(afterCreationState);
                    if (afterCreationState.equals(LoadingState.class) && afterLoadingState != null)
                        ((LoadingState)gs).setAfterLoadingState(afterLoadingState);
                })
        });
        List<ScrollListChooser.ScrollListItem> slil = new LinkedList<>();
        for (Gender s : Gender.values())
            slil.add(new ScrollListChooser.ScrollListItem(s.toString(), () -> playerGender = s, slil.isEmpty()));
        ((ScrollListChooser)genderMi.control).setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
        setScrollListProperties(genderMi, slil);
        slil = new LinkedList<>();
        for (Type t : Type.values())
            slil.add(new ScrollListChooser.ScrollListItem(t.toString(), () -> characterType = t, slil.isEmpty()));
        setScrollListProperties(typeMi, slil);
        slil = new LinkedList<>();
        for (Difficulty d : Difficulty.values())
            slil.add(new ScrollListChooser.ScrollListItem(d.toString(), () -> difficulty = d, slil.isEmpty()));
        setScrollListProperties(difficultyMi, slil);
    }

    /**
     * Set the <code>ScrollListChooser</code> properties for this state.
     * @param mi
     * @param slil
     */
    private void setScrollListProperties(MenuItem mi, List<ScrollListChooser.ScrollListItem> slil) {
        ((ScrollListChooser)mi.control).setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
        mi.size.y = (int)((slil.size() + 1) * (gs.getNormalFont().getLineHeight() + 2 * topMargin) + 2 * topMargin);
    }

    public void setAfterCreationState(Class<? extends GameState> afterCreationState) {
        this.afterCreationState = afterCreationState;
    }

    public void setAfterLoadingState(Class<? extends GameState> afterLoadingState) {
        this.afterLoadingState = afterLoadingState;
    }
}
