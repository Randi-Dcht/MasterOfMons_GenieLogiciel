package be.ac.umons.mom.g02.GameStates;

import be.ac.umons.mom.g02.Animations.StringAnimation;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.Managers.AnimationManager;
import be.ac.umons.mom.g02.Managers.GameInputManager;
import be.ac.umons.mom.g02.Managers.GameStateManager;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NewChapterState extends GameState {

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
     * @param gs The game's graphical settings.
     */
    public NewChapterState(GraphicalSettings gs) {
        super(gs);
    }

    /**
     * Part of this code is from https://stackoverflow.com/a/24104427 by "Boris the Spider"
     */
    public void init() {
        sb = new SpriteBatch();
        StringAnimation sa = new StringAnimation(GraphicalSettings.getStringFromId("newChapter"), 1000);
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
                executorService.schedule(() ->
                                Gdx.app.postRunnable(() -> gsm.removeFirstState()),
                        4, TimeUnit.SECONDS);
            });
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> AnimationManager.getInstance().addAnAnimation("NewChapterStringAnim2", anim), 2, TimeUnit.SECONDS);

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
