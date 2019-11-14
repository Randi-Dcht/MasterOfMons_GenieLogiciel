package be.ac.umons.sgl.mom.Extensions.Multiplayer.GameStates;

import be.ac.umons.sgl.mom.GraphicalObjects.Character;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;

public class PlayingState extends be.ac.umons.sgl.mom.GameStates.PlayingState {

    private Character playerTwo;

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
        playerTwo = new Character(gs);
    }

    @Override
    public void draw() {
        super.draw();
        playerTwo.draw(sb, playerTwo.getPosX() - (int)cam.position.x + defaultCamXPos, playerTwo.getPosY() - (int)cam.position.y + defaultCamYPos, tileWidth, tileHeight);
    }
}
