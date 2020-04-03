package be.ac.umons.mom.g02.Regulator;

import be.ac.umons.mom.g02.Enums.*;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator.SupervisorMultiPlayer;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.Phone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SupervisorTest extends SuperviserNormally
{

    @Test
    public void TestInitSupervisor()
    {
        SuperviserNormally.initNormallyGame();
        assertEquals(Supervisor.getSupervisor().getClass(), SuperviserNormally.class,"Check if the supervisor class is the single people");

        SupervisorDual.initDual();
        assertEquals(Supervisor.getSupervisor().getClass(), SupervisorDual.class,"Check if the supervisor class is the two peoples");
    }


    /**
     * This method allows to check if the object doesn't create before the init game else the game crash
     */
    @Test
    public void TestNewParty()
    {
        /*before new party*/
        //assertNull(playerOne,"There isn't player instance");
        assertTrue(listItems == null  || listItems.size() == 0);
        assertTrue(listMobile == null || listMobile.size() == 0);
        assertTrue(listMoving == null || listMoving.size() == 0);
        /*after init new party*/
        SuperviserNormally.initNormallyGame();
        setMustPlaceItem(false);
        newParty("Test", Type.athletic,Gender.Men, Difficulty.Easy);
        assertNotNull(playerOne);
        assertNotNull(listItems);
        assertNotNull(listMobile);
        assertNotNull(listMoving);
    }


    @Test
    public void TestTheDeadMobile()
    {
        SuperviserNormally.initNormallyGame();
        Mobile bf;
        setMustPlaceItem(false);
        newParty("Test",Type.athletic,Gender.Men,Difficulty.Easy);
        listMobile.get(Maps.GrandAmphi).add(bf = new Mobile("Test",Bloc.BA1,MobileType.Lambda,Actions.Attack,NameDialog.Lambda));
        bf.setMaps(Maps.GrandAmphi);
        bf.addObject(new Phone(),new Phone());
        Mobile mb = listMobile.get(Maps.GrandAmphi).get(0);
        int sizeMbMap  = listMobile.get(Maps.GrandAmphi).size();
        int sizeUpdate = listUpdate.size();
        int sizeDead   = deadMobile.size();
        List<Items> list = Supervisor.getPeople().getInventory();
        deadMobile(mb);
        assertFalse(sizeMbMap != listMobile.get(Maps.GrandAmphi).size() && sizeUpdate != listUpdate.size() && sizeDead != deadMobile.size());
        assertTrue(list.containsAll(Supervisor.getPeople().getInventory()));
    }
}
