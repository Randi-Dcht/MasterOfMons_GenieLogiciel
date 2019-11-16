package be.ac.umons.sgl.mom.GraphicalObjects;

import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Quest;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class QuestProgressCircle {
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
     * Les paramètres graphiques du jeu.
     */
    protected GraphicalSettings gs;
    /**
     * Permet de dessiner les formes comme les rectangles.
     */
    protected ShapeRenderer sr;
    /**
     * Doit-on utiliser les variables d'animations ?
     */
    protected boolean isBeingAnimated;
    /**
     * L'opacité du du rectangle sur laquelle l'image de l'élément d'inventaire sera déssinée durant une animation.
     */
    protected float duringAnimationOpacity;
    /**
     * L'opacité du du rectangle sur laquelle l'image de l'élément d'inventaire sera déssinée durant une animation.
     */
    protected float duringAnimationCircleDegrees;

    /**
     * La quête associé avec ce cercle.
     */
    protected Quest quest;

    /**
     * Le dernier radius donné lors du dessinage.
     */
    protected float lastRadius;

    /**
     * Crée un nouveau cercle de progression de quête.
     * @param gs Les paramètres graphique du jeu.
     * @param quest La quête associée avec ce cercle.
     */
    public QuestProgressCircle(GraphicalSettings gs, Quest quest) {
        this.gs = gs;
        this.quest = quest;
        sr = new ShapeRenderer();
        sr.setAutoShapeType(true);
    }

    /**
     * Dessine aux coordonées (<code>x</code>, <code>y</code>) une cercle de rayon <code>radius</code>.
     * @param x La position horizontale.
     * @param y La position verticale.
     * @param radius Le rayon du cercle.
     */
    public void draw(int x, int y, float radius) {
        float degrees;
        if (isBeingAnimated)
            degrees = getDuringAnimationCircleDegrees();
        else
            degrees = (float)quest.getProgress() * 360;
        lastRadius = radius;
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        sr.begin();

        sr.setColor(UNACTIVATED_QUEST_CIRCLE_COLOR);
        if (quest.isFinished())
            sr.setColor(FINISHED_QUEST_CIRCLE_COLOR);
        else if (quest.isActive() || degrees != 0)
            sr.setColor(ACTIVATED_QUEST_CIRCLE_COLOR);

        if (isBeingAnimated)
            sr.getColor().a = duringAnimationOpacity;

        if (quest.isActive() && quest.getProgress() == 0) {
            sr.set(ShapeRenderer.ShapeType.Line); // Ce n'est pas dérangeant qu'il s'éxécute en dernier comme le end est appelé juste après.
            if (isBeingAnimated)
                sr.arc(x, y, radius, 0, degrees); // Evite une ligne en plein milieu de tout !!!
            else
                sr.circle(x, y, radius);
        }
        else {
            sr.set(ShapeRenderer.ShapeType.Filled);
            sr.arc(x, y, radius, 0, (degrees == 0 ? 360 : degrees));
        }
        sr.end();

        Gdx.gl.glDisable(GL30.GL_BLEND);
    }

    /**
     * Libère les ressources alloués par cet élément.
     */
    public void dispose() {
        sr.dispose();
    }

    /**
     * Défini l'opacité durant une animation.
     * @param duringAnimationOpacity L'opacité durant une animation.
     */
    public void setDuringAnimationOpacity(float duringAnimationOpacity) {
        this.duringAnimationOpacity = duringAnimationOpacity;
    }

    /**
     * Retourne l'opacité durant une animation.
     * @return L'opacité durant une animation.
     */
    public float getDuringAnimationOpacity() {
        return duringAnimationOpacity;
    }

    public void setDuringAnimationCircleDegrees(float duringAnimationCircleDegrees) {
        this.duringAnimationCircleDegrees = duringAnimationCircleDegrees;
    }

    public float getDuringAnimationCircleDegrees() {
        return duringAnimationCircleDegrees;
    }

    /**
     * Commencer à utiliser les variables d'animations.
     */
    public void beginAnimation() {
        isBeingAnimated = true;
    }

    /**
     * Arreter d'utiliser les variables d'animations.
     */
    public void finishAnimation() {
        isBeingAnimated = false;
    }

    /**
     * Retourne la hauteur du cercle.
     * @return La hauteur du cercle.
     */
    public int getHeight() {
        return Math.round(lastRadius * 2);
    }
    /**
     * Retourne la longueur du cercle.
     * @return La longueur du cercle.
     */
    public int getWidth() {
        return Math.round(lastRadius * 2);
    }

    /**
     * Défini le pourcentage du cercle qui doit être déssiné. Le degré final est (percent * (pourcentage de la quête) * 360).
     * @param percent Le pourcentage du cercle qui doit être déssiné
     */
    public void setDuringAnimationProgressPercent(double percent) {
        setDuringAnimationCircleDegrees((float)(percent * (quest.getProgress() == 0 ? 1 : quest.getProgress()) * 360));
    }

    /**
     * Retourne la quête associée à ce cercle.
     * @return La quête associée à ce cercle.
     */
    public Quest getQuest() {
        return quest;
    }
}
