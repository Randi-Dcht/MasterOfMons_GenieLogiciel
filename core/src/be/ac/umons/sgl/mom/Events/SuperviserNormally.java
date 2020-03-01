package be.ac.umons.sgl.mom.Events;

import be.ac.umons.sgl.mom.Enums.Actions;
import be.ac.umons.sgl.mom.Enums.Bloc;
import be.ac.umons.sgl.mom.Enums.Difficulty;
import be.ac.umons.sgl.mom.Enums.Gender;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Maps;
import be.ac.umons.sgl.mom.Enums.MobileType;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Events.Notifications.Dialog;
import be.ac.umons.sgl.mom.Events.Notifications.LaunchAttack;
import be.ac.umons.sgl.mom.Events.Notifications.MeetOther;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.GraphicalObjects.QuestShower;
import be.ac.umons.sgl.mom.Objects.Characters.Attack;
import be.ac.umons.sgl.mom.Objects.Characters.Character;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Characters.MovingPNJ;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Objects.Characters.Social;
import be.ac.umons.sgl.mom.Objects.Course;
import be.ac.umons.sgl.mom.Objects.GraphicalSettings;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import be.ac.umons.sgl.mom.Objects.Saving;
import be.ac.umons.sgl.mom.Other.Date;
import be.ac.umons.sgl.mom.Other.Regulator;
import be.ac.umons.sgl.mom.Other.TimeGame;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import be.ac.umons.sgl.mom.Quests.Master.MyFirstYear;
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


        /**
         * The people who play this party
         */
        private People people;
        /**
         * The all objects in all maps in this game
         */
        private HashMap<Maps,ArrayList<Items>> listItems;
        /**
         * The all mobile - PNJ (not moving) in this game
         */
        private HashMap<Maps,ArrayList<Mobile>> listMobile;
        /**
         * The all mobile who moves in this game
         */
        private HashMap<Maps,ArrayList<MovingPNJ>> listMoving;
        /**
         * This is a lst of the mobile dead
         */
        private ArrayList<Mobile> deadMobile = new ArrayList<>();
        /**
         * This the class who save the game in real time
         */
        private Saving save;
        /**
         * This is the instance of the Graphic
         */
        private GraphicalSettings graphic;
        /**
         * This is the time in the game
         */
        private TimeGame time;
        /**
         * This is the events instance
         */
        private Event event;
        /**
         * Associate String to maps
         */
        private HashMap<String, Maps> listMap = new HashMap<>();
        /**
         * Associate Bloc to Lesson
         */
        private HashMap<Bloc,ArrayList<Lesson>> listLesson = new HashMap<Bloc, ArrayList<Lesson>>();
        /**
         * when the attack is the mobile
         */
        private Mobile memoryMobile;
        /**
         * The instance of the regulator class*
         */
        private Regulator regule;
        /**
         * This the actual variable of the Id on the maps
         */
        private String actualID;
        /***/
        private ArrayList<Course> listCourse;
        /***/
        private Course actualCourse;


       /**
        * This constructor allows to define the class who monitor the game
        */
       private SuperviserNormally()
       {
           for (Maps plt : Maps.values())
               listMap.put(plt.getMaps(),plt);
           event = new Event();
           event.add(this,Events.Dead,Events.ChangeDay,Events.ChangeHour);
          // event.add(Events.Answer,this);//TODO changer pour mettre avec remove
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
         * This method return the items for a maps.
         * @param maps is the map where there are items
         * @return list of items
         */
        public ArrayList<Items> getItems(Maps maps)
        {
             return listItems.get(maps);
        }


        /**
         * This method allows to associate the lesson with the bloc
         * This method create the hashMap with the bloc associate with list of the lesson
         */
        public void associateLesson()
        {
            for (Bloc blc : Bloc.values())
                listLesson.put(blc,new ArrayList<Lesson>());
            for (Lesson ls : Lesson.values())
                listLesson.get(ls.getBloc()).add(ls);
        }


        /**
         * This method allows to give the lesson associate to the param bloc
         * @param blc is the bloc of the Quest
         */
        public ArrayList<Lesson> getLesson(Bloc blc)
        {
            return listLesson.get(blc);
        }


        /**
         * This method return the mobile for a maps
         * @param maps is the maps (maps)
         * @return list of the mobile for this maps.
         */
        public ArrayList<Mobile> getMobile(Maps maps)
        {
            return listMobile.get(maps);
        }


        /***/
        public Course getActualCourse()
        {
            return actualCourse;
        }


        /**
         * This method to allows to create a new party of this game
         * This class allows create all instance of the class for a game
         * @param namePlayer who name of the player play game
         * @param type who is type of the people as defence,agility
         */
        public void newParty(String namePlayer, Type type, GraphicalSettings graphic, Gender gender, Difficulty difficulty)
        {
            time = new TimeGame(new Date(16,9,2019,8,15));
            people = new People(namePlayer,type, gender,difficulty);
            this.graphic = graphic;
            MasterQuest mQ = new MyFirstYear(people,null,graphic,difficulty);
            people.newQuest(mQ);
            save = new Saving(people,namePlayer);
            refreshQuest();
            regule = new Regulator(people,time);
            listCourse = people.getPlanning().get(time.getDate().getDay());
            checkPlanning();
        }


        /***/
        public Saving getSave()
        {
           return save;
        }


         /***/
         public Regulator getRegale()
         {
             return regule;
         }


        /**
         * This method create the hashMap with mobile and the maps of this mobile
         * @param difficulty is the difficulty of the game
         */
        private void createMovingPnj(Difficulty difficulty)
        {
            listMoving = new HashMap<>();
            Maps[] plc = Maps.values();
            Maps maps;
            for (Maps pl : plc)
                listMoving.put(pl,new ArrayList<>());
            for (int i = 0 ; i <= difficulty.getNumberPNJ() ; i++)
                listMoving.get((maps = plc[new Random().nextInt(plc.length)])).add(new MovingPNJ(people.getBloc(), MobileType.Athletic, maps));
        }


        /**
         * This method return the list of the moving PNJ in the specific map TMX
         * @param maps is the maps
         * @return list of the moving pnj in the maps
         */
        public ArrayList<MovingPNJ> getMovingPnj(Maps maps)
        {
            return listMoving.get(maps);
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
            refreshQuest();
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
         * @return the maps (enum)
         */
        public Maps getMaps(String nameTmx)
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
            Maps[] maps = quest.getListMaps();
            for (Maps plc : Maps.values())
                listMobile.put(plc,new ArrayList<>());
            for (Mobile mb : quest.getListPnj())
            {
                if (mb.getMaps() == null)
                    mb.setMaps(maps[new Random().nextInt(maps.length)]);
                listMobile.get(mb.getMaps()).add(mb);
            }
        }


        /**
         * This method allows to analyse the id in the maps
         * @param id is the id of the object name
         * @throws Exception is the exception if the the size of list is bad or the ill word
         */
        public void analyseIdMap(String id) throws Exception
        {
            if (!id.equals(actualID))
            {
                actualID = id;
                String[] word = id.split("_");
                if (word[0].equals("Room") && word.length >= 3)
                    regule.placeInOut(word[2],word[1]);
                else if (word[0].equals("Info") && word.length >= 2)
                    regule.push(word[1]);
                else
                    throw new Exception();
            }
        }


        /**
         * This method allows to create the items on the different maps for this Quest
         * @param quest who is the actual Quest
         */
        private void createItems(MasterQuest quest)
        {
            listItems = new HashMap<>();
            Maps[] maps = quest.getListMaps();
            for (Maps plc : Maps.values())
                listItems.put(plc,new ArrayList<>());
            for (Items mb : quest.getListItems())
            {
                if (mb.getMaps() == null)
                    mb.setMaps(maps[new Random().nextInt(maps.length)]);
                listItems.get(mb.getMaps()).add(mb);
            }
        }


        /**
         * This method is called when the quest is start
         */
        public void refreshQuest()
        {
            createItems(people.getQuest());
            createMobil(people.getQuest());
            createMovingPnj(people.getDifficulty());
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
                listMobile.get(mb.getMaps()).remove(mb);
                deadMobile.add(mb);
                if (mb.equals(memoryMobile))
                    memoryMobile = null;
            }

            if (notify.getEvents().equals(Events.Answer) && notify.bufferNotEmpty())
                switchingDialog(((String)notify.getBuffer()));

            if (notify.getEvents().equals(Events.ChangeQuest))
                refreshQuest();

            if (notify.getEvents().equals(Events.ChangeDay))
                listCourse = people.getPlanning().get(time.getDate().getDay());

            if (notify.getEvents().equals(Events.ChangeHour))
                checkPlanning();
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
         * @param items is the items that the people want to add in the bag
         */
        public void addItems(Items items)
        {
            if (items != null)
                people.pushObject(items);
            else
            {
                ArrayList<Items> list = listItems.get(people.getMaps());
                people.pushObject(list.get(new Random().nextInt(list.size())));
            }
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
                listMobile.get(mobile.getMaps()).add(mobile);
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
       public void attackMethod(Attack attacker, Attack victim) //TODO ajout
       {
           if (attacker.getType().equals(Character.TypePlayer.Human))
           {
               ((People)attacker).reduceEnergizing(State.attack);
               event.notify(new LaunchAttack(memoryMobile));
           }
           if(victim.dodge() < 0.6 && attacker.canAttacker())
           {
               if(attacker.howGun())
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
           return ( ( 2.5 * bonus(attacker.getCharacteristic().getStrengthBonus()) * Math.pow(attacker.getStrength(),1.6 ) ) / ( bonus(victim.getCharacteristic().getDefenceBonus()) * victim.getDefence() + ((bonus(victim.getCharacteristic().getAgilityBonus()) * victim.getAgility() ) / 5) ) ) * ( ( gun + 40 )/40 );
       }


       /**
        * This method allows calculated a bonus for an attack in two players
        * @return the bonus (double)
        */
        private double bonus(double c)
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
                event.notify(new MeetOther(memoryMobile = (Mobile)player1));
            if (((Character)player2).getType().equals(Character.TypePlayer.Computer))
                event.notify(new MeetOther(memoryMobile = (Mobile)player2));
            Actions action = player1.getAction().comparable(player2.getAction());
            if (action.equals(Actions.Attack))
                attackMethod(memoryMobile,people);
            else if (action.equals(Actions.Dialog))
            {
                event.add(Events.Answer,this);
                event.notify(new Dialog(people.getDialog("Start")));
            }
        }


        /**
         * This method allows to switch between people dialog and
         * @param answer is the answer of the dialog between two characters
         */
        public void switchingDialog(String answer)
        {
            if (answer.equals("Attack"))
                attackMethod(people,memoryMobile);
            if(answer.equals("ESC"))
            {
                event.notify(new Dialog("ESC"));
                event.remove(Events.Answer,this);//TODO
            }
            else
                event.notify(new Dialog(people.getDialog(memoryMobile.getDialog(answer))));
        }



        /**
         * This method allows to check the actual course of the player in the game
         */
        public void checkPlanning()
        {
            Date actu = time.getDate();
            if (actualCourse == null || actualCourse.getDate().getHour() + 2 <= actu.getHour())
            {
                  actualCourse = null;
                  for (Course crs : listCourse)
                  {
                       if (crs.getDate().getHour()<= actu.getHour() && (crs.getDate().getHour()+2) > actu.getHour())
                               actualCourse = crs;
                  }
            }
        }

    }