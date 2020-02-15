package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.PlayerType;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Characters.Social;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Saving;
import be.ac.umons.sgl.mom.Objects.TimeGame;
import be.ac.umons.sgl.mom.Quests.Master.MyFirstYear;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.util.ArrayList;
import java.util.HashMap;


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



        /*The people who play this party*/
        private People people;
        /*The all objects in all maps in this game*/
        private HashMap<Place,ArrayList<Items>> listItems;
        /*The all no people in this game*/
        private HashMap<Place,ArrayList<Mobile>> listMobile;
        /*This the class who save the game in real time*/
        public /*private*/ Saving save;
        /**/
        private GraphicalSettings graphic;
        /*This is the time in the game*/
        private TimeGame time;
        /*This is the events variable*/
        private Event event;
        /**/
        private HashMap<String,Place> listMap = new HashMap<>();


       /**
        * This constructor allows to define the class who monitor the game
        */
       private SuperviserNormally()
       {
           for (Place plt : Place.values())
               listMap.put(plt.getMaps(),plt);
           event = new Event();
           event.add(Events.ChangeFrame,this);//TODO voir si celle-ci va être continuer
       }

        /**
         * This methods allows to return the people of this game
         * @return people of this game
         */
        public  People getPeople()
        {
            return people;
        }


        /**
         * This method return the items for a place.
         * @param place is the map where there are items
         * @return list of items
         */
        public ArrayList<Items> getItems(Place place)
        {
            return listItems.get(place);
        }


        /**
         * This method return the mobile for a maps
         * @param place is the place (maps)
         * @return list of the mobile for this maps.
         */
        public ArrayList<Mobile> getMobile(Place place)
        {
            return listMobile.get(place);
        }


        /**
         * This method to allows to create a new party of this game
         * This class allows create all instance of the class for a game
         * @param namePlayer who name of the player play game
         * @param type who is type of the people as defence,agility
         */
        public void newParty(String namePlayer, Type type,GraphicalSettings graphic)
        {
            people = new People(namePlayer,type);
            this.graphic = graphic;
            MasterQuest mQ = new MyFirstYear(people,null,graphic);
            people.newQuest(mQ);
            time = new TimeGame(9,1,8,2019);
            save = new Saving(people,namePlayer);
        }


        /**
         * This method allows to give the graphical instance of Graphic
         * @return graphic instance
         */
        public GraphicalSettings getGraphic()
        {
            return graphic;
        }


        /**
         * This method return the enum of the maps with the name in String (.tmx)
         * @param nameTmx is the name of the maps with the name .TMX
         * @return the place (enum)
         */
        public Place getMaps(String nameTmx)
        {
            return listMap.get(nameTmx);
        }


        /**
         * This method allows to create the Mobil on different maps for this Quest
         * @param quest who is the actual Quest
         */
        private void createMobil(MasterQuest quest)
        {}


        /**
         * This method allows to create the items on the different maps for this Quest
         * @param quest who is the actual Quest
         */
        private void createItems(MasterQuest quest)
        {}


        /**
         * This method allows to receive the notification of the other class
         * @param notify is a notification
         */
        @Override
        public void update(Notification notify)
        {
            if (Events.ChangeFrame.equals(notify.getEvents()))
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

            //for (Items o : listPNJ)
              //  o.make(dt);
            //event.notify(Events.ChangeFrame); //pour le timerGame
        }


        /**
         * This method allows to give an items to the people for this Quest (#debug#)
         */
        public void addItems(Items item)
        {}


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
        *This method isn't adapt to fight between two mobile/PNJ
        *This method can take two people (Human player)
        *This method can take one people (Human player) and one one mobile/PNJ (Computer player)
        * @param attacker is the character who attack
        * @param victim is the character who give hits
        */
       public void attackMethod(Attack attacker, Attack victim)
       {
           if (attacker.getType().equals(PlayerType.HumanPlayer))
               ((People)attacker).reduceEnergizing(State.attack);
           if(victim.dodge() < 0.6)
           {
               if(attacker.howGun())
                   victim.loseAttack(calculateHits(attacker,victim,attacker.damageGun()));
               else
                   victim.loseAttack(calculateHits(attacker,victim,0));
           }
           if(attacker.getType().equals(PlayerType.ComputerPlayer))
           {
               Mobile mb = (Mobile) attacker;
               mb.nextAttack(victim);
           }
       }


       /**
        * This method calculates the hits for the victim of the attack
        * @param attacker is the character who attack
        * @param victim is the character who give hits
        * @param gun is damage calculus
        */
       public double calculateHits(Attack attacker, Attack victim,double gun)
       {
           return ( ( 2.5 * bonus(1,1) * Math.pow(attacker.getStrength(),1.6 ) ) / ( bonus(1,1) * victim.getDefence() + ((bonus(1,1) * victim.getAgility() ) / 5) ) ) * ( ( gun + 40 )/40 );
       }


       /**
        * This method allows calculated a bonus for an attack in two players
        * @return the bonus (double)
        */
        public double bonus(double p, double c)
        {
            return 1; //TODO continuer
        }


        /***/
        public void meetCharacter(Social player1, Social player2)
        {
            Actions action = player1.getAction().comparable(player2.getAction());
            if (action.equals(Actions.Attack))
                System.out.println("Attack"); //TODO modif
            else if (action.equals(Actions.Dialog))
                System.out.println("Dialog"); //TODO modif
        }

    }