package be.ac.umons.mom.g02.Regulator;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Events.Notifications.LaunchAttack;
import be.ac.umons.mom.g02.Events.Notifications.MeetOther;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Notifications.PlaceInMons;
import be.ac.umons.mom.g02.Objects.Characters.DialogCharacter;
import be.ac.umons.mom.g02.Events.Event;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.QuestShower;
import be.ac.umons.mom.g02.Objects.Characters.Attack;
import be.ac.umons.mom.g02.Objects.Characters.Character;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Characters.Social;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.Flower;
import be.ac.umons.mom.g02.Objects.Items.Gun;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.MyPlacePosition;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.PaperHelp;
import be.ac.umons.mom.g02.Objects.Items.Pen;
import be.ac.umons.mom.g02.Objects.Items.Phone;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;

import java.util.ArrayList;
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
     * This method return the supervisor instance
     * @return supervisor instance (one instance)
     */
    public static Supervisor getSupervisor()
    {
        if (instance == null)//TODO
          SuperviserNormally.initNormallyGame();  //Gdx.app.error("Error in the Supervisor by bad initialization", String.valueOf(new ExceptionInInitializerError()));
        return instance;
    }

    /*--------------------------------------------------------------------------------------------------------*/

    /**
     * The people who play this party
     */
    protected People playerOne;
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
     * This is the instance of the Graphic
     */
    protected GraphicalSettings graphic;
    /**
     * This is the time in the game
     */
    protected TimeGame time;
    /**
     * This is the events instance
     */
    protected Event event;
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
    /***/
    protected MyPlacePosition placePosition;
    /***/
    protected boolean first = true;
    /**
     * The instance of the regulator class*
     */
    protected Regulator regulator;


    /***/
    protected Supervisor()
    {
        for (Maps plt : Maps.values())
            listMap.put(plt.getMaps(),plt);
        event = new Event();
        event.add(this, Events.Dead,Events.ChangeDay,Events.ChangeHour,Events.PlaceInMons);
        save = new Saving();
        associateLesson();
        graphicalMob = new HashMap<>();
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
     * This methods allows to return the people of this game
     * @return people of this game
     */
    public  People getPeople()
    {
        return playerOne;
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


    /***/
    public Items[] getAllItems()//TODO optimize
    {
        return new Items[]{new Energizing(),new Flower(),new Gun(),new OldExam(),new PaperHelp(),new Pen(),new Phone(),new TheKillBoot()};
    }


    /***/
    public void refreshAndDelete()
    {
        listUpdate   = new ArrayList<>();
        memoryMobile = null;
    }


    /***/
    public abstract void analyseIdMap(String id)throws Exception;


    /***/
    public abstract void oldGame(String pathAndFile,PlayingState play, GraphicalSettings graphic);


    /***/
    public abstract void saveGame(String pathAndFile);


    /***/
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
    public void newParty(String namePlayer, Type type, GraphicalSettings graphic, Gender gender, Difficulty difficulty)
    {
        placePosition = new MyPlacePosition();
        time = new TimeGame(new Date(16,9,2019,8,15));
        playerOne = new People(namePlayer,type, gender,difficulty);
        this.graphic = graphic;
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
            //listMoving.get((maps = plc[new Random().nextInt(plc.length)])).add(new MovingPNJ(people.getBloc(), MobileType.Athletic, maps));
        }
        listMoving.get(Maps.Nimy).add(new MovingPNJ(Bloc.BA1,MobileType.Lambda,Maps.Nimy,Actions.Dialog));
    }


    /**
     * This method allows to give the character to refresh with the changed frame
     * @param maps is the actual maps of the player
     */
    private void refreshList(Maps maps)
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
        if ( playerOne != null && qs != null && playerOne.getQuest() != null && ps != null)
        {
            qs.setQuest(playerOne.getQuest());
            save.setGraphic(ps);
            playGraphic = ps;
        }
    }


    /***/
    protected void placeItem()
    {
        if (playGraphic != null)
        {
            for (Maps maps : Maps.values())
            {
                for (Items it : listItems.get(maps))
                   playGraphic.addItemToMap(it, placePosition.getPosition(it.getMaps()));
            }
            first = false;
        }
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
        createItems(playerOne.getQuest());
        createMobil(playerOne.getQuest());
        createMovingPnj(playerOne.getDifficulty());
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
            listUpdate.remove(mb);
            playerOne.winExperience(mb);
            if (mb.equals(memoryMobile))
                memoryMobile = null;
        }

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
            if (first)//TODO changer et avec chaque position plus prècis !
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
        be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character graphical = graphicalMob.get(memoryMobile);
        be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.Character player    = graphicalMob.get(playerOne);
        int displaceX = player.getPosX() - graphical.getPosX();
        int displaceY = player.getPosY() - graphical.getPosY();

        int x=0,y=0;
        int toMove = (int)Math.round(memoryMobile.getSpeed() * dt * 64);

        if(displaceX > toMove+128 || displaceX < -toMove-128 || displaceY > toMove+64 || displaceY < -toMove-64) {
            if (displaceX < 0) {
                x = -toMove;
                graphical.setOrientation(Orientation.Left);
            } else {
                x = toMove;
                graphical.setOrientation(Orientation.Right);
            }
            if (displaceY < 0) {
                y = -toMove;
                graphical.setOrientation(Orientation.Bottom);
            } else {
                y = toMove;
                graphical.setOrientation(Orientation.Top);
            }
        }
        graphical.move(x,y);
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
        attackMethod(attacker,victim,true);
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
            event.notify(new LaunchAttack(memoryMobile));
        }
        if(victim.dodge() < 0.6 && attacker.canAttacker())
        {
            if(attacker.howGun())
            {
                victim.loseAttack(calculateHits(attacker,victim,attacker.damageGun()));
                attacker.getGun().useGun();
            }
            else
                victim.loseAttack(calculateHits(attacker,victim,0));
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
    public void meetCharacter(Social player1, Social player2)//TODO upgrade pour moins de clss
    {
        if (((Character)player1).getType().equals(Character.TypePlayer.Computer))
        {
            event.notify(new MeetOther(memoryMobile = (Mobile)player1));
            dialog= listDialog.get(((Mobile)player1).getDialog());//TODO
        }
        if (((Character)player2).getType().equals(Character.TypePlayer.Computer))
        {
            event.notify(new MeetOther(memoryMobile = (Mobile)player2));
            dialog= listDialog.get(((Mobile)player2).getDialog());//TODO
        }
        Actions action = player1.getAction().comparable(player2.getAction());
        if (action.equals(Actions.Attack))
            attackMethod(memoryMobile, playerOne);
        else if (action.equals(Actions.Dialog))
        {
            event.add(Events.Answer,this);
            switchingDialog("Start");
        }
    }


    /**
     * This method allows to switch between people dialog and
     * @param answer is the answer of the dialog between two characters
     */
    public void switchingDialog(String answer)
    {
        if(answer.equals("ESC") || dialog == null)
        {
            event.notify(new Dialog("ESC"));
            //event.remove(Events.Answer,this);//TODO
        }
        if (answer.equals("Attack"))
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

}
