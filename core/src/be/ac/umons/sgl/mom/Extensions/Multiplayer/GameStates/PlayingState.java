package be.ac.umons.sgl.mom.Extensions.Multiplayer.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Enums.Orientation;
import be.ac.umons.sgl.mom.GraphicalObjects.Player;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * L'état de jeu du jeu. Il affiche la carte, deux joueurs ainsi qu'un HUD.
 */
public class PlayingState extends be.ac.umons.sgl.mom.GameStates.PlayingState {

    private Player playerTwo;

    private int defaultCamXPos;
    private int defaultCamYPos;

    /**
     * Crée un nouvel état de jeu.
     *
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques à utiliser.
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
        int toMove = Math.round(VELOCITY * Gdx.graphics.getDeltaTime());
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
