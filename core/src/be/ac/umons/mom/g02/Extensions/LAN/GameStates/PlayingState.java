package be.ac.umons.mom.g02.Extensions.LAN.GameStates;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;

/**
 * L'état de jeu du jeu. Il affiche la carte, un joueur ainsi qu'un HUD. Cet état suppose qu'une connexion avec un autre joueur a travers un réseau local a déjà été établi.
 * @author Guillaume Cardoen
 */
public class PlayingState extends be.ac.umons.mom.g02.Extensions.Multiplayer.GameStates.PlayingState {

    private NetworkManager nm;
    private HashMap<String, Character> idCharacterMap; // Used only for MovingPNJ


    /**
     * Crée un nouvel état de jeu.
     *
     * @param gsm Le GameStateManager du jeu.
     * @param gim Le GameInputManager du jeu.
     * @param gs  Les paramètres graphiques à utiliser.
     */
    public PlayingState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        try {
            nm = NetworkManager.getInstance();
        } catch (SocketException e) {
            Gdx.app.error("PlayingState", "The NetworkManager couldn't be retrieved !", e);
            // TODO Go to an error page
        }
        idCharacterMap = new HashMap<>();
        super.init();
        nm.setOnPNJDetected((name, mob, x, y) -> {
            Character c = new Character(gs, mob);
            pnjs.add(c);
            idCharacterMap.put(name, c);
            c.setMapPos(new Point(x, y));
            c.setMapWidth(mapWidth * tileWidth);
            c.setMapHeight(mapHeight * tileHeight);
            c.setTileWidth(tileWidth);
            c.setTileHeight(tileHeight);
        });
        if (nm.isTheServer()) {
            if (nm.mustSendPNJPos()) {
                sendPNJsPositions();
            } else
                nm.setOnGetPNJPos(this::sendPNJsPositions);
        } else
            nm.askPNJsPositions();
        nm.setOnPositionDetected(this::setSecondPlayerPosition);
    }

    @Override
    public void initMap(String mapPath) {
        super.initMap(mapPath);
        if (nm.isTheServer()) {
            for (Character pnj : pnjs) {
                if (! (pnj.getCharacteristics() instanceof MovingPNJ))
                    idCharacterMap.put(pnj.getCharacteristics().getName(), pnj);
            }
        }
    }

    @Override
    public void handleInput() {
        super.handleInput();
        nm.sendPlayerPosition(player);
    }

    @Override
    public void initPNJsPositions() {
        if (nm.isTheServer()) {
            super.initPNJsPositions();
        }
    }

    public void sendPNJsPositions() {
        for (String name : idCharacterMap.keySet()) {
            try {
                nm.sendPNJInformation(idCharacterMap.get(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
