package be.ac.umons.sgl.mom.GameStates;

import be.ac.umons.sgl.mom.Enums.KeyStatus;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.GraphicalObjects.Character;
import be.ac.umons.sgl.mom.GraphicalObjects.Controls.Button;
import be.ac.umons.sgl.mom.GraphicalObjects.ProgressBar;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class CombatState extends GameState {

    Character player1, player2;
    SpriteBatch sb;
    ShapeRenderer sr;
    ProgressBar lifePlayer1, lifePlayer2;
    Button attackButton;

    public CombatState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
    }

    @Override
    public void init() {
        super.init();
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
        lifePlayer1 = new ProgressBar();
        lifePlayer2 = new ProgressBar();
        attackButton = new Button(gim, gs);
        attackButton.setText(gs.getStringFromId("attack"));
        attackButton.setSelected(true);
        lifePlayer1.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
        lifePlayer2.setForegroundColor(new Color(213f / 255, 0, 0, .8f));
        lifePlayer1.setBackgroundColor(new Color(0x636363AA));
        lifePlayer2.setBackgroundColor(new Color(0x636363AA));
    }

    @Override
    public void update(float dt) {
        handleInput();
        player1.update(dt);
        player2.update(dt);
        attackButton.setSelected(player1.canAttack());
    }

    @Override
    public void draw() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(new Color(0x212121AA));
        sr.rect(0,0, MasterOfMonsGame.WIDTH, MasterOfMonsGame.HEIGHT);
        sr.end();
        sb.begin();
        if (player1 != null)
            sb.draw(player1.getTexture(), MasterOfMonsGame.WIDTH * 1/4, MasterOfMonsGame.HEIGHT / 3);
        if (player2 != null)
            sb.draw(player2.getTexture(), MasterOfMonsGame.WIDTH * 2/3, MasterOfMonsGame.HEIGHT * 2 / 3);
        sb.end();
        attackButton.draw(sb, new Point(MasterOfMonsGame.WIDTH * 1 / 2, MasterOfMonsGame.HEIGHT / 3),
                new Point(MasterOfMonsGame.WIDTH / 3, MasterOfMonsGame.HEIGHT / 8));
        lifePlayer1.draw(MasterOfMonsGame.WIDTH / 10, MasterOfMonsGame.HEIGHT / 6, MasterOfMonsGame.WIDTH / 3, MasterOfMonsGame.HEIGHT / 18);
        lifePlayer2.draw(MasterOfMonsGame.WIDTH / 2, MasterOfMonsGame.HEIGHT * 5 / 6, MasterOfMonsGame.WIDTH / 3, MasterOfMonsGame.HEIGHT / 18);
    }

    @Override
    public void handleInput() {
        attackButton.handleInput();
        if (gim.isKey(Input.Keys.ENTER, KeyStatus.Pressed) && player1.canAttack()) {
            SuperviserNormally.getSupervisor().attackMethod(player1.getCharacteristics(), player2.getCharacteristics());
            player1.setTimeBeforeAttack(player1.getCharacteristics().recovery());
        }
    }

    @Override
    public void dispose() {
        sb.dispose();
        sr.dispose();
        attackButton.dispose();
        lifePlayer1.dispose();
        lifePlayer2.dispose();
    }

    public void setPlayer1(Character player1) {
        this.player1 = player1;
        lifePlayer1.setMaxValue((int)player1.getCharacteristics().lifemax());
    }

    public void setPlayer2(Character player2) {
        this.player2 = player2;
        lifePlayer2.setMaxValue((int)player2.getCharacteristics().lifemax());
    }
}
