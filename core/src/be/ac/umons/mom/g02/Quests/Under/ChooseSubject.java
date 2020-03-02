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
     * This method allows to choice the subject of the memory
     *
     * @return choice is the choice of your subject
     */
    public Subject makeChoice()  //Cette méthode est appelle lors du choix  !
    {
        System.out.println("It's the different subject that you can choice");
        for (Subject sub : Subject.values()) {
            System.out.println("-->" + sub.getSubjectName() + "\n");
            Scanner a = new Scanner(System.in);
            System.out.println("Do you want to choose this subject, YES or NO");
            String answer = a.nextLine();
            if (answer.equals("YES"))
            {
                System.out.println("Your choice is : "+sub.getSubjectName());
                this.subject=sub;
                addProgress(50);
                return sub;
            }
        }
        System.out.println("You must do this subject because you didn't choose anything");
        this.subject=Subject.crepro;
        addProgress(50);
        return (Subject.crepro);
    }

    /**
     * This method allows us to change the subject before the confirmation
     */
    public void removeChoice()
    {
        //TODO regarder si j'ajoute 50 pour cette quete et pas oublier de changer ça dans makeChoice
        this.subject=null; //on détruit l'ancien sujet
        //removeProgress(15); // si on ne retire pas de l'experience alors on peut en gagnger tout le temps
        progress=progress-50;
        this.makeChoice();
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
