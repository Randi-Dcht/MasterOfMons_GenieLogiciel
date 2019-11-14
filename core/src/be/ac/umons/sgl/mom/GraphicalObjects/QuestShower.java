package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Quest;
import be.ac.umons.sgl.mom.Objects.GoalsQuest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

/**
 * Support pour montrer les quêtes actuelles du jeu ainsi que leur avancement.
 */
public class QuestShower {
    /**
     * La marge entre le texte et le rectangle d'arrière-plan.
     */
    public static final int TEXT_AND_RECTANGLE_MARGIN = 7;
    /**
     * La marge verticale entre les quêtes.
     */
    public static final int BETWEEN_QUEST_MARGIN_HEIGHT = 7;
    /**
     * La marge de décalage horizontale entre une quête et sa sous-quête.
     */
    public static final int BETWEEN_QUEST_MARGIN_WIDTH = 7;
    /**
     * La marge entre le cercle et le texte.
     */
    public static final int BETWEEN_CIRCLE_AND_TEXT_MARGIN = 7;

    /**
     * La couleur du cercle lorsque la quête est active.
     */
    protected static final Color ACTIVATED_QUEST_CIRCLE_COLOR = Color.valueOf("FF5722");
    /**
     * La couleur du cercle lorsque la quête est finie.
     */
    protected static final Color FINISHED_QUEST_CIRCLE_COLOR = Color.valueOf("00C853");
    /**
     * La couleur du cercle lorsque la quête est inactive.
     */
    protected static final Color UNACTIVATED_QUEST_CIRCLE_COLOR = Color.valueOf("616161");

    /**
     * Où le support doit être dessiné.
     */
    protected Batch batch;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    private ShapeRenderer sr;
    /**
     * Les paramètres graphiques du jeu.
     */
    protected GraphicalSettings gs;

    /**
     * La position du support.
     */
    protected int x, y;
    /**
     * La taille du support durant une animation.
     */
    protected float duringAnimationQuestShowerHeight, duringAnimationQuestShowerWidth;
    /**
     * L'opacité du texte durant une animation.
     */
    protected double duringAnimationTextOpacity;
    /**
     * Le support est-il en train d'être animé ?
     */
    protected boolean isBeingAnimated;
    /**
     * La taille du support.
     */
    protected int questShowerWidth, questShowerHeight;
    /**
     * Le rayon d'un cercle d'avancement de quête.
     */
    protected int circleRadius;
    /**
     * La quête principale à montrer.
     */
    private Quest questToShow;


    /**
     * Crée un nouveau support de quête.
     * @param gs Les paramètres graphiques du jeu.
     * @param batch Où le support doit être dessiné.
     * @param x La position horizontale.
     * @param y La position verticale.
     */
    public QuestShower(GraphicalSettings gs, Batch batch, int x, int y) {
        this.gs = gs;
        this.batch = batch;
        this.x = x;
        this.y = y;
        init();
    }

    /**
     * Dessine le support.
     * Code inspiré de https://gamedev.stackexchange.com/a/115483 par NuttyBunny
     * et https://stackoverflow.com/a/14721570 par UVM
     */
    public void draw() {
        if (questToShow == null)
            return;

        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(21f / 255, 21f / 255, 21f / 255, .5f);
        if (isBeingAnimated)
            sr.rect(x - TEXT_AND_RECTANGLE_MARGIN, y  - duringAnimationQuestShowerHeight + TEXT_AND_RECTANGLE_MARGIN, duringAnimationQuestShowerWidth, duringAnimationQuestShowerHeight);
        else
            sr.rect(x - TEXT_AND_RECTANGLE_MARGIN, y  - questShowerHeight + TEXT_AND_RECTANGLE_MARGIN, questShowerWidth, questShowerHeight);
        drawQuestCircles(questToShow, x + TEXT_AND_RECTANGLE_MARGIN, y - circleRadius, circleRadius);
        sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
        batch.begin();
        if (isBeingAnimated)
            printQuest(questToShow, x + 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN, y, (float)duringAnimationTextOpacity);
        else
            printQuest(questToShow, x + 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN, y, 1);
        batch.end();
    }

    /**
     * Initialise les variables du support.
     */
    protected void init() {
        circleRadius = (int)gs.getQuestFont().getLineHeight() / 2;

        sr = new ShapeRenderer();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.setAutoShapeType(true);
    }

    /**
     * Défini la quête principale à afficher.
     * @param q La quête principale.
     */
    public void setQuest(Quest q) {
        questToShow = q;
        questShowerWidth = getMaximumQuestNameWidth(q, 2 * circleRadius + BETWEEN_CIRCLE_AND_TEXT_MARGIN) + 2 * TEXT_AND_RECTANGLE_MARGIN;
        questShowerHeight = getMaximumQuestHeight(q) + TEXT_AND_RECTANGLE_MARGIN * 2;
    }


    /**
     * Affiche la ligne correspondante à la quête <code>q</code> et les lignes correspondantes à ces sous-quêtes à partir de la position(<code>beginningX</code>, <code>beginningY</code>) avec l'opacité de texte <code>textOpacity</code>.
     * @param q La quête à afficher.
     * @param beginningX La position horizontale de départ.
     * @param beginningY La position verticale de départ.
     * @param textOpacity L'opacité du texte.
     */
    protected void printQuest(Quest q, int beginningX, int beginningY, float textOpacity) {
        gs.getQuestFont().setColor(1, 1, 1, textOpacity);
        gs.getQuestFont().draw(batch, q.getName() + '\n', beginningX, beginningY);
        gs.getQuestFont().setColor(1, 1, 1, 1); // Si jamais il est utilisé entre temps
        for (GoalsQuest q2 : q.getSubQuests()) {
            beginningY -= (gs.getQuestFont().getLineHeight() + BETWEEN_QUEST_MARGIN_HEIGHT);
            printQuest(q2, beginningX + BETWEEN_QUEST_MARGIN_WIDTH, beginningY, textOpacity);
        }
    }

    /**
     * Dessine les cercles montrant l'avancement de la quête <code>q</code>.
     * @param q La quête
     * @param beginningX La position horizontale de départ.
     * @param beginningY La position verticale de départ.
     * @param radius Le rayon du cercle.
     */
    protected void drawQuestCircles(Quest q, int beginningX, int beginningY, float radius) {
//        sr.setColor(21f / 255, 21f / 255, 21f / 255, 1f);
        float degrees = (float)q.getProgress() * 360;

        sr.setColor(UNACTIVATED_QUEST_CIRCLE_COLOR);
        if (q.isFinished())
            sr.setColor(FINISHED_QUEST_CIRCLE_COLOR);
        else if (q.isActive() || degrees != 0)
            sr.setColor(ACTIVATED_QUEST_CIRCLE_COLOR);

        if (q.isActive() && degrees == 0) {
            sr.set(ShapeRenderer.ShapeType.Line); // Ce n'est pas dérangeant qu'il s'éxécute en dernier comme le end est appelé juste après.
            sr.circle(beginningX, beginningY, radius); // Evite une ligne en plein milieu de tout !!!
        }
        else {
            sr.set(ShapeRenderer.ShapeType.Filled);
            sr.arc(beginningX, beginningY, radius, 0, (degrees == 0 ? 360 : degrees));
        }
        for (GoalsQuest q2 : q.getSubQuests()) {
            beginningY -= (gs.getQuestFont().getLineHeight() + BETWEEN_QUEST_MARGIN_HEIGHT);
            drawQuestCircles(q2, beginningX + BETWEEN_QUEST_MARGIN_WIDTH, beginningY, radius);
        }
    }

