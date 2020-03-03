package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Subject;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;
import java.util.Scanner;

public class ChooseSubject extends UnderQuest
{
    public Subject subject;
    public ChooseSubject(Quest q, People people)
    {
        super("ChooseSubject", 50, q,people);
    }

    public String showSubject()
    {
        String res="";
        for (Subject sub :Subject.values())
        {
            res=res+sub+", \n";
        }
        return res;
        //on appelle cette methode dans une bulle de dialogue et ça permet de savoir les sujets disponible
    }

    /**
     * This method show the subject of the memory
     *
     * @return all the choice
     */
    public String displayChoice()
    {
        String subject="";
        for (Subject sub : Subject.values()) {
            subject=subject+" , "+sub;
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
        //TODO regarder si j'ajoute 50 pour cette quete et pas oublier de changer ça dans makeChoice
        this.subject=null; //on détruit l'ancien sujet
        addProgress(50);
        //Cette methode est appele quand on demande si l'utilisateur est sur de son choix et qu'il dit nn sinon elle n'est jamais appellé.
    }

    public Quest[] getSubQuests() {
        Quest[] q = {};
        return q;
    }

    public int getTotalSubQuestsNumber() {
        return getSubQuests().length;
    }

    @Override
    public void evenActivity(Notification notify) {
    }
}
