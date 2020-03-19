package be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates;

import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;

import java.awt.*;

/**
 * The state of play of the game. It displays the map, two players and a HUD.
 */
public class PlayingState extends be.ac.umons.mom.g02.GameStates.PlayingState {

    protected Player playerTwo;
    protected boolean mustDrawSecondPlayer = true;

    /**
     * Create a new game state.
     *
     * @param gsm The GameStateManager of the game.
     * @param gim The GameInputManager of the game.
     * @param gs  The graphic parameters to use.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    public PlayingState() { }

    @Override
    public void init() {
        super.init();
        if (playerTwo == null) {
            playerTwo = new Player(gs);
            playerTwo.setMapPos(new Point(player.getPosX(), player.getPosY()));
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (mustDrawSecondPlayer)
            playerTwo.draw(sb, playerTwo.getPosX() - (int)cam.position.x + MasterOfMonsGame.WIDTH / 2, playerTwo.getPosY() - (int)cam.position.y + MasterOfMonsGame.HEIGHT / 2, tileWidth, 2 * tileHeight);

    }

    public void setSecondPlayerCharacteristics(People secondPlayerCharacteristics) {
        playerTwo.setCharacteristics(secondPlayerCharacteristics);
    }

    public void setSecondPlayerPosition(Point mapPos) {
        if (playerTwo == null)
            playerTwo = new Player(gs);
        playerTwo.setMapPos(mapPos);
    }


}