    /**
     * Retourne la taille hozizontale maximale qui sera utilisée par l'affichage de la quête <code>mainQuest</code> et de ces sous-quêtes.
     * @param mainQuest La quête principale.
     * @param defaultMargin La marge de départ.
     * @return La taille hozizontale maximale qui sera utilisée par l'affichage d'une quête <code>mainQuest</code> et de ces sous-quêtes.
     */
    protected int getMaximumQuestNameWidth(Quest mainQuest, int defaultMargin) {
        int max = getTextSize(mainQuest.getName()).x + defaultMargin;
        for (Quest q: mainQuest.getSubQuests()) {
            int i = getMaximumQuestNameWidth(q, max + BETWEEN_QUEST_MARGIN_WIDTH);
            if (i > max)
                max = i;
        }
        return max;
    }

    /**
     * Retourne la taille verticale maximum du support pour la quête principale <code>mainQuest</code>.
     * @param mainQuest La quête principale.
     * @return La taille verticale maximum du support pour la quête principale <code>mainQuest</code>.
     */
    protected int getMaximumQuestHeight(Quest mainQuest) {
        return (int)(mainQuest.getTotalSubQuestsNumber() * gs.getQuestFont().getLineHeight() + BETWEEN_QUEST_MARGIN_HEIGHT * mainQuest.getTotalSubQuestsNumber());
    }

    /**
     * Retourne la taille du texte <code>text</code> avec l'écriture utilisée pour les quêtes.
     * Code inspiré de https://stackoverflow.com/a/16606599 par BennX
     * @param text Le texte
     * @return La taille du texte <code>text</code> avec l'écriture utilisée pour les quêtes.
     */
    protected Point getTextSize(String text) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(gs.getQuestFont(), text);
        return new Point((int)layout.width, (int)layout.height);// contains the width of the current set text
    }

    /**
     * Retourne la taille horizontale du support.
     * @return La taille horizontale du support.
     */
    public int getWidth() {
        return questShowerWidth;
    }
    /**
     * Retourne la taille verticale du support.
     * @return La taille verticale du support.
     */
    public int getHeight() {
        return questShowerHeight;
    }
    /**
     * Retourne la taille verticale du support durant son animation.
     * @return La taille verticale du support durant son animation.
     */
    public float getDuringAnimationQuestShowerHeight() {
        return duringAnimationQuestShowerHeight;
    }
    /**
     * Retourne la taille horizontale du support durant son animation.
     * @return La taille horizontale du support durant son animation.
     */
    public float getDuringAnimationQuestShowerWidth() {
        return duringAnimationQuestShowerWidth;
    }

    /**
     * Défini la taille verticale du support durant son animation.
     * @param duringAnimationQuestShowerHeight La taille verticale du support durant son animation.
     */
    public void setDuringAnimationQuestShowerHeight(float duringAnimationQuestShowerHeight) {
        this.duringAnimationQuestShowerHeight = duringAnimationQuestShowerHeight;
    }

    /**
     * Défini la taille horizontale du support durant son animation.
     * @param duringAnimationQuestShowerWidth La taille horizontale du support durant son animation.
     */
    public void setDuringAnimationQuestShowerWidth(float duringAnimationQuestShowerWidth) {
        this.duringAnimationQuestShowerWidth = duringAnimationQuestShowerWidth;
    }

    /**
     * Retourne l'opacité du texte durant l'animation du support.
     * @return L'opacité du texte durant l'animation du support.
     */
    public double getDuringAnimationTextOpacity() {
        return duringAnimationTextOpacity;
    }
    /**
     * Défini l'opacité du texte durant l'animation du support.
     * @param duringAnimationTextOpacity L'opacité du texte durant l'animation du support.
     */
    public void setDuringAnimationTextOpacity(double duringAnimationTextOpacity) {
        this.duringAnimationTextOpacity = duringAnimationTextOpacity;
    }

    /**
     * Lance l'animation du support.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
    }

    /**
     * Termine l'animation du support.
     */
    public void finishAnimation() {
        isBeingAnimated = false;
    }
}
