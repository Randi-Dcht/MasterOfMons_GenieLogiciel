package be.ac.umons.mom.g02.Objects;

import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Languages;

import java.io.Serializable;

/**
 * Class representing the different options/settings available in the games.
 */
public class Settings implements Serializable {
    /**
     * The resolution of the game.
     */
    private int gameResolutionWidth = 1920, gameResolutionHeight = 1080;
    /**
     * The maximum number of automatic saves to save in the folder.
     */
    private int maximumAutomaticSaves = 9;

    /**
     * Game's language
     */
    private Languages language = Languages.English;
    /**
     * The foreground's color (font color, ...)
     */
    private String foregroundColor = "FFFFFF";
    /**
     * The background's color
     */
    private String backgroundColor = "161616";
    /**
     * The semi-transparent background color
     */
    private String transparentBackgroundColor = "161616F2";
    /**
     * The background's color of a control
     */
    private String controlBackgroundColor = "070707";
    /**
     * The semi-transparent background's color of a control
     */
    private String controlTransparentBackgroundColor = "070707AA";
    /**
     * The control's color when it's selected
     */
    private String controlSelectedColor = "4527A0";
    /**
     * The color of the attack's circle.
     */
    private String attackRangeColor = "FF212180";
    /**
     * The color of the attack's circle when recovering
     */
    private String recoveringAttackRangeColor = "63636380";
    /**
     * The life bar's color
     */
    private String lifeBarColor = "D50000AF";
    /**
     * The energy bar's color
     */
    private String energyBarColor = "689F38AF";
    /**
     * The experience bar's color
     */
    private String experienceBarColor = "01579BAF";
    /**
     * The path of the last save.
     */
    private String lastSavePath = null;

    /**
     * @return The resolution's height
     */
    public int getGameResolutionHeight() {
        return gameResolutionHeight;
    }
    /**
     * Set the resolution's height
     * @param gameResolutionHeight The resolution's height
     */
    public void setGameResolutionHeight(int gameResolutionHeight) {
        this.gameResolutionHeight = gameResolutionHeight;
    }

    /**
     * @return The resolution's width
     */
    public int getGameResolutionWidth() {
        return gameResolutionWidth;
    }

    /**
     * Set the resolution's width
     * @param gameResolutionWidth The resolution's width
     */
    public void setGameResolutionWidth(int gameResolutionWidth) {
        this.gameResolutionWidth = gameResolutionWidth;
    }
    /**
     * @return The maximum number of saves to save in the folder.
     */
    public int getMaximumAutomaticSaves() {
        return maximumAutomaticSaves;
    }

    /**
     * Set the maximum number of saves to save in the folder.
     * @param maximumAutomaticSaves The maximum number of saves to save in the folder.
     */
    public void setMaximumAutomaticSaves(int maximumAutomaticSaves) {
        this.maximumAutomaticSaves = maximumAutomaticSaves;
    }

    /**
     * @return The game's language
     */
    public Languages getLanguage() {
        return language;
    }

    /**
     * @param language The game's language
     */
    public void setLanguage(Languages language) {
        this.language = language;
    }

    /**
     * @return The foreground's color (font color, ...)
     */
    public String getForegroundColor() {
        return foregroundColor;
    }

    /**
     * @param foregroundColor The foreground's color (font color, ...)
     */
    public void setForegroundColor(String foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
    /**
     * @return The background's color
     */
    public String getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor The background's color
     */
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    /**
     * @return The semi-transparent background color
     */
    public String getTransparentBackgroundColor() {
        return transparentBackgroundColor;
    }

    /**
     * @param transparentBackgroundColor The semi-transparent background color
     */
    public void setTransparentBackgroundColor(String transparentBackgroundColor) {
        this.transparentBackgroundColor = transparentBackgroundColor;
    }
    /**
     * @return The background's color of a control
     */
    public String getControlBackgroundColor() {
        return controlBackgroundColor;
    }

    /**
     * @param controlBackgroundColor The background's color of a control
     */
    public void setControlBackgroundColor(String controlBackgroundColor) {
        this.controlBackgroundColor = controlBackgroundColor;
    }
    /**
     * @return The semi-transparent background's color of a control
     */
    public String getControlTransparentBackgroundColor() {
        return controlTransparentBackgroundColor;
    }

    /**
     * @param controlTransparentBackgroundColor The semi-transparent background's color of a control
     */
    public void setControlTransparentBackgroundColor(String controlTransparentBackgroundColor) {
        this.controlTransparentBackgroundColor = controlTransparentBackgroundColor;
    }
    /**
     * @return The control's color when it's selected
     */
    public String getControlSelectedColor() {
        return controlSelectedColor;
    }

    /**
     * @param controlSelectedColor The control's color when it's selected
     */
    public void setControlSelectedColor(String controlSelectedColor) {
        this.controlSelectedColor = controlSelectedColor;
    }
    /**
     * @return The color of the attack's circle
     */
    public String getAttackRangeColor() {
        return attackRangeColor;
    }

    /**
     * @param attackRangeColor The color of the attack's circle
     */
    public void setAttackRangeColor(String attackRangeColor) {
        this.attackRangeColor = attackRangeColor;
    }
    /**
     * @return The color of the attack's circle when recovering
     */
    public String getRecoveringAttackRangeColor() {
        return recoveringAttackRangeColor;
    }

    /**
     * @param recoveringAttackRangeColor The color of the attack's circle when recovering
     */
    public void setRecoveringAttackRangeColor(String recoveringAttackRangeColor) {
        this.recoveringAttackRangeColor = recoveringAttackRangeColor;
    }
    /**
     * @return The energy bar's color
     */
    public String getEnergyBarColor() {
        return energyBarColor;
    }

    /**
     * @param energyBarColor The energy bar's color
     */
    public void setEnergyBarColor(String energyBarColor) {
        this.energyBarColor = energyBarColor;
    }
    /**
     * @return The experience bar's color
     */
    public String getExperienceBarColor() {
        return experienceBarColor;
    }

    /**
     * @param experienceBarColor The experience bar's color
     */
    public void setExperienceBarColor(String experienceBarColor) {
        this.experienceBarColor = experienceBarColor;
    }
    /**
     * @return The life bar's color
     */
    public String getLifeBarColor() {
        return lifeBarColor;
    }

    /**
     * @param lifeBarColor The life bar's color
     */
    public void setLifeBarColor(String lifeBarColor) {
        this.lifeBarColor = lifeBarColor;
    }

    /**
     * @return The path of the last save.
     */
    public String getLastSavePath() {
        return lastSavePath;
    }

    /**
     * @param lastSavePath The path of the last save.
     */
    public void setLastSavePath(String lastSavePath) {
        this.lastSavePath = lastSavePath;
    }
}
