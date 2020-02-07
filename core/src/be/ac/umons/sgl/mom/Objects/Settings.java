package be.ac.umons.sgl.mom.Objects;

import java.io.Serializable;

/**
 * Class representing the different options/settings available in the games.
 */
public class Settings implements Serializable {
    private int gameResolutionWidth = 1920, gameResolutionHeight = 1080;

    public int getGameResolutionHeight() {
        return gameResolutionHeight;
    }

    public void setGameResolutionHeight(int gameResolutionHeight) {
        this.gameResolutionHeight = gameResolutionHeight;
    }

    public int getGameResolutionWidth() {
        return gameResolutionWidth;
    }

    public void setGameResolutionWidth(int gameResolutionWidth) {
        this.gameResolutionWidth = gameResolutionWidth;
    }
}
