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
    protected int pointToUse;
    /**
     * Where the points has been attributed.
     */
    protected int[] pointsAttributed = new int[Characteristics.values().length];
    /**
     * The map linking each characteristics to the <code>TextBox</code> associated
     */
    protected HashMap<Characteristics, TextBoxMenuItem> textBoxCharacteristicsMap;
    /**
     * The map linking each characteristics to the <code>SlidingBar</code> associated
     */
    protected HashMap<Characteristics, SlidingBarMenuItem> slidingBarCharacteristicsMap;
    /**
     * What to do when the points are attributed.
     */
    protected OnPointsAttributedRunnable onPointsAttributed;

    /**
     * The menu item showing the number of points left
     */
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
        slidingBarCharacteristicsMap = new HashMap<>();
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
            textBoxCharacteristicsMap.put(ch, ntmi);
            ntmi.getControl().setText("" + pointsAttributed[ch.ordinal()]);
            ntmi.getControl().setOnTextChanged(() -> onTextChanged(ntmi, ch));

            menuItemList.add(mi = new SlidingBarMenuItem(gim, gs, ""));
            SlidingBarMenuItem sbmi = (SlidingBarMenuItem)mi;
            sbmi.getControl().setMaxValue(pointToUse);
            slidingBarCharacteristicsMap.put(ch, sbmi);
            sbmi.getControl().setOnValueChanged(() -> onValueChanged(sbmi, ch));

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

    /**
     * Add all the remaining points to a characteristics.
     * @param ch The characteristics where all the points must be added
     */
    protected void addAllRemainingPoints(Characteristics ch) {
        pointsAttributed[ch.ordinal()] += pointToUse;
        pointToUse = 0;
        refresh();
    }

    /**
     * Remove all the points of a characteristics.
     * @param ch The characteristics where all the points must be removed
     */
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
            onPointsAttributed.run(pointsAttributed[Characteristics.Strength.ordinal()], pointsAttributed[Characteristics.Defence.ordinal()], pointsAttributed[Characteristics.Agility.ordinal()]);
    }

    /**
     * @param onPointsAttributed What to do when the points are attributed.
     */
    public void setOnPointsAttributed(OnPointsAttributedRunnable onPointsAttributed) {
        this.onPointsAttributed = onPointsAttributed;
    }

    /**
     * What to do when the text of a <code>TextBox</code> changed
     * @param tbmi The <code>MenuItem</code> where the text changed
     * @param ch The characteristics represented by this <code>TextBox</code>
     */
    protected void onTextChanged(TextBoxMenuItem tbmi, Characteristics ch) {
        int[] points = Arrays.copyOf(pointsAttributed, pointsAttributed.length);
        if (tbmi.getControl().getText().equals(""))
            points[ch.ordinal()] = 0;
        else
            points[ch.ordinal()] = Integer.parseInt(tbmi.getControl().getText());
        if (! checkPoints(points))
            tbmi.getControl().setText("" + pointsAttributed[ch.ordinal()]);
    }

    /**
     * What to do when the value of a <code>SlidingBar</code> changed
     * @param sbmi The <code>MenuItem</code> where the value changed
     * @param ch The characteristics represented by this <code>TextBox</code>
     */
    protected void onValueChanged(SlidingBarMenuItem sbmi, Characteristics ch) {
        int[] points = Arrays.copyOf(pointsAttributed, pointsAttributed.length);
        points[ch.ordinal()] = sbmi.getControl().getActualValue();
        if (! checkPoints(points))
            sbmi.getControl().setActualValue(pointsAttributed[ch.ordinal()]);
    }

    /**
     * Check if the points attributed doesn't exceed the point that the user can attribute.
     * Refresh the state if it is.
     * @param points An array of the points attributed to each characteristics
     * @return If the points attributed doesn't exceed the point that the user can attribute
     */
    protected boolean checkPoints(int[] points) {
        int usedPoints = computerUsedPoints(points);
        int availablePoints = player.getPointLevel();
        if (usedPoints - availablePoints <= 0) {
            pointsAttributed = points;
            pointToUse = availablePoints - usedPoints;
            refresh();
            return true;
        } else
            return false;
    }

    /**
     * Computer the attributed points from the array. (Sum of all the int in the array)
     * @param points An array of the points attributed to each characteristics
     * @return The attributed points
     */
    protected int computerUsedPoints(int[] points) {
        int attributedPoints = 0;
        for (int i : points)
            attributedPoints += i;
        return attributedPoints;
    }

    /**
     * Refresh the state by updating all the <code>SlidingBar</code> and <code>TextBox</code>.
     */
    protected void refresh() {
        pointsToUseMi.setHeader(String.format(gs.getStringFromId("youHavePoints"), pointToUse));
        for (Characteristics ch : Characteristics.values()) {
            TextBoxMenuItem tbmi = textBoxCharacteristicsMap.get(ch);
            tbmi.setHeader(String.format(gs.getStringFromId(ch.toString()) + " : %d + ", getCharacteristics(ch)));
            tbmi.getControl().setText("" + pointsAttributed[ch.ordinal()]);

            SlidingBarMenuItem sbmi = slidingBarCharacteristicsMap.get(ch);
            if (sbmi.getControl().getActualValue() != pointsAttributed[ch.ordinal()])
                sbmi.getControl().setActualValue(pointsAttributed[ch.ordinal()]);
            sbmi.getControl().setMaxSlidingValue(pointToUse + pointsAttributed[ch.ordinal()]);
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

    /**
     * Represent a runnable that take 3 int as parameter
     */
    public interface OnPointsAttributedRunnable {
        void run(int strength, int defence, int agility);
    }
}


