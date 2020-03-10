package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the menu that will be showed when the user will select where to put his points (agility, ...)
 */
public class LevelUpMenuState extends MenuState {

    /**
     * The human player.
     */
    Player player;
    /**
     * The number of points that the user must attributes.
     */
    int pointToUse;
    /**
     * Where the points has been attributed.
     */
    int[] pointsAttributed = new int[Characteristics.values().length];
    /**
     * What to do when the points are attributed.
     */
    protected Runnable onPointsAttributed;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public LevelUpMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        transparentBackground = true;
        if (player == null)
            return;
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(String.format(gs.getStringFromId("youAreLevel"), player.getCharacteristics().getLevel()), MenuItemType.Title));
        menuItemList.add(new MenuItem(String.format(gs.getStringFromId("youHavePoints"), pointToUse)));
        MenuItem mi;
        for (Characteristics ch : Characteristics.values()) {
            menuItemList.add(mi = new MenuItem("---", MenuItemType.Button, () -> removePoint(ch)));
            mi.size.x = -1;
            menuItemList.add(mi = new MenuItem("+++", MenuItemType.Button, () -> addPoint(ch), false));
            mi.size.x = -1;
            menuItemList.add(mi = new MenuItem(String.format(gs.getStringFromId(ch.toString()) + " : %d (+%d)", getCharacteristics(ch), pointsAttributed[ch.ordinal()]), MenuItemType.Text, () -> {},false));
            mi.size.x = -2;
        }
        menuItemList.add(new MenuItem(gs.getStringFromId("confirm"), MenuItemType.Button, this::confirm));
        setMenuItems(menuItemList.toArray(new MenuItem[0]), false);
    }

    /**
     * Add a point to the given characteristic.
     * @param ch The characteristic to which a point must be added.
     */
    public void addPoint(Characteristics ch) {
        if (pointToUse > 0) {
            pointToUse--;
            pointsAttributed[ch.ordinal()]++;
            init();
        }
    }

    /**
     * Remove a point to the given characteristic.
     * @param ch The characteristic to which a point must be removed.
     */
    public void removePoint(Characteristics ch) {
        if (pointsAttributed[ch.ordinal()] > 0){
            pointToUse++;
            pointsAttributed[ch.ordinal()]--;
            init();
        }
    }

    /**
     * @param ch The characteristic
     * @return The number that the user has in a given characteristic.
     */
    public int getCharacteristics(Characteristics ch) {
        switch (ch) {
            case Strength:
                return player.getCharacteristics().getStrength();
            case Agility:
                return player.getCharacteristics().getAgility();
            case Defence:
                return player.getCharacteristics().getDefence();
        }
        return -1;
    }

    /**
     * @param player The human player.
     */
    public void setPlayer(Player player) {
        this.player = player;
        pointToUse = ((People)player.getCharacteristics()).getPointLevel();
        init();
    }

    /**
     * Executed when the "Confirm" button is pressed
     */
    public void confirm() {
        SuperviserNormally.getSupervisor().getPeople().updateUpLevel(pointsAttributed[Characteristics.Strength.ordinal()], pointsAttributed[Characteristics.Defence.ordinal()], pointsAttributed[Characteristics.Agility.ordinal()]);
        gsm.removeFirstState();
        if (onPointsAttributed != null)
            onPointsAttributed.run();
    }

    /**
     * @param onPointsAttributed What to do when the points are attributed.
     */
    public void setOnPointsAttributed(Runnable onPointsAttributed) {
        this.onPointsAttributed = onPointsAttributed;
    }

    /**
     * The possible characteristics available in this state.
     */
    public enum Characteristics {
        Strength,
        Agility,
        Defence
    }
}


