package be.ac.umons.mom.g02.Regulator;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Enums.Orientation;
import be.ac.umons.mom.g02.Enums.State;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Notifications.*;
import be.ac.umons.mom.g02.Events.Event;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.QuestShower;
import be.ac.umons.mom.g02.Objects.Characters.*;
import be.ac.umons.mom.g02.Objects.Characters.Character;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.*;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


/**
 * This class define the supervisor who regulate the all game during with single player on the maps
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public  abstract class Supervisor implements Observer
{
    /**
     * This is the only instance of the Supervisor
     */
    protected static Supervisor instance;
    /**
     * The people who play this party
     */
    protected static People playerOne;
    /**
     * This is the events instance
     */
    protected static Event event;
    /**
     * This is the instance of the Graphic
     */
    protected static GraphicalSettings graphic;


    /**
     * This method return the supervisor instance
     * @return supervisor instance (one instance)
     */
    public static Supervisor getSupervisor()
    {
        if (instance == null)//TODO
          SuperviserNormally.initNormallyGame();  //Gdx.app.error("Error in the Supervisor by bad initialization", String.valueOf(new ExceptionInInitializerError()));
        return instance;
    }


    /**
     * This methods allows to return the people of this game
     * @return people of this game
     */
    public static People getPeople()
    {
        return playerOne;
    }


    public static void setPlayerOne(People playerOne)
    {
        Supervisor.playerOne = playerOne;
    }


    /**
     * This method to give the event instance
     * @return event
     */
    public static Event getEvent()
    {
        if (event == null)
        {
            event = new Event();
        }
        return event;
    }


    /**
     * This method allows to give the graphical instance of Graphic
     * @return graphic instance
     */
    public static GraphicalSettings getGraphic()
    {
        return graphic;
    }


    /**
     * This method allows to set the graphical instance of Graphic
     * @param graphic  graphic instance
     */
    public static void setGraphic(GraphicalSettings graphic)
    {
        Supervisor.graphic = graphic;
    }


    /*--------------------------------------------------------------------------------------------------------*/
    /**
     * The all objects in all maps in this game
     */
    protected HashMap<Maps, ArrayList<Items>> listItems;
    /**
     * The all mobile - PNJ (not moving) in this game
     */
    protected HashMap<Maps,ArrayList<Mobile>> listMobile;
    /**
     * The all mobile who moves in this game
     */
    protected HashMap<Maps,ArrayList<MovingPNJ>> listMoving;
    /**
     * This is the list with the association with the character and the graphical instance
     */
    protected HashMap<Character, be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character> graphicalMob;
    /**
     * This is a lst of the mobile dead
     */
    protected ArrayList<Mobile> deadMobile = new ArrayList<>();
    /**
     * This the class who save the game in real time
     */
    protected Saving save;
    /**
     * This is the time in the game
     */
    protected TimeGame time;
    /**
     * Associate String to maps
     */
    protected HashMap<String, Maps> listMap = new HashMap<>();
    /**
     * Associate Bloc to Lesson
     */
    protected HashMap<Bloc,ArrayList<Lesson>> listLesson = new HashMap<>();
    /**
     * The all instance of the dialog in the game
     */
    protected HashMap<NameDialog, DialogCharacter> listDialog = new HashMap<>();
    /**
     * when the attack is the mobile
     */
    protected Mobile memoryMobile;
    /**
     * This is the list of the course of the player
     */
    protected ArrayList<Course> listCourse;
    /**
     * This is the actual course of player
     */
    protected Course actualCourse;
    /**
     * This is a list with the class to update between every changed frames
     */
    protected ArrayList<FrameTime> listUpdate = new ArrayList<>();
    /**
     * This is the dialog instance
     */
    protected DialogCharacter dialog;
    /**
     * This list save the characteristic of player
     */
    protected int[] debugSaving = new int[3];
    /**
     * Instance of the PlayingState
     */
    protected PlayingState playGraphic;
    /**
     * The class to place the item on the maps (id of item)
     */
    protected PositionOnMaps placePosition;
    /**
     * If the first start of the game (place item)
     */
    protected boolean first = true;
    /**
     * The instance of the regulator class*
     */
    protected Regulator regulator;
    /**
     * Boolean representing if the items must be added to the map.
     */
    protected boolean mustPlaceItem = true;
    /**
     * This the actual variable of the Id on the maps
     */
    protected String actualID;
    /**
     * If the game have the shop and the transaction of money
     */
    protected boolean haveMoney = false;
    /**
     * If the dialog can to show
     */
    protected boolean startDialog = true;
    /**
     * The victim of the mobile during attack
     */
    protected People victimPlayer;
    /**
     * The seller PNJ to buy the rare items
     */
    protected Dealer dealerOnMap;


    /**
     * This constructor define the supervisor of the logic game
     */
    protected Supervisor()
    {
        for (Maps plt : Maps.values())
            listMap.put(plt.getMaps(),plt);
        getEvent().add(this, Events.Dead,Events.ChangeDay,Events.ChangeHour,Events.PlaceInMons);
        save = new Saving();
        associateLesson();
        graphicalMob = new HashMap<>();
        listMoving = new HashMap<>();
        listMobile = new HashMap<>();
        victimPlayer = playerOne;
    }


    /**
     * This method return the regulator of the game
     * @return regulator instance
     */
    public Regulator getRegale()
    {
        return regulator;
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
            listLesson.put(blc,new ArrayList<>());
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
     * This method return the all item for the #DEBUGS#
     * @return a list of all item
     */
    public Items[] getAllItems()
    {
        return new Items[]{new Energizing(),new Flower(),new Gun(),new OldExam(),new PaperHelp(),new Pen(),new Phone(),new TheKillBoot(),
                                 new PassLevel(),new Sword(),new TNT(),new Sportswear()};
    }


    /**
     * This method allows to refresh and delete the delete the old list of frameTime
     */
    public void refreshAndDelete()
    {
        listUpdate   = new ArrayList<>();
        memoryMobile = null;
    }


    /**
     * This method analyses the the object (About on the TMx)
     * @param id is the name of rectangle About on Tmx
     */
    public abstract void analyseIdMap(String id)throws Exception;


    /**
     * This method allows to analyse the object on maps to return the message
     * @param id is the id of the object on the maps
     */
    protected void analyseNormalGameIdMap(String id) throws Exception
    {
        if (!id.equals(actualID))
        {
            actualID = id;
            String[] word = id.split("_");
            if (word[0].equals("Room") && word.length >= 3)
                regulator.placeInOut(word[2],word[1]);
            else if (word[0].equals("Info") && word.length >= 2)
            {
                regulator.push(word[1]);
                event.notify(new OtherInformation(word[1]));
            }
            else
                throw new Exception();
        }
    }


    /**
     * This method allows to old game
     * @param pathAndFile  is the name path and the name file
     * @param play is the playingState to run graphic play
     * @param graphic is the graphicalSetting class
     */
    public abstract void oldGame(String pathAndFile,PlayingState play, GraphicalSettings graphic);


    /**
     * This method allows to save the game
     * @param pathAndFile is the path and name of file
     */
    public abstract void saveGame(String pathAndFile);


    /**
     * This methods allows to init the list the object graphic
     * @param pnj is the logic instance
     * @param graphic is the associate graphic instance
     */
    public void init(Character pnj, be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character graphic)
    {
        if (!graphicalMob.containsKey(pnj))
            graphicalMob.put(pnj,graphic);
        else
            graphicalMob.replace(pnj,graphic);
    }


    /**
     * This method return the mobile for a maps
     * @param maps is the maps (maps)
     * @return list of the mobile for this maps.
     */
    public ArrayList<Mobile> getMobile(Maps maps)
    {
        if (! listMobile.containsKey(maps))
            return new ArrayList<>();
        return listMobile.get(maps);
    }


    /**
     * This method allows to give the actual course of the player
     */
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
    public void newParty(String namePlayer, Type type, Gender gender, Difficulty difficulty)
    {
        placePosition = new PositionOnMaps();
        time = new TimeGame(new Date(16,9,2019,8,15));
        playerOne = new People(namePlayer,type, gender,difficulty);
        for (NameDialog name : NameDialog.values())
            listDialog.put(name,new DialogCharacter(name,this));
    }


    /**
     * This method returns the save of the game
     * @return the instance of save
     */
    public Saving getSave()
    {
        return save;
    }


    /**
     * */
    public void setMoneyGame(boolean haveMoney)
    {
        this.haveMoney = haveMoney;
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
        {
            //listMoving.get((maps = plc[new Random().nextInt(plc.length)])).add(new MovingPNJ(people.getBloc(), MobileType.Athletic, maps)); TODO
        }
        listMoving.get(Maps.Nimy).add(new MovingPNJ(Bloc.BA1,MobileType.Lambda,Maps.Nimy,Actions.Dialog));
    }


    /**
     * This method allows to give the character to refresh with the changed frame
     * @param maps is the actual maps of the player
     */
    protected void refreshList(Maps maps)
    {
        listUpdate = new ArrayList<>();
        listUpdate.add(playerOne);
        listUpdate.addAll(getMobile(maps));
        listUpdate.addAll(getMovingPnj(maps));
    }


    /**
     * This method return the list of the moving PNJ in the specific map TMX
     * @param maps is the maps
     * @return list of the moving pnj in the maps
     */
    public ArrayList<MovingPNJ> getMovingPnj(Maps maps)
    {
        if (! listMoving.containsKey(maps))
            return new ArrayList<>();
        return listMoving.get(maps);
    }


    /**
     * This method allows to give the actual quest to the graphic quest
     * @param qs is the graphic instance to quest
     */
    public void setGraphic(QuestShower qs, PlayingState ps)
    {
        if ( playerOne != null && qs != null && ps != null)
        {
            qs.setQuest(playerOne.getQuest());
            save.setGraphic(ps);
            playGraphic = ps;
        }
    }


    /***/
    public abstract MasterQuest actualQuest();


    /***/
    protected void placeItem()
    {
        if (playGraphic != null && mustPlaceItem)
        {
            for (Maps maps : Maps.values())
            {
                for (Items it : listItems.get(maps))
                   playGraphic.addItemToMap(it, placePosition.getPosition(it.getMaps(),it.idOfPlace()), maps.getMaps());
            }
            first = false;
        }
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

        if (haveMoney)
        {
            dealerOnMap = new Dealer(playerOne.getBloc());
            dealerOnMap.setMaps(Maps.Nimy);
            listMobile.get(Maps.Nimy).add(dealerOnMap);
        }
    }


    /***/
    public Dealer getDealerOnMap()
    {
        return dealerOnMap;
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
        createItems(actualQuest());
        createMobil(actualQuest());
        createMovingPnj(playerOne.getDifficulty());//TODO changed
    }


    public void deadMobile(Mobile mb)
    {
        listMobile.get(mb.getMaps()).remove(mb);
        deadMobile.add(mb);
        listUpdate.remove(mb);
        playerOne.winExperience(mb);

        for (Items it : mb.getInventory())
            playerOne.pushObject(it);//TODO check !

        if (mb.equals(memoryMobile))
            memoryMobile = null;
    }


    /**
     * This method allows to add the object of frameTime in the list to receive the time
     * @param refresh is a list of the frameTime
     */
    public void addRefresh(FrameTime ... refresh)
    {
        listUpdate.addAll(Arrays.asList(refresh));
    }


    /**
     * This method allows to receive the notification of the other class
     * @param notify is a notification
     */
    @Override
    public void update(Notification notify)
    {
        if (notify.getEvents().equals(Events.Dead) && ((Character)notify.getBuffer()).getType().equals(Character.TypePlayer.Computer))
            deadMobile((Mobile)notify.getBuffer());

        if (notify.getEvents().equals(Events.Dead) && ((Dead)notify).getBuffer().getType().equals(Character.TypePlayer.Human))
            refreshAndDelete();

        if (notify.getEvents().equals(Events.Answer) && notify.bufferNotEmpty())
            switchingDialog(((String)notify.getBuffer()));


        if (notify.getEvents().equals(Events.ChangeQuest))
            refreshQuest();

        if (notify.getEvents().equals(Events.ChangeDay))
            listCourse = playerOne.getPlanning().get(time.getDate().getDay());


        if (notify.getEvents().equals(Events.PlaceInMons) && notify.bufferNotEmpty())
        {
            refreshList(((PlaceInMons)notify).getBuffer());
            if (first)
                placeItem();
        }

    }


    /**
     * This method allows to update the class
     * @param dt who is the time between two windows
     */
    public void callMethod(double dt)
    {
        time.updateSecond(dt);
        if (memoryMobile != null && graphicalMob.containsKey(memoryMobile) && memoryMobile.inAttack())
         mobileMove(dt);
        for (Mobile mb : deadMobile)
            mobileLife(mb,dt);
        for (FrameTime up : listUpdate)
            up.update(dt);
        deadMobile.removeIf(Character::isLiving);
    }


    /**/
    public void mobileMove(double dt)
    {
        if (!graphicalMob.containsKey(memoryMobile) || !graphicalMob.containsKey(victimPlayer))
            return;
        be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character graphical = graphicalMob.get(memoryMobile);
        be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character player    = graphicalMob.get(victimPlayer);
        int displaceX = player.getPosX() - graphical.getPosX();
        int displaceY = player.getPosY() - graphical.getPosY();

        int x=0,y=0;
        int toMove = (int)Math.round(memoryMobile.getSpeed() * dt * 64);

        if(Math.sqrt(Math.pow(displaceX, 2) + Math.pow(displaceY, 2)) * 2 > graphical.getAttackRange()) { // *2 just to be sure it isn't at one pixel
            if (displaceX < 0)
                x = -toMove;
            else
                x = toMove;
            if (displaceY < 0)
                y = -toMove;
            else
                y = toMove;
        }
        if (Math.abs(displaceX) >= Math.abs(displaceY)) {
            if (displaceX >= 0)
                graphical.setOrientation(Orientation.Right);
            else
                graphical.setOrientation(Orientation.Left);
        } else {
            if (displaceY >= 0)
                graphical.setOrientation(Orientation.Top);
            else
                graphical.setOrientation(Orientation.Bottom);
        }
        graphical.move(x,y);
        if (x != 0 || y != 0)
            getEvent().notify(new PNJMoved(memoryMobile, graphical.getMapPos()));
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
            listUpdate.add(mobile);
        }
    }


    /**
     * This method to give the time of the game
     * @return time of game
     */
    public TimeGame getTime()
    {
        if (time == null)
            return new TimeGame(new Date(0,0,0,0,0));
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
        attackMethod(attacker, victim,true);
    }


    /**
     * This method allows to attack the other
     * @param victim is the victim on this attack
     * @param attacker  is the attacker on this attack
     * @param first     is the first attack of the player
     */
    public void attackMethod(Attack attacker, Attack victim, boolean first)
    {
        if (attacker.getType().equals(Character.TypePlayer.Human))
        {
            ((People)attacker).reduceEnergizing(State.attack);
        }
        if(victim.dodge() < 0.6 && attacker.canAttack())
        {
            if(attacker.howGun())
            {
                victim.loseAttack(calculateHits(attacker,victim,attacker.damageGun()));
                attacker.getGun().useGun();
            }
            else
                victim.loseAttack(calculateHits(attacker,victim,0));
            event.notify(new LaunchAttack((Character)victim,(Character)attacker));
        }
        if(attacker.getType().equals(Character.TypePlayer.Computer) && ((Character)victim).isLiving())
        {
            ((Mobile) attacker).letsGo(victim);
            memoryMobile = (Mobile)attacker;
            if (graphicalMob.containsKey(memoryMobile))
                graphicalMob.get(memoryMobile).expandAttackCircle();
        }
        if(victim.getType().equals(Character.TypePlayer.Computer) && first && ((Character)victim).isLiving())
            attackMethod(victim,attacker,false);
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
    public void meetCharacter(Character player1, Character player2)
    {
        if (player1 == null || player2 == null)
            return;
        if ((player1).getType().equals(Character.TypePlayer.Computer))
        {
            event.notify(new MeetOther(memoryMobile = (Mobile)player1));
            dialog= listDialog.get(((Mobile)player1).getDialog());
        }
        if ((player2).getType().equals(Character.TypePlayer.Computer))
        {
            event.notify(new MeetOther(memoryMobile = (Mobile)player2));
            dialog= listDialog.get(((Mobile)player2).getDialog());
        }
        Actions action = player1.getAction().comparable(player2.getAction());
        if (action.equals(Actions.Attack))
            attackMethod(memoryMobile, playerOne);
        else if (action.equals(Actions.Dialog))
        {
            if (startDialog)
            {event.add(Events.Answer,this);startDialog = false;}
            switchingDialog("Start");
        }
    }


    /**
     * This method allows to switch between people dialog and
     * @param answer is the answer of the dialog between two characters
     */
    public void switchingDialog(String answer)
    {
        if( dialog == null)
        {
            event.notify(new Dialog("ESC"));
            //event.remove(Events.Answer,this);//TODO
        }
        else if (answer.equals("Attack"))
        {
            attackMethod(playerOne,memoryMobile);
            event.notify(new Dialog("ESC"));
        }
        else
            dialog.analyzeAnswer(answer,memoryMobile);
    }


    /**
     * This method allows to reinitialisation of the player and save the old characteristic
     */
    public void reinitialisationPlayer()
    {
        debugSaving[0] = playerOne.getAgility();
        debugSaving[1] = playerOne.getDefence();
        debugSaving[2] = playerOne.getStrength();
        playerOne.reinitialization();
    }


    /**
     * This method allows to check the actual course of the player in the game
     */
    public void checkPlanning()
    {
        Date actu = time.getDate();
        if ((listCourse != null) && (actualCourse == null || actualCourse.getDate().getHour() + 2 <= actu.getHour()))
        {
            actualCourse = null;
            for (Course crs : listCourse)
            {
                if (crs.getDate().getHour()<= actu.getHour() && (crs.getDate().getHour()+2) > actu.getHour())
                    actualCourse = crs;
            }
        }
    }


    /**
     * @param mustPlaceItem If the items must be added to the map.
     */
    public void setMustPlaceItem(boolean mustPlaceItem)
    {
        this.mustPlaceItem = mustPlaceItem;
    }


    /**
     * This method allows to set the date
     * @param date is the date
     */
    public void setDate(Date date)
    {
        if (time == null)
            time = new TimeGame(date);
        time.setDate(date);
    }


    /**
     * Add a mobile
     * @param mob The mobile
     * @param map On which map he is
     * @param graphical Its graphical instance
     */
    public void addMobile(Mobile mob, Maps map, be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character graphical)
    {
        listMobile.get(map).add(mob);
        listUpdate.add(mob);
        graphicalMob.put(mob, graphical);
    }


    /**
     * Add a MovingPNJ
     * @param mob The mobile
     * @param map On which map he is
     * @param graphical Its graphical instance
     */
    public void addMoving(MovingPNJ mob, Maps map, be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character graphical)
    {
        listMoving.get(map).add(mob);
        listUpdate.add(mob);
        graphicalMob.put(mob, graphical);
    }


    /**
     * @param victimPlayer The player to attack
     */
    public void setVictimPlayer(People victimPlayer) {
        this.victimPlayer = victimPlayer;
    }


    /**
     * This methods return the list of all maps with the moving pnj on the maps
     * @return hashmap<Maps,List<MovingPnj>>
     */
    public HashMap<Maps, ArrayList<MovingPNJ>> getListMoving() {
        return listMoving;
    }
}
