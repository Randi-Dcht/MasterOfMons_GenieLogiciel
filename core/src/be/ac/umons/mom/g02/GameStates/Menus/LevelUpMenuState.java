package be.ac.umons.mom.g02.GameStates.Menus;

import be.ac.umons.mom.g02.GraphicalObjects.MenuItems.*;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Represent the menu that will be showed when the user will select where to put his points (agility, ...)
 */
public class LevelUpMenuState extends MenuState {

    /**
     * The human player.
     */
    protected People player;
    /**
     * The number of points that the user must attributes.
     */
    int pointToUse;
    /**
     * Where the points has been attributed.
     */
    int[] pointsAttributed = new int[Characteristics.values().length];
    HashMap<TextBoxMenuItem, Characteristics> textBoxCharacteristicsMap;
    /**
     * What to do when the points are attributed.
     */
    protected Runnable onPointsAttributed;

    protected TextMenuItem pointsToUseMi;

    /**
     * @param gs The game's graphical settings.
     */
    public LevelUpMenuState(GraphicalSettings gs) {
        super(gs);
    }

    @Override
    public void init() {
        super.init();
        textBoxCharacteristicsMap = new HashMap<>();
        transparentBackground = true;
        if (player == null)
            return;
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new TitleMenuItem(gs, String.format(gs.getStringFromId("youAreLevel"), player.getLevel())));
        menuItemList.add(pointsToUseMi = new TextMenuItem(gs, String.format(gs.getStringFromId("youHavePoints"), pointToUse)));
        MenuItem mi;
        for (Characteristics ch : Characteristics.values()) {
            menuItemList.add(mi = new NumberTextBoxMenuItem(gim, gs, String.format(gs.getStringFromId(ch.toString()) + " : %d + ", getCharacteristics(ch))));
            mi.getSize().x = -2;
            NumberTextBoxMenuItem ntmi = (NumberTextBoxMenuItem)mi;
            textBoxCharacteristicsMap.put(ntmi, ch);
            ntmi.getControl().setText("" + pointsAttributed[ch.ordinal()]);
            ntmi.getControl().setOnTextChanged(() -> onTextChanged(ntmi));

            menuItemList.add(mi = new ButtonMenuItem(gim, gs,"---", () -> removeAllPoints(ch)));
            mi.getSize().x = -1;
            menuItemList.add(mi = new ButtonMenuItem(gim, gs, "-", () -> removePoint(ch), false));
            mi.getSize().x = -1;
            menuItemList.add(mi = new ButtonMenuItem(gim, gs,"+", () -> addPoint(ch), false));
            mi.getSize().x = -1;
            menuItemList.add(mi = new ButtonMenuItem(gim, gs,"+++", () -> addAllRemainingPoints(ch), false));
            mi.getSize().x = -1;
        }
        menuItemList.add(new ButtonMenuItem(gim, gs, gs.getStringFromId("confirm"), this::confirm));
        setMenuItems(menuItemList.toArray(new MenuItem[0]), false);
    }

    protected void addAllRemainingPoints(Characteristics ch) {
        pointsAttributed[ch.ordinal()] += pointToUse;
        pointToUse = 0;
        refresh();
    }
    protected void removeAllPoints(Characteristics ch) {
        pointToUse += pointsAttributed[ch.ordinal()];
        pointsAttributed[ch.ordinal()] = 0;
        refresh();
    }

    /**
     * Add a point to the given characteristic.
     * @param ch The characteristic to which a point must be added.
     */
    protected void addPoint(Characteristics ch) {
        if (pointToUse > 0) {
            pointToUse--;
            pointsAttributed[ch.ordinal()]++;
            refresh();
        }
    }

    /**
     * Remove a point to the given characteristic.
     * @param ch The characteristic to which a point must be removed.
     */
    protected void removePoint(Characteristics ch) {
        if (pointsAttributed[ch.ordinal()] > 0){
            pointToUse++;
            pointsAttributed[ch.ordinal()]--;
            refresh();
        }
    }

    /**
     * @param ch The characteristic
     * @return The number that the user has in a given characteristic.
     */
    public int getCharacteristics(Characteristics ch) {
        switch (ch) {
            case Strength:
                return player.getStrength();
            case Agility:
                return player.getAgility();
            case Defence:
                return player.getDefence();
        }
        return -1;
    }

    /**
     * @param player The human player.
     */
    public void setPlayer(People player) {
        this.player = player;
        pointToUse = player.getPointLevel();
        if (pointsToUseMi != null) // If null, init not executed yet => refresh useless
            refresh();
    }

    /**
     * Executed when the "Confirm" button is pressed
     */
    public void confirm() {
        Supervisor.getPeople().updateUpLevel(pointsAttributed[Characteristics.Strength.ordinal()], pointsAttributed[Characteristics.Defence.ordinal()], pointsAttributed[Characteristics.Agility.ordinal()]);
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

    protected void onTextChanged(TextBoxMenuItem tbmi) {
        int[] points = Arrays.copyOf(pointsAttributed, pointsAttributed.length);
        Characteristics ch = textBoxCharacteristicsMap.get(tbmi);
        if (tbmi.getControl().getText().equals(""))
            points[ch.ordinal()] = 0;
        else
            points[ch.ordinal()] = Integer.parseInt(tbmi.getControl().getText());
        int usedPoints = computerUsedPoints(points);
        int availablePoints = player.getPointLevel();
        if (usedPoints > availablePoints)
            tbmi.getControl().setText("" + pointsAttributed[ch.ordinal()]);
        else {
            pointsAttributed = points;
            pointToUse = availablePoints - usedPoints;
            refresh();
        }
    }

    protected int computerUsedPoints(int[] points) {
        int attributedPoints = 0;
        for (int i : points)
            attributedPoints += i;
        return attributedPoints;
    }

    protected void refresh() {
        pointsToUseMi.setHeader(String.format(gs.getStringFromId("youHavePoints"), pointToUse));
        for (TextBoxMenuItem tbmi : textBoxCharacteristicsMap.keySet()) {
            Characteristics ch = textBoxCharacteristicsMap.get(tbmi);
            tbmi.setHeader(String.format(gs.getStringFromId(ch.toString()) + " : %d + ", getCharacteristics(ch)));
            tbmi.getControl().setText("" + pointsAttributed[ch.ordinal()]);
        }
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


