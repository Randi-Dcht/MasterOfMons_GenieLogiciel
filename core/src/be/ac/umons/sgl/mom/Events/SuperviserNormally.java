package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Gender;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Notifications.Dialog;
import be.ac.umons.sgl.mom.Events.Notifications.LaunchAttack;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.Character;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.MovingPNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Characters.Social;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Saving;
import be.ac.umons.sgl.mom.Objects.TimeGame;
import be.ac.umons.sgl.mom.Other.Date;
import be.ac.umons.sgl.mom.Quests.Master.MyFirstYear;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * This class allows to monitor the game in the normally game without extension .
 * @author Umons_Group_2_ComputerScience
 */
public class SuperviserNormally implements Observer
{


       /***/
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

       /*--------------------------------------------------------------------------------------------------------*/


        /**The people who play this party*/
        private People people;
        /**The all objects in all maps in this game*/
        private HashMap<Place,ArrayList<Items>> listItems;
        /**The all mobile - PNJ (not moving) in this game*/
        private HashMap<Place,ArrayList<Mobile>> listMobile;
        /**The all mobile who moves in this game*/
        private HashMap<Place,ArrayList<MovingPNJ>> listMoving;
        /**This is a lst of the mobile dead */
        private ArrayList<Mobile> deadMobile = new ArrayList<>();
        /**This the class who save the game in real time*/
        private Saving save;
        /**This is the instance of the Graphic*/
        private GraphicalSettings graphic;
        /**This is the time in the game*/
        private TimeGame time;
        /**This is the events variable*/
        private Event event;
        /**Associate String to maps*/
        private HashMap<String,Place> listMap = new HashMap<>();
        /**Associate Bloc to Lesson*/
        private HashMap<Bloc,ArrayList<Lesson>> listLesson = new HashMap<Bloc, ArrayList<Lesson>>();
        /**when the attack is the mobile*/
        private Mobile memoryMobile;


       /**
        * This constructor allows to define the class who monitor the game
        */
       private SuperviserNormally()
       {
           for (Place plt : Place.values())
               listMap.put(plt.getMaps(),plt);
           event = new Event();
           event.add(Events.Dead,this);
           event.add(Events.Answer,this);//TODO changer pour mettre avec remove
           associateLesson();
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
            if (listItems.containsValue(place))
                return listItems.get(place);
            return new ArrayList<>();
        }


        /***/
        public void associateLesson()
        {
            for (Bloc blc : Bloc.values())
                listLesson.put(blc,new ArrayList<Lesson>());
            for (Lesson ls : Lesson.values())
                listLesson.get(ls.getBloc()).add(ls);
        }


        /***/
        public ArrayList<Lesson> getLesson(Bloc blc)
        {
            return listLesson.get(blc);
        }


        /**
         * This method return the mobile for a maps
         * @param place is the place (maps)
         * @return list of the mobile for this maps.
         */
        public ArrayList<Mobile> getMobile(Place place)
        {
            if(listMobile.containsValue(place))
                return listMobile.get(place);
            return new ArrayList<>();
        }


        /**
         * This method to allows to create a new party of this game
         * This class allows create all instance of the class for a game
         * @param namePlayer who name of the player play game
         * @param type who is type of the people as defence,agility
         */
        public void newParty(String namePlayer, Type type, GraphicalSettings graphic, Gender gender, Difficulty difficulty)
        {
            time = new TimeGame(new Date(16,9,2020,8,15));
            people = new People(namePlayer,type, gender,difficulty);
            this.graphic = graphic;
            MasterQuest mQ = new MyFirstYear(people,null,graphic,difficulty);
            people.newQuest(mQ);
            save = new Saving(people,namePlayer);
            createMovingPnj(difficulty);
            createMobil(mQ);
            createItems(mQ);
        }


        /**
         * This method create the hashMap with mobile and the place of this mobile
         * @param difficulty is the difficulty of the game
         */
        private void createMovingPnj(Difficulty difficulty)
        {
            listMoving = new HashMap<>();
            Place[] plc = Place.values();Place place;
            for (Place pl : plc)
                listMoving.put(pl,new ArrayList<>());
            for (int i = 0 ; i <= difficulty.getNumberPNJ() ; i++)
                listMoving.get((place = plc[new Random().nextInt(plc.length)])).add(new MovingPNJ(people.getBloc(), MobileType.Athletic,place));
        }


        /**
         * This method return the list of the moving PNJ in the specific map TMX
         * @param place is the place
         * @return list of the moving pnj in the maps
         */
        public ArrayList<MovingPNJ> getMovingPnj(Place place)
        {
            return listMoving.get(place);
        }


        /**
         * This method allows to give the actual quest to the graphic quest
         * @param qs is the graphic instance to quest
         */
        public void setQuest(QuestShower qs)
        {
            qs.setQuest(people.getQuest());
        }


