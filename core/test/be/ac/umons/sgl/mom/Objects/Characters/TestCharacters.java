package be.ac.umons.sgl.mom.Objects.Characters;

import be.ac.umons.sgl.mom.Enums.*;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Items.Battery;
import be.ac.umons.sgl.mom.Objects.Items.Energizing;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        Battery[] b = {new Battery(),new Battery(),new Battery(),new Battery(),new Battery(),new Battery()};
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
        for (int i = 0; i < 100 ; i++ ){people.energy(1);}
        double second = people.getEnergy();
        assertTrue(first > second,"depency energy");

        people.setPlace(Place.Kot);
        for (int i = 0; i < 100 ; i++ ){people.energy(1);}
        first = people.getEnergy();
        assertTrue(second < first,"add energy");
    }

    @Test
    void testingAttack() throws InterruptedException
    {
        SuperviserNormally sp = SuperviserNormally.getSupervisor();
        sp.newParty("Test",Type.beefy,null,Gender.Men,Difficulty.Easy);
        People p1 = sp.getPeople();
        Mobile p2 = new Mobile("Testing",Bloc.BA3, MobileType.Athletic, Actions.Never);

        double memory = p1.getLife();
        sp.attackMethod(p2,p1);
        assertTrue(memory > p1.getLife());

        memory = p2.getLife();
        sp.attackMethod(p1,p2);
        assertTrue(memory > p2.getLife());
    }

    @Test
    void testingDialog()
    {}

    @Test
    void createMobilTest()
    {}
}
