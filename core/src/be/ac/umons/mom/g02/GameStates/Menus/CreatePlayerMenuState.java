package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GameStates.GameState;
import be.ac.umons.mom.g02.GameStates.LoadingState;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.ScrollListChooser;
import be.ac.umons.mom.g02.GraphicalObjects.Controls.TextBox;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

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
        MenuItem nameMi = new MenuItem(gs.getStringFromId("charName"), MenuItemType.TextBox);
        MenuItem genderMi = new MenuItem(gs.getStringFromId("charGender"), MenuItemType.ScrollListChooser);
        MenuItem typeMi = new MenuItem(gs.getStringFromId("charType"), MenuItemType.ScrollListChooser);
        MenuItem difficultyMi = new MenuItem(gs.getStringFromId("difficulty"), MenuItemType.ScrollListChooser);
        setMenuItems(new MenuItem[] {
                new MenuItem(gs.getStringFromId("newGame"), MenuItemType.Title),
                nameMi,
                genderMi,
                typeMi,
                difficultyMi,
                new MenuItem(gs.getStringFromId("newGame"), MenuItemType.Button, () -> {
                    SuperviserNormally.getSupervisor().newParty(((TextBox)nameMi.control).getText(),
                            characterType, gs, playerGender, difficulty);
                    GameState gs = gsm.setState(afterCreationState);
                    if (afterCreationState.equals(LoadingState.class) && afterLoadingState != null)
                        ((LoadingState)gs).setAfterLoadingState(afterLoadingState);
                })
        });
        List<ScrollListChooser.ScrollListItem> slil = new LinkedList<>();
        for (Gender s : Gender.values())
            slil.add(new ScrollListChooser.ScrollListItem(gs.getStringFromId(s.toString()), () -> playerGender = s, slil.isEmpty()));
        ((ScrollListChooser)genderMi.control).setScrollListItems(slil.toArray(new ScrollListChooser.ScrollListItem[0]));
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
