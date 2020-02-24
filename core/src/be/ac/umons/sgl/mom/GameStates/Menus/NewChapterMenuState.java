package be.ac.umons.sgl.mom.GameStates.Menus;

import be.ac.umons.sgl.mom.Animations.StringAnimation;
import be.ac.umons.sgl.mom.GameStates.GameState;
import be.ac.umons.sgl.mom.Helpers.StringHelper;
import be.ac.umons.sgl.mom.Managers.AnimationManager;
import be.ac.umons.sgl.mom.Managers.GameInputManager;
import be.ac.umons.sgl.mom.Managers.GameStateManager;
import be.ac.umons.sgl.mom.MasterOfMonsGame;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewChapterMenuState extends GameState {

    /**
     * The name of the new chapter.
     */
    protected String newChapterName = "";
    /**
     * THe text that must be shown on the screen.
     */
    protected String textToShow = "";
    /**
     * Where the text must be drawn.
     */
    protected SpriteBatch sb;
    /**
     * The number of lines the text to show is.
     */
    protected int numberOfLines = 1;

    /**
     * @param gsm The game's state manager
     * @param gim The game's input manager
     * @param gs The game's graphical settings.
     */
    public NewChapterMenuState(GameStateManager gsm, GameInputManager gim, GraphicalSettings gs) {
        super(gsm, gim, gs);
        init();
    }

    /**
     * Part of this code is from https://stackoverflow.com/a/24104427 by "Boris the Spider"
     */
    public void init() {
        sb = new SpriteBatch();
        StringAnimation sa = new StringAnimation(gs.getStringFromId("newChapter"), 1000);
        sa.setRunningAction(() -> {
            textToShow = StringHelper.adaptTextToWidth(gs.getTitleFont(), sa.getActual(), (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin));
            numberOfLines = textToShow.split("\n").length;
        });
        sa.setEndingAction(() -> {
            StringAnimation anim = new StringAnimation(newChapterName, 2000);
            anim.setRunningAction(() -> {
                textToShow = StringHelper.adaptTextToWidth(gs.getTitleFont(), anim.getActual(), (int)(MasterOfMonsGame.WIDTH - 2 * leftMargin));
                numberOfLines = textToShow.split("\n").length;
            });
            anim.setEndingAction(() -> {
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(() -> gsm.removeFirstState(), 2, 1, TimeUnit.SECONDS);
            });
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(() -> AnimationManager.getInstance().addAnAnimation("NewChapterStringAnim2", anim), 5, 1, TimeUnit.SECONDS);

        });
        AnimationManager.getInstance().addAnAnimation("NewChapterStringAnim", sa);
    }

    @Override
    public void update(float dt) {}

    @Override
    public void draw() {
        GlyphLayout gl = new GlyphLayout();
        gl.setText(gs.getTitleFont(), textToShow);
        sb.begin();
        gs.getTitleFont().draw(sb, textToShow, (MasterOfMonsGame.WIDTH - gl.width) / 2, MasterOfMonsGame.HEIGHT / 2 + (numberOfLines * gs.getTitleFont().getLineHeight()) / 2);
        sb.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {
        sb.dispose();
    }

    /**
     * @param newChapterName The new chapter's name.
     */
    public void setNewChapterName(String newChapterName) {
        this.newChapterName = newChapterName;
    }
}
