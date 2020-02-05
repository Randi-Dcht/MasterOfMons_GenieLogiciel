package be.ac.umons.sgl.mom.Quests;

import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import be.ac.umons.sgl.mom.Quests.Master.Bachelor1;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestQuest
{
    @Test
    void TestnextQuest()
    {
        People p = new People("Tesst", Type.normal);
        MasterQuest mq = new Bachelor1(p,null);
        mq.nextQuest();
        assertNull(mq.getChildren(),"quest after is null");
        assertNull(mq.getParent(),"quest before is null");
        mq.addProgress(100);
        assertNotNull(mq.getChildren(),"quest1 after isn't null");
        assertNotNull(p.getQuest().getParent(),"quest2 before isn't null");
        assertNull(p.getQuest().getChildren(),"quest2 after is null");
    }
}
