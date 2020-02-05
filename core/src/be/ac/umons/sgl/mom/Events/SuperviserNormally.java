package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.PNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Saving;
import be.ac.umons.sgl.mom.Objects.TimeGame;
import be.ac.umons.sgl.mom.Quests.Master.Bachelor1;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.util.ArrayList;
import java.util.Arrays;


/**
 This class allows to monitor the game in the normally game without extension .
 *@author Randy Dauchot (étudiant en Sciences informatique)
 */

public class SuperviserNormally implements Observer
{
       public static SuperviserNormally instance;

       /**
        * This method to give the only instance of <code>SuperviserNormaly</code>
        */
       public static SuperviserNormally getSupervisor()
       {
           if(instance == null)
               instance = new SuperviserNormally();
           return instance;
       }

       /**
        * This constructor allows to define the class who monitor the game
        */
       private SuperviserNormally()
       {
           event = new Event();
           event.add(Events.ChangeFrame,this);
       }

        /*The people who play this party*/
        private People people;
        /*The all objects in all maps in this game*/
        private ArrayList<Items> objet = new ArrayList<Items>();
        /*The all no people in thsi game*/
        private ArrayList<PNJ> listPNJ = new ArrayList<PNJ>();
        /*This is a timer for a saving the game at the regular period*/
        private double minute = 600;
        /*This the class who save the game in real time*/
        public /*private*/ Saving save;
        /*This is the time in the game*/
        private TimeGame time;
        /*This is the events variable*/
        private Event event;

        /**
         * This methods allows to return the people of this game
         * @return people of this game
         */
        public  People getPeople()
        {
            return people;
        }

        /**
         * This method allows to add the new PNJ to the party.
         * @param lst is a list of the new PNJ
         */
        public void add(PNJ ... lst)
        {
            listPNJ.addAll(Arrays.asList(lst));
        }

        /**
         * This method allows to add the new items.
         * @param lst is a list of the new items
         */
        public void add(Items ... lst)
        {
            objet.addAll(Arrays.asList(lst));
        }

        /**
         * This method to allows to create a new party of this game
         * This class allows create all instance of the class for a game
         * @param namePlayer who name of the player play game
         * @param type who is type of the people as defence,agility
         * @param gs who is the class with the param to safe.
         */
        public void newParty(String namePlayer, Type type, GraphicalSettings gs) //TODO regarder pour events mais pas sûre
        {
            people = new People(namePlayer,type);
            //add
            MasterQuest mQ = new Bachelor1(people,null);
            people.newQuest(mQ);
            time = new TimeGame(9,1,8,2019);
            //add
            save = new Saving(people,namePlayer,gs);
            //add
        }

        @Override
        public void update(Events event)
        {
            if (event.equals(Events.ChangeFrame))
                callMethod(0.3);
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
                //remplacer par event
            }
            time.updateSecond(dt);
        }

        /**
         * This method to give the event instance
         * @return event
         */
        public Event getEvent()
        {
            return event;
        }

        /**
         * This method to give the time of the game
         * @return time of game
         */
    public TimeGame getTime()
    {
        return time;
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