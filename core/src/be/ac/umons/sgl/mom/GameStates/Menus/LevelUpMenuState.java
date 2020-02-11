package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.GraphicalObjects.Player;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent the menu that will be showed when the user will select where to put his points (agility, ...)
 */
public class LevelUpMenuState extends MenuState {

    Player player;
    int pointToUse = 3;
    int[] pointsAttributed = new int[Characteristics.values().length];

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
        topMargin = .1;
        transparentBackground = true;
        if (player == null)
            return;
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem(String.format("You are now level %d", player.getCharacteristics().getLevel()), MenuItemType.Title));
        menuItemList.add(new MenuItem(String.format("You have %d points", pointToUse)));
        for (Characteristics ch : Characteristics.values()) {
            menuItemList.add(new MenuItem("---", MenuItemType.Button, () -> removePoint(ch)));
            menuItemList.add(new MenuItem(String.format("Strength : %d (+%d)", getCharacteristics(ch), pointsAttributed[ch.ordinal()]), MenuItemType.Text, () -> {},false));
            menuItemList.add(new MenuItem("+++", MenuItemType.Button, () -> addPoint(ch), false));
        }
        menuItemList.add(new MenuItem("Confirm", MenuItemType.Button, this::confirm));
        setMenuItems(menuItemList.toArray(new MenuItem[0]), false);
    }

    public void addPoint(Characteristics ch) {
        if (pointToUse > 0) {
            pointToUse--;
            pointsAttributed[ch.ordinal()]++;
            init();
        }
    }
    public void removePoint(Characteristics ch) {
        if (pointsAttributed[ch.ordinal()] > 0){
            pointToUse++;
            pointsAttributed[ch.ordinal()]--;
            init();
        }
    }

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

    public void setPlayer(Player player) {
        this.player = player;
        init();
    }

    public void confirm() {
        //TODO
    }

    public enum Characteristics {
        Strength,
        Agility,
        Defence
    }
}


