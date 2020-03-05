package be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates;

import be.ac.umons.mom.g02.Enums.KeyStatus;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Player;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * The state of play of the game. It displays the map, two players and a HUD.
 */
public class PlayingState extends be.ac.umons.mom.g02.GameStates.PlayingState {

    private Player playerTwo;

    private int defaultCamXPos;
    private int defaultCamYPos;

    /**
     * Create a new game state.
     *
     * @param gsm The GameStateManager of the game.
     * @param gim The GameInputManager of the game.
     * @param gs  The graphic parameters to use.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
        defaultCamXPos = (int)cam.position.x;
        defaultCamYPos = (int)cam.position.y;
    }

    @Override
    public void init() {
        super.init();
        playerTwo = new Player(gs);
        playerTwo.move(player.getPosX(), playerTwo.getPosY());
    }

    @Override
    public void draw() {
        int toMove = (int)Math.round(velocity * Gdx.graphics.getDeltaTime() * tileWidth);
        int toMoveX = 0, toMoveY = 0;
        super.draw();
        if (gim.isKey(Input.Keys.S, KeyStatus.Down)) {
            playerTwo.setOrientation(Orientation.Bottom);
            toMoveY += -toMove;
        }
        if (gim.isKey(Input.Keys.Z, KeyStatus.Down)) {
            playerTwo.setOrientation(Orientation.Top);
            toMoveY += toMove;
        }
        if (gim.isKey(Input.Keys.Q, KeyStatus.Down)) {
            playerTwo.setOrientation(Orientation.Left);
            toMoveX += -toMove;
        }
        if (gim.isKey(Input.Keys.D, KeyStatus.Down)) {
            playerTwo.setOrientation(Orientation.Right);
            toMoveX += toMove;
        }
        playerTwo.move(toMoveX, toMoveY);
        playerTwo.draw(sb, playerTwo.getPosX() - (int)cam.position.x + defaultCamXPos, playerTwo.getPosY() - (int)cam.position.y + defaultCamYPos, tileWidth, tileHeight);

    }


}
