package be.ac.umons.mom.g02.Quests.Under;

import be.ac.umons.mom.g02.Enums.Subject;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Quest;

import java.util.ArrayList;
import java.util.Scanner;

public class ChooseSubject extends UnderQuest
{
    public Subject subject;
    public ArrayList choice=new ArrayList();
    public ChooseSubject(Quest q, People people)
    {
        super("ChooseSubject", 50, q,people);
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
