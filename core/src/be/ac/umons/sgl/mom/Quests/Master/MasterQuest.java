package be.ac.umons.sgl.mom.Quests.Master;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Objects.Characters.PNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Supervisor;
import be.ac.umons.sgl.mom.Quests.Quest;
import be.ac.umons.sgl.mom.Quests.Under.UnderQuest;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *La MasterQuest est une classe abstraite qui contient elle même des underQuest.
 *Chaque joueur peut jouer sur un seul MasterQuest à la fois.
 *Pour réusir une MasterQuest, on doit réusir un maximun de underQuest qui font augmenter le % de réussite.
 *Chaque MasterQuest possède un parent et/ou un enfant.
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public abstract class MasterQuest implements Quest,Serializable
{
    /*nombre de quête qui sont chainées*/
    protected static int numberQuest = 1;
    /*interrogation qui doit encore passer*/
    protected ArrayList<Lesson> interrogation = new ArrayList<Lesson>(); //les interrogations qui doit encore passer.
    /*objet disponible dans son sac à dos*/
    protected ArrayList<Items> availableObject = new ArrayList<Items>(); //objet disponible sur la maps pour lui prendre
    /*avancement de la quete en %*/
    protected double percent = 0; //avanacement de la quête
    /*pourcentage maximun de la MasterQuest*/
    protected double maxPercent = 95;
    /*la quete parent de celle-ci*/
    final MasterQuest before; //quête qui se trouve juste avant
    /*numéro de la quête pour savoir si elle peut être débuté*/
    final int id; //permet de dire dans quelle quête cela se passe (1 à 5)
    /*qui joue cette quête*/
    final People people;
    /*liste des cours de cette années*/
    protected Lesson[] course; //cours que le personnage doit prendre pour cette quête
    /*quête qui suit celle-ci qui est la quete fils*/
    protected MasterQuest after = null; //la quête qui suit
    /*liste des objectifs de cette quete (sous quete et sous sous quete)*/
    protected UnderQuest[] underQuest;
    /*est ce que la quete est terminée*/
    protected boolean finished = false;

    /**
     * This constructor allows to define a masterQuest
     *@param before who is the before masterQuest
     *@param people who is the people who play this masterQuest
     */
    public MasterQuest(MasterQuest before, People people)
    {
        this.before = before;
        this.id     = numberQuest;
        numberQuest++;
        this.people = people;
    }

    /**
     *Cette méthode permet de créer une nouvelle quête quand celle-ci est terminé
     *@param after qui est la quête suiavnte celle-ci (liste chainée).
     */
    public void newQuest(MasterQuest after)
    {
        if(finished)
        {
            this.after = after;
            people.newQuest(after);
        }
    }

    public abstract void nextQuest();

    /**
     *Cette méthode permet d'ajouter des sous quete à la MasterQuest
     */
    public void addUnderQuest(UnderQuest[] q)
    {
        underQuest = q;
    }

    /**
     * Retourne si la quête est terminée.
     * @return Si la quête est terminée.
     */
    public boolean isFinished()
    {
        return finished;
    }

    /**
     *Cette méthode permet de retourner les cours suivis.
     *@return couse qui retourne la liste des cours suivis pour cette quête.
     */
    public Lesson[] getLesson()
    {
        return course;
    }

    protected void ObligationLesson(Lesson[] course)
    {
        this.course = course;
    }

    /**
     * Retourne le progrés de la quête compris dans l'interval [0,1].
     * @return Le progrés de la quête compris dans l'interval [0,1].
     */
    public double getProgress()
    {
        return (percent/maxPercent);
    }

    /**
     *Cette méthode permet de retourner l'avancement entre 0 et max %
     *@return percent qui est l'avancement
     */
    public double getAdvancement()
    {
        return percent;
    }

    /**
     * Retourne le nom de la quête.
     * @return Le nom de la quête.
     */
    public abstract String getName();

    /**
     * Retourne si la quête est active ?
     * @return Si la quête est active ?
     */
    public boolean isActive()
    {
        return !finished;
    }

    /**
     *Cette méthode permet de dire le nombre de quête qu'il y a déjà
     */
    public int getTotalSubQuestsNumber()
    {
        return getTotalSubQuestsNumber(true);
    }

    /**
     *Cette méthode permet de dire le nombre de quête qu'il y a déjà
     * @param main Si l'appel est récursif ou non.
     */
    protected int getTotalSubQuestsNumber(boolean main)
    {
        return underQuest.length + (main ? 1 : 0);
    }

    /**
     *Cette méthode permet d'ajouter à la liste d'interrogation les cours qui ont été raté dans la quête précédent
     *@param list qui est une ArrayList des cours que le personnage suit.
     */
    public void retake(ArrayList<Lesson> list)
    {
        interrogation.addAll(list);
    }

    /**
     *Cette méthod permet de dire où est le personnage et en fonction changer son état
     */
    public void location(Place place)
    {
        if(place.equals(Place.GrandAmphi) || place.equals(Place.Waroque))
            people.changedState(State.listen);/*
  else if(place.equals(Place.kot))
   //gérer car étudie ou dort
  else
   //gérer soit courir ou rien*/
        //eventMaps();
    }

    /**
     *Cette méthode permet de dire au sous quete qu'il y a eu un évènement et qu'il faut vérifier l'avancement
     */
    public void eventMaps(Actions action)
    {
        for(UnderQuest uq : underQuest)
        {
            uq.evenActivity();
        }
    }

/*public void checkEvent(UnderQuest quest)
{
  quest.evenActivity();
  if(quest.getTotalSubQuestsNumber > 0)
}*/

    /**
     *Cette méthode permet de voir si la quête est terminée pour changer de quête ou continuer
     *@param many ajoute du pourcentage de terminer quand le personnage a fait quelque chose
     */
    public void addProgress(double many)
    {
        if(!finished)
        {
            percent = percent + many;
            if(percent >= maxPercent)
            {
                finished = true;
                nextQuest();
                Supervisor.changedQuest();
            }
        }
    }

    /**
     *This method allows to give the before masterQuest
     *@return before who is a before masterQuest
     */
    public MasterQuest getParent()
    {
        return before;
    }

    /**
     *This method allows to give the masterQuest after this
     @return after who is the next masterQuest
     */
    public MasterQuest getChildren()
    {
        return after;
    }

    /**
     *This method allows to give a people who play this masterQuest
     *@return people who play
     */
    public People getPlayer()
    {
        return people;
    }

    /**
     *This method allows to give the interrogation of this masterQuest
     *@return a list of the Lesson where there are the interrogations
     */
    public ArrayList<Lesson> getInterrogation()
    {
        return interrogation;
    }

    /**
     *Cette méthode permet de retourner les objets que le personnages a dans son sac à dos
     *@return  availableObject qui est son sac à dos
     */
    public ArrayList<Items> getObjets()
    {
        return availableObject;
    } //TODO supprimer cette méthode

    /**
     *This method allows to give a tab with the underQuest of this masterQuest
     *@return a tab of the underQuest.
     */
    public UnderQuest[] getunderQuest()
    {
        return underQuest;
    }

    /**
     *Cette méthode permet de retourner les sous quêtes (objectif) de cette quête
     *@return Quest qui est une liste de sous quêtes.
     */
    public Quest[] getSubQuests()
    {
        return underQuest;
    } //TODO supprimer avec ligne plus haut

    /**
     *This method allows to give an unique id of this masterQuest and same the place of the linkList
     *@return id who is unique and it is a integer
     */
    public int id()
    {
        return id;
    }

    public double getMaximun()
    {
        return maxPercent;
    }
    /*------------------------------------------------------------------------------------------------------------*/

    /**
     *This method allows to meet between people and a pnj
     *@param other who is the pnj to meet
     */
    public void meetOther(PNJ other)
    {
        //TODO a faire
    }

    /**
     *This method allow to say what is this quest
     *@return a goal (=question) of this quest
     */
    public abstract String question();

}