        /**
         * This method allows to start an old game
         * @param people is the people of the game
         * @param  date is the actually date
         * @param save  is the class of the saving
         */
        public void oldGame(People people, Date date,Saving save)
        {
            time = new TimeGame(date);
            this.people  = people;
            this.save    = save;

            createItems(people.getQuest());
            createMobil(people.getQuest());
            createMovingPnj(people.getDifficulty());
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
        {
            listMobile = new HashMap<>();
            Place[] place = quest.whatPlace();
            for (Place plc : quest.whatPlace())
                listMobile.put(plc,new ArrayList<>());
            for (Mobile mb : quest.whatMobile())
            {
                if (mb.getPlace() == null)
                    mb.setPlace(place[new Random().nextInt(place.length)]);
                listMobile.get(mb.getPlace()).add(mb);
            }
        }


        /**
         * This method allows to create the items on the different maps for this Quest
         * @param quest who is the actual Quest
         */
        private void createItems(MasterQuest quest)
        {
            listItems = new HashMap<>();
            Place[] place = quest.whatPlace();
            for (Place plc : place)
                listItems.put(plc,new ArrayList<>());
            for (Items mb : quest.whatItem())
            {
                if (mb.getPlace() == null)
                    mb.setPlace(place[new Random().nextInt(place.length)]);
                listItems.get(mb.getPlace()).add(mb);
            }
        }


        /**
         * This method allows to receive the notification of the other class
         * @param notify is a notification
         */
        @Override
        public void update(Notification notify)
        {
            if (notify.getEvents().equals(Events.Dead) && ((Character)notify.getBuffer()).getType().equals(Character.TypePlayer.Computer))
            {
                Mobile mb = (Mobile)notify.getBuffer();
                listMobile.get(mb.getPlace()).remove(mb);
                deadMobile.add(mb);
                if (mb.equals(memoryMobile))
                    memoryMobile = null;
            }

            if (notify.getEvents().equals(Events.Answer) && notify.bufferEmpty())
                switchingDialog(((String)notify.getBuffer()));
        }


         /**
         * This method allows to update the class
         * @param dt who is the time between two windows
         */
        public void callMethod(double dt)
        {
            time.updateSecond(dt);
            if(people != null)
                people.energy(dt);
            if (memoryMobile != null)
                memoryMobile.update(dt);
            for (Mobile mb : deadMobile)
                mobileLife(mb,dt);
            deadMobile.removeIf(Character::isLiving);
        }


        /**
         * This method allows to give an items to the people for this Quest (#debug#)
         */
        public void addItems()
        {
            ArrayList<Items> list = listItems.get(people.getPlace());
            people.pushObject(list.get(new Random().nextInt(list.size())));
        }


        /**
         * This method allows to get the life of the Mobile in the game
         * @param dt is the time between two frame
         * @param mobile is the mobile who check the life
         */
        private void mobileLife(Mobile mobile,double dt)
        {
            mobile.regeneration(dt);
            if (mobile.isLiving())
            {
                listMobile.get(mobile.getPlace()).add(mobile);
            }
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
        *This method isn't adapt to fight between two mobile/PNJ
        *This method can take two people (Human player)
        *This method can take one people (Human player) and one one mobile/PNJ (Computer player)
        * @param attacker is the character who attack
        * @param victim is the character who give hits
        */
       public void attackMethod(Attack attacker, Attack victim/*,boolean faceToFace*/) //TODO ajout
       {
           if (attacker.getType().equals(Character.TypePlayer.Human))
               ((People)attacker).reduceEnergizing(State.attack);
           if(victim.dodge() < 0.6)
           {
               if(attacker.howGun() /*&& faceToFace*/)
                   victim.loseAttack(calculateHits(attacker,victim,attacker.damageGun()));
               else
                   victim.loseAttack(calculateHits(attacker,victim,0));
           }
           if(attacker.getType().equals(Character.TypePlayer.Computer))
           {
               ((Mobile) attacker).letsGo(victim);
               memoryMobile = (Mobile)attacker;
           }
           if(victim.getType().equals(Character.TypePlayer.Computer))
               attackMethod(victim,attacker);
       }


       /**
        * This method calculates the hits for the victim of the attack
        * @param attacker is the character who attack
        * @param victim is the character who give hits
        * @param gun is damage calculus
        */
       private double calculateHits(Attack attacker, Attack victim,double gun)
       {
           return ( ( 2.5 * bonus(1,1) * Math.pow(attacker.getStrength(),1.6 ) ) / ( bonus(1,1) * victim.getDefence() + ((bonus(1,1) * victim.getAgility() ) / 5) ) ) * ( ( gun + 40 )/40 );
       }


       /**
        * This method allows calculated a bonus for an attack in two players
        * @return the bonus (double)
        */
        private double bonus(double p, double c)
        {
            return Math.pow(Math.cbrt(1.3),c);
        }


        /**
         * This method is called when two character meet together
         * @param player1 is the first character
         * @param player2 is the second character
         */
        public void meetCharacter(Social player1, Social player2)//TODO upgrade pour moins de clss
        {
            if (((Character)player1).getType().equals(Character.TypePlayer.Computer))
                memoryMobile = (Mobile)player1;
            if (((Character)player2).getType().equals(Character.TypePlayer.Computer))
                memoryMobile = (Mobile)player2;
            Actions action = player1.getAction().comparable(player2.getAction());
            if (action.equals(Actions.Attack))
                attackMethod(memoryMobile,people);//event.notify(new LaunchAttack());TODO donner à Guillaume
            else if (action.equals(Actions.Dialog))
                event.notify(new Dialog(people.getDialog("Start")));
        }


        /**
         * This method allows to quit the meet between two characters
         */
        public void quitCharacter()
        {
            memoryMobile = null;
        }


        /**
         * This method allows to switch between people dialog and
         * @param answer is the answer of the dialog between two characters
         */
        public void switchingDialog(String answer)
        {
            if (answer.equals("Attack"))
                attackMethod(people,memoryMobile);//event.notify(new LaunchAttack(memoryMobile));TODO donner à guillaume
            if(answer.equals("ESC"))
            {
                ArrayList<String> end = new ArrayList<>();end.add("ESC");
                event.notify(new Dialog(end));
                //event.remove(Events.Answer,this);//TODO raccorder
            }
            else
                event.notify(new Dialog(people.getDialog(memoryMobile.getDialog(answer))));
        }

    }//TODO add the update of list item,mobiel,moving when up quest