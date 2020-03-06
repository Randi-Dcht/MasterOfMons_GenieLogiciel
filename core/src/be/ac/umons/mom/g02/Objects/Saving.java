package be.ac.umons.mom.g02.Objects;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import com.badlogic.gdx.Gdx;


/**
 * This class allows to save this party, every end party or every time define.
 * @author Umons_Group_2_ComputerScience
 */
public class Saving implements Observer
{
    private People people;
    private String path = "/tmp/";
    private String defaltName= "MasterOfMons_Save_NoneName.mom";


    /**
     * This constructor allows give the class who are saving.
     */
    public Saving() {}


    /**
     * @param people who is the people who play this game with Quest
     */
    public void setSaving(People people)
    {
        this.people = people;
        SuperviserNormally.getSupervisor().getEvent().add(Events.ChangeQuest,this);
    }


    /**
     * This method who is called to save this party
     */
    public void signal()
    {
        if (defaltName == null)
            newSave(people,path,SuperviserNormally.getSupervisor().getTime().getDate());
        else
            newSave(people,defaltName,SuperviserNormally.getSupervisor().getTime().getDate());
    }


    /**
     * @param name who is the name of the saving
     */
    public void setNameSave(String name)
    {
        path = name;
        defaltName = null;
    }


    /**
     * This method allows to create a file with the save of the game
     * @param file who is the file with the saving game.
     * @param people qui est l'objet a safeguarded
     */
    private void newSave(People people, String file, be.ac.umons.mom.g02.Other.Date date)
    {
        try
        {
            ObjectOutputStream sortie;
            sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(file))));
            sortie.writeObject(people);
            sortie.writeObject(date);
            sortie.close();
        }
        catch(IOException e)
        {
            Gdx.app.error("Error in the saving the game (out)", e.getMessage());
        }
    }


    /**
     * This method allows to save the element of graphic param
     * @param setting who is the class with the param to save.
     */
    public void savingGraphic(Settings setting/*,String nameSave*/)
    {
        try
        {
            ObjectOutputStream sortie;
            sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(path +"MasterOfMons_Settings.save"))));
            sortie.writeObject(setting);
            sortie.close();
        }
        catch(IOException e)
        {
            Gdx.app.error("Error in the saving the game (out)", e.getMessage());
        }
    }


    /**
     * This method allows to give the saving of the graphic parameters
     * @param file is the name of the file
     */
    public Settings getSavingGraphic(String file)
    {
        try
        {
            ObjectInputStream entree;
            entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file))));
            return (Settings) entree.readObject();
        }
        catch(ClassNotFoundException | IOException e)
        {
            Gdx.app.error("Error in the replay the get of setting (in)", e.getMessage());
        }
        return null;
    }


    /**
     * This method allows you to resume the objects saved in a file and start a new game.
     * @param file which is the full file name
     */
    public void playOldParty(String file ,GraphicalSettings gs)
    {
        try
        {
            ObjectInputStream entree;
            entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file))));
            people = (People) entree.readObject();
            be.ac.umons.mom.g02.Other.Date date = (be.ac.umons.mom.g02.Other.Date) entree.readObject();
            SuperviserNormally.getSupervisor().oldGame(people,date,gs);
            SuperviserNormally.getSupervisor().getEvent().add(Events.ChangeQuest,this);
            path = file;
        }
        catch(ClassNotFoundException | IOException e)
        {
            Gdx.app.error("Error in the replay the old game party (in)", e.getMessage());
        }
    }


    /**
     * This method allows to receive the new notification with the event
     * @param notify is the notification
     */
    @Override
    public void update(Notification notify)
    {
        signal();
    }
}
