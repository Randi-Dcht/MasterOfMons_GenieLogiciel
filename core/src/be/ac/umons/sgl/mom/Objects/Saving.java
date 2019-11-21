package be.ac.umons.sgl.mom.Objects;

import java.util.*;
import java.io.*;
import be.ac.umons.sgl.mom.GraphicalObjects.*;
import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Enums.Place;
import be.ac.umons.sgl.mom.Enums.State;
import be.ac.umons.sgl.mom.Enums.Type;
import be.ac.umons.sgl.mom.Quests.Under.*;
import be.ac.umons.sgl.mom.Quests.Master.*;
import be.ac.umons.sgl.mom.Quests.Quest;

public class Saving
{
  private String oldSave;
  
  public void newSave(People people, String fichier)
  {
    try
    {
      ObjectOutputStream sortie;
      sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("save.save"))));
      sortie.writeObject(people);
      sortie.close();
    }
    catch(IOException e){}
  }

  public void playOldParty(String fichier)
  {
    try
    {
      ObjectInputStream entree;
      entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("save.save"))));
      People p = (People) entree.readObject();
    }
    catch(ClassNotFoundException  e){}
    catch (FileNotFoundException e){}
    catch (IOException e){}
  }
}
