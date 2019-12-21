package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.PNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Saving;
import be.ac.umons.sgl.mom.Objects.Schedule;
import be.ac.umons.sgl.mom.Quests.Master.Bachelor1;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.util.ArrayList;
import java.util.Arrays;


/**
 This class allows to monitor the game in the normally game without extension .
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public class SuperviserNormally
{
        /*The people who play this party*/
        private People people;
        /*The all objects in all maps in this game*/
        private ArrayList<Items> objet = new ArrayList<Items>();
        /*The all no people in thsi game*/
        private ArrayList<PNJ> listPNJ = new ArrayList<PNJ>();
        /*This is an interface graphic of this game (to display quest)*/
        //private QuestShower questShower;
        /**/
        private double minute = 600; /*<= changer par un événement qui viendrer de la classe timer*/
        /*This the class who save the game in real time*/
        private Saving save;
        /*This is the time in the game*/
        private Schedule time;


        /**
         * This methods allows to return the people of this game
         * @return people of this game
         */
        public  People getPeople()
        {
            return people;
        }

        public void add(PNJ ... lst)
        {
            listPNJ.addAll(Arrays.asList(lst));
        }

        public void add(Items ... lst)
        {
            objet.addAll(Arrays.asList(lst));
        }

        /**
         * This method to allows to create a new party of this game
         * This class allows create all instance of the class for a game
         * @param namePlayer who name of the player play game
         * @param type who is type of the people as defence,agility
         */
        public void newParty(String namePlayer, Type type, GraphicalSettings gs)
        {
            people = new People(namePlayer,type);
            MasterQuest mQ = new Bachelor1(people,null);
            people.newQuest(mQ);
            //questShower.setQuest(mQ); /*=>*/ event.update(Events.changeQuest);
            time = new Schedule(9,1,8,2019);
            save = new Saving(people,namePlayer,gs);
        }

        /**
         * voir pour mettre dans la classe de guillaume
         * */
        public void changedQuest() //TODO mettre dans la classe de guillaume
        {/*
            if(questShower != null) //permet lors des tests de ne pas intancier de classes graphique.
            {
                Gdx.app.postRunnable(() -> questShower.setQuest(people.getQuest()));
                save.Signal();
            }*/
        }

        /**
         * This method
         * @param dt who is the time between two windows
         */
        public void callMethod(double dt)
        {
            if(people != null)
                people.energy(dt);

            for (Items o : objet)
                o.make(dt);

            minute = minute - dt;
            if(minute <= 0)
            {
                save.Signal();
                minute = 600;
            }
            time.updateSecond(dt);
        }

    /**
     *This method who call when two Attack want to fight
     */
    public void attackMethod(Attack attacker, Attack victim)
    {
        int gun=1; //TODO a dertimer dans les prochaines fois et regarder au bonus
        double hit = ( ( 2.5 * bonus(1,1) * Math.pow(attacker.getStrength(),1.6 ) ) / ( bonus(1,1) * victim.getDefence() + ((bonus(1,1) * victim.getAgility() ) / 5) ) ) * ( ( gun + 40 )/40 );
    }

    /**
     * This method allows calculated a bonus for an attack in two players
     * @return the bonus (double)
     */
    public double bonus(double p, double c)
    {
        return 1; //TODO continuer
    }

    }