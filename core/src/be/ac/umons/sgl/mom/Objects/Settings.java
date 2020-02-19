package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Languages;

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

    private Difficulty difficulty = Difficulty.Medium;

    private String backgroundColor = "212121";
    private String transparentBackgroundColor = "21212180";

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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getTransparentBackgroundColor() {
        return transparentBackgroundColor;
    }

    public void setTransparentBackgroundColor(String transparentBackgroundColor) {
        this.transparentBackgroundColor = transparentBackgroundColor;
    }
}
