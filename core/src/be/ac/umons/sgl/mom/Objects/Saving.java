package be.ac.umons.sgl.mom.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
  private String nameSave;
  private String oldSave;
  private People people;
  private DateFormat format = new SimpleDateFormat("dd-MM-yy-HH-mm-ss");//TODO : modifier en fct
  final static String prefixe = ""; //TODO : à modifier

  public Saving(People people, String nameSave)
  {
    this.people = people;
    this.nameSave = nameSave;
  }

  public Saving(String oldSave)
  {
    this.oldSave = oldSave;
    nameSave = cleanName(oldSave,0);
    playOldParty(oldSave);
  }

  private String cleanName(String name,int who)
  {
    String[] list = name.split("_");
    return list[who];
  }

  public void Signal() //TODO : appeler toutes les 10 minutes ou fin
  {
    Date date = new Date();
    oldSave =  nameSave+"_"+format.format(date);
    newSave(people,oldSave);
  }
  
  private void newSave(People people, String fichier)
  {
    try
    {
      ObjectOutputStream sortie;
      sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(prefixe + fichier+".save"))));
      sortie.writeObject(people);
      sortie.close();
    }
    catch(IOException e){}
  }

  private void playOldParty(String fichier)
  {
    try
    {
      ObjectInputStream entree;
      entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(prefixe + fichier+".save"))));
      people = (People) entree.readObject();
    }
    catch(ClassNotFoundException  e){}
    catch (FileNotFoundException e){}
    catch (IOException e){}
  }
}
