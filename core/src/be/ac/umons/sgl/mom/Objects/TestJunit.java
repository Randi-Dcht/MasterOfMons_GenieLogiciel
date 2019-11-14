package be.ac.umons.sgl.mom;
public class TestJunit
{
 /**
 *Cette méthode permet de tester les méthodes de la classe Lesson
 */
  @Test
  public void TestOfLesson()
  {
    assertTrue(true);
    Lesson l1 = Lesson.MI1;
    Lesson l2 = Lesson.ecopol;
    Lesson l3 = Lesson.statistique;
    int levelPlayer = 2;

    assertTrue("take the lesson of level player",l1.take(levelPlayer));
    assertTrue("take the lesson of level player",l2.take(levelPlayer));
    assertFalse("take the lesson of level player",l3.take(levelPlayer));
    assertTrue("Obligatory of lesson",l1.obligatoryCourse());
    assertSame("same place",l1.location(),Place.Nimy);
  }

  @Test
 public void PeopleObjectTest()
 {
   People people = new People("MasterOfMons",5,5,5);
   Battery[] b = {new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0),new Battery(0,0)};
   Energizing e = new Energizing(0,0);
   for (int i = 0; i < 5 ; i++)
   {
     assertTrue("add not over full",people.pushObject(b[i]));
   }
   assertFalse("add over full",people.pushObject(b[5]));
   assertTrue("remove object",people.removeObject(b[0]));
   assertFalse("remove false object",people.removeObject(e));
 }
}
