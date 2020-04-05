package be.ac.umons.mom.g02.Quests;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Enums.Places;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Notifications.Dead;
import be.ac.umons.mom.g02.Events.Notifications.EntryPlaces;
import be.ac.umons.mom.g02.Events.Notifications.LaunchAttack;
import be.ac.umons.mom.g02.Events.Notifications.LowSomething;
import be.ac.umons.mom.g02.Events.Notifications.MeetOther;
import be.ac.umons.mom.g02.Events.Notifications.OtherInformation;
import be.ac.umons.mom.g02.Events.Notifications.PlaceInMons;
import be.ac.umons.mom.g02.Events.Notifications.UseItem;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.Pen;
import be.ac.umons.mom.g02.Objects.Items.Phone;
import be.ac.umons.mom.g02.Quests.Master.SuccessfulYear;
import be.ac.umons.mom.g02.Quests.Under.BattleForPlace;
import be.ac.umons.mom.g02.Quests.Under.CheckStudy;
import be.ac.umons.mom.g02.Quests.Under.FollowLesson;
import be.ac.umons.mom.g02.Quests.Under.FreeTimeMons;
import be.ac.umons.mom.g02.Quests.Under.GoToLesson;
import be.ac.umons.mom.g02.Quests.Under.LowEnergizing;
import be.ac.umons.mom.g02.Quests.Under.MeetManyPeople;
import be.ac.umons.mom.g02.Quests.Under.ReadInformation;
import be.ac.umons.mom.g02.Quests.Under.SuccesfulYear;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Master.MyFirstYear;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestQuest
{

    @BeforeAll
    static void before()
    {
        SuperviserNormally.initNormallyGame();
        SuperviserNormally.getSupervisor().setMustPlaceItem(false);
        SuperviserNormally.getSupervisor().newParty("Test",Type.athletic,Gender.Men,Difficulty.Easy);
    }


    @Test
    void TestnextQuest()
    {
        People p = SuperviserNormally.getPeople();
        MasterQuest mq = new MyFirstYear(p,null,Difficulty.Easy);
        mq.nextQuest();
        assertNull(mq.getChildren(),"quest after is null");
        assertNull(mq.getParent(),"quest before is null");
        mq.addProgress(100);
        for (int i = 0; i < 40; i++){p.upLevel();}
        assertNotNull(mq.getChildren(),"quest1 after isn't null");
        Assertions.assertNotNull(p.getQuest().getParent(),"quest2 before isn't null");
        Assertions.assertNull(p.getQuest().getChildren(),"quest2 after is null");
    }


    @Test
    void TestNextWithLevel()
    {
        People p = SuperviserNormally.getPeople();
        People pTest = new People("Tesst", Type.normal, Gender.Men ,Difficulty.Easy);
        SuccessfulYear quest = new SuccessfulYear(pTest,null,Difficulty.Easy);
        quest.addProgress(100);
        assertNull(quest.getChildren());
        for (int i = 0; i < 60;i++){pTest.upLevel();}
        assertNotSame(pTest.getQuest(),quest,"The level of people is goog to give the child quest");
    }


    @Test
    public void TestBattle()
    {
        Supervisor.getPeople().setMaps(Maps.GrandAmphi);
        Mobile mob = new Mobile("Test", Bloc.BA2,MobileType.Lambda,Actions.Attack,NameDialog.Lambda);
        BattleForPlace battle = new BattleForPlace(Supervisor.getSupervisor().actualQuest(),30,Supervisor.getPeople());
        double old = battle.getProgress();
        battle.evenActivity(new LaunchAttack(mob,Supervisor.getPeople()));//first attack mobile
        assertTrue(old < battle.getProgress());
        old = battle.getProgress();
        battle.evenActivity(new LaunchAttack(mob,Supervisor.getPeople()));//same mobile attack
        assertEquals(old, battle.getProgress());
        battle.evenActivity(new Dead(mob));
        assertTrue(old < battle.getProgress());
    }


    @Test
    public void TestStudy()
    {
        CheckStudy study = new CheckStudy(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        Supervisor.getPeople().setMaps(Maps.Kot);
        Supervisor.getPeople().setPlaceMaps(Places.StudyRoom);
        double old = study.getProgress();
        study.evenActivity(new PlaceInMons());
        assertTrue(old < study.getProgress());
    }


    @Test
    public void TestFollow()
    {
        FollowLesson follow = new FollowLesson(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        double old = follow.getProgress();
        follow.evenActivity(new UseItem(new Pen(),Supervisor.getPeople()));//pen bad item
        assertEquals(old, follow.getProgress());
        follow.evenActivity(new UseItem(new OldExam(),Supervisor.getPeople()));//oldExam good item
        assertTrue(old < follow.getProgress());
    }


    @Test
    public void TestFreeTime()
    {
        FreeTimeMons freeTime = new FreeTimeMons(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        Mobile mb = new Mobile("Test",Bloc.BA1,MobileType.Lambda,Actions.Dialog,NameDialog.Lambda);
        Supervisor.getPeople().setMaps(Maps.DeVinci);
        double old = freeTime.getProgress();
        freeTime.evenActivity(new MeetOther(mb));
        assertFalse(old < freeTime.getProgress());
    }


    @Test
    public void TestGoTo()
    {
        GoToLesson go = new GoToLesson(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        double old = go.getProgress();
        go.evenActivity( new EntryPlaces(Places.StudyRoom));
        assertTrue(old < go.getProgress());
    }


    //@Test
    public void TestHelpMe()
    {
        //TODO
    }


    //@Test
    public void TestSoulMate()
    {
        //TODO
    }


    @Test
    public void TestLow()
    {
        LowEnergizing low = new LowEnergizing(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        double old = low.getProgress();
        Mobile mobile = new Mobile("Test",Bloc.BA1,MobileType.Lambda,Actions.Attack,NameDialog.Lambda);
        low.evenActivity(new LaunchAttack(mobile,Supervisor.getPeople()));
        assertTrue(old < low.getProgress());
        old = low.getProgress();
        Supervisor.getPeople().setEnergy(9);
        Supervisor.getEvent().notify(new LowSomething(LowSomething.TypeLow.Energy));
        assertFalse(old < low.getProgress());
    }


    @Test
    public void TestMeetMany()
    {
        MeetManyPeople many = new MeetManyPeople(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        Mobile mb = new Mobile("Test",Bloc.BA1,MobileType.Lambda,Actions.Attack,NameDialog.Lambda);
        double old = many.getProgress();
        many.evenActivity(new UseItem(new Phone(),Supervisor.getPeople()));
        assertFalse(old < many.getProgress());
        old = many.getProgress();
        many.evenActivity(new MeetOther(mb));//first meet
        assertTrue(old < many.getProgress());
        old = many.getProgress();
        many.evenActivity(new MeetOther(mb));//second meet
        assertEquals(old, many.getProgress());
    }


    @Test
    public void TestRead() throws Exception
    {
        ReadInformation read = new ReadInformation(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        double old = read.getAdvancement();
        read.evenActivity(new OtherInformation("Info_MemoryPentagone"));
        assertTrue(old < read.getAdvancement());
        old = read.getAdvancement();
        read.evenActivity(new OtherInformation("Info_MemoryPentagone"));
        assertEquals(old, read.getAdvancement());
    }


    @Test
    public void TestSuccess()
    {
        SuccesfulYear success = new SuccesfulYear(Supervisor.getSupervisor().actualQuest(),10,Supervisor.getPeople());
        double old = success.getAdvancement();
        success.evenActivity(new EntryPlaces());//TODO
    }
}
