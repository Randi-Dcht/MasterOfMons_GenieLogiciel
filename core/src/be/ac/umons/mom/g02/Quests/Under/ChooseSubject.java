package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Subject;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;

import java.util.ArrayList;


/**
 * This class define the goals of choose subject in the list
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class ChooseSubject extends UnderQuest
{
    /***/
    public Subject subject;
    /***/
    public ArrayList choice=new ArrayList();


    /**
     * This constructor define the class of check study
     * @param master is the master class of this
     * @param max    is the maximum percent of this class
     * @param people is the player when play the underQuest
     */
    public ChooseSubject(Quest master,int max, People people)
    {
        super("ChooseSubject", max, master,people);
    }


    /**
     * This method show the subject of the memory
     *
     * @return all the choice
     */
    public String displayChoice()
    {
        choice.add("Please, choice your subject");
        choice.add(subject.crepro);
        choice.add(subject.crelang);
        choice.add(subject.infauto);
        SuperviserNormally.getSupervisor().getEvent().notify(new Dialog(choice));
        String subject="";
        for (Subject sub : Subject.values()) {
            subject = subject + " , " + sub;
        }
        return subject;
    }

    /**
     * This method allows to choice your subject
     */
    public void makeChoice(Subject sub)  //Cette méthode est appelle lors du choix  !
    {
        this.subject=sub;
        addProgress(50);

    }

    /**
     * This method allows us to change the subject before the confirmation
     */
    public void removeChoice()
    {
        this.subject=null; //on détruit l'ancien sujet
        addProgress(-50);
        //Cette methode est appele quand on demande si l'utilisateur est sur de son choix et qu'il dit nn sinon elle n'est jamais appellé.
    }


    /**
     * This method allows to receive the notification of the game
     * @param notify is the notification in the game
     */
    @Override
    public void evenActivity(Notification notify)
    {
    }


    /**
     * This method return the list of the under quest of this
     * @return list of under quest
     */
    public Quest[] getSubQuests()
    {
        return new Quest[]{};
    }


    /**
     * This method return the number of recursion on under this
     * @return number recursion
     */
    public int getTotalSubQuestsNumber()
    {
        return 0;
    }
}
