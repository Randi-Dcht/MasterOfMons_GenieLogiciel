package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.Phone;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestCharacters
{

    /**
     * This method allows you to test the character class
     */
    @Test
    void PeopleObjectTest()
    {
        People people = new People("MasterOfMons", Type.normal, Gender.Men, Difficulty.Easy);
        Phone[] b = {new Phone(),new Phone(),new Phone(),new Phone(),new Phone(),new Phone()};
        Energizing e = new Energizing();
        for (int i = 0; i < 5 ; i++)
        {
            assertTrue(people.pushObject(b[i]),"add not over full");
        }
        assertFalse(people.pushObject(b[5]),"add over full");
        assertTrue(people.removeObject(b[0]),"remove true object");
        assertFalse(people.removeObject(e),"remove false object");
    }

    @Test
    void energyPeopleTest()
    {
        People people = new People("Junit",Type.normal,Gender.Men,Difficulty.Easy);
        double first = people.getEnergy();
        for (int i = 0; i < 100 ; i++ ){/*people.energy(1);*/}
        double second = people.getEnergy();
        assertTrue(first > second,"depency energy");

        people.setMaps(Maps.Kot);
        for (int i = 0; i < 100 ; i++ ){/*people.energy(1);*/}
        first = people.getEnergy();
        assertTrue(second < first,"add energy");
    }

    @Test
    void testingAttack()
    {
        SuperviserNormally sp = SuperviserNormally.getSupervisor();
        sp.newParty("Test",Type.beefy,Gender.Men,Difficulty.Easy);
        People p1 = sp.getPeople();
        Mobile p2 = new Mobile("Testing",Bloc.BA3, MobileType.Athletic, Actions.Never, NameDialog.Lambda);

        double memory = p1.getActualLife();
        sp.attackMethod(p2,p1);
        assertTrue(memory > p1.getActualLife(),"Check the life after the attack (people)");

        memory = p2.getActualLife();
        sp.attackMethod(p1,p2);
        assertTrue(memory > p2.getActualLife(),"Chack the life after the attack (PNJ)");
    }


    @Test
    void testingDialog()
    {
        SuperviserNormally.initNormallyGame();
        DialogCharacter dialog = new DialogCharacter(NameDialog.Lambda,Supervisor.getSupervisor());
        //TODO check
    }


    @Test
    void createMobilTest()
    {
        Mobile mobile = new Mobile("Test",Bloc.BA2,MobileType.Lambda,Actions.Attack,NameDialog.Lambda);
        assertTrue(mobile.getLevel()<=10 && mobile.getLevel() >= 5, "Check the level of the mobile");
        int total = (mobile.getLevel()-1)*3;
        assertSame(total,mobile.getAgility()+mobile.getDefence()+mobile.getStrength(), "check the calculus the points of the mobile ");
    }
}
