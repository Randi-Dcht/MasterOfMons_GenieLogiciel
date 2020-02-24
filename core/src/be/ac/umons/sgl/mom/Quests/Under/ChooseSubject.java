package be.ac.umons.sgl.mom.Quests.Under;

import be.ac.umons.sgl.mom.Enums.Subject;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Quest;

public class ChooseSubject extends UnderQuest
{
    public Subject subject;
    public ChooseSubject(Quest q, People people)
    {
        super("ChooseSubject", 50, q,people);
    } //TODO regarder si je garde cette valeur là pour cette sous quete
    /**
     * This method allows to choice the subject of the memory
     *
     * @return choice is the choice of your subject
     */
    public String showSubject()
    {
        String res="";
        for (Subject sub :Subject.values())
        {
            res=res+sub+", \n";
        }
        return res;
    }
    public void makeChoice(Subject sub)  //Quand tu cliques sur le sujet cette méthode est appelé !
    {
        /*System.out.println("It's the different subject that you can choice");
        for (Subject sub : Subject.values()) {
            System.out.println("-->" + sub.getSubjectName() + "\n");
            Scanner a = new Scanner(System.in);
            System.out.println("Do you want to choose this subject, YES or NO");
            String answer = a.nextLine();
            if (answer.equals("YES"))
            {
                System.out.println("Your choice is : "+sub.getSubjectName());
                subject=sub;
                return sub;
            }
        }
        System.out.println("You must do this subject because you didn't choose anything");
        subject=Subject.crepro;
        return  Subject.crepro;*/
    subject=sub;
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
