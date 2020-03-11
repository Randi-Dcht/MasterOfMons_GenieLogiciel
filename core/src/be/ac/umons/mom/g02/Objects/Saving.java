package be.ac.umons.mom.g02.Objects;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.OnMapObjects.MapObject;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;


/**
 * This class allows to save this party, every end party or every time define.
 * @author Umons_Group_2_ComputerScience
 */
public class Saving implements Observer
{

    /**
     * The name of the file setting
     */
    private static final String SETTINGS_FILE_NAME = "MasterOfMons.settings.mom";
    /**
     * The instance of the people
     */
    private People people;
    /**
     * The path by default
     */
    private String path = "/tmp/";
    /**
     * The name by default of the file
     */
    private String defaltName= "MasterOfMons_Save_NoneName.mom";
    /**
     * This is the instance of the playing state graphic
     */
    private PlayingState playingState;


    /**
     * This constructor allows give the class who are saving.
     */
    public Saving() {}


    /**
     * @param people who is the people who play this game with Quest
     */
    public void setLogic(People people)
    {
        this.people = people;
        Supervisor.getSupervisor().getEvent().add(Events.ChangeQuest,this);
    }


    /**
     * This methods to give the instance of the playing state graphic
     * @param playingState is the instance of playingState
     */
    public void setGraphic(PlayingState playingState)
    {
        this.playingState = playingState;
    }


    /**
     * This method who is called to save this party
     */
    public void signal()
    {
        if (defaltName == null)
            newSave(people,path,Supervisor.getSupervisor().getTime().getDate());
        else
            newSave(people,defaltName,Supervisor.getSupervisor().getTime().getDate());
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
            sortie.writeObject(playingState.getPlayerPosition());
            sortie.writeObject(playingState.getItemsOnMap());
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
    public void savingGraphic(Settings setting)
    {
        try
        {
            ObjectOutputStream sortie;
            sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(SETTINGS_FILE_NAME))));
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
     */
    public static Settings getSavingGraphic()
    {
        try
        {
            if (new File(SETTINGS_FILE_NAME).exists()) {
                ObjectInputStream entree;
                entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(SETTINGS_FILE_NAME))));
                return (Settings) entree.readObject();
            }
            return new Settings();
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
    public void playOldParty(String file , GraphicalSettings gs, PlayingState play)//TODO add playingState param
    {
        try
        {
            ObjectInputStream entree;
            entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file))));
            people = (People) entree.readObject();
            be.ac.umons.mom.g02.Other.Date date = (be.ac.umons.mom.g02.Other.Date) entree.readObject();
            play.initMap(people.getMaps().getMaps());
            play.setPlayerPosition((Point)entree.readObject());
            play.addItemsToMap((MapObject.OnMapItem[])entree.readObject());
            SuperviserNormally.getSupervisor().oldGame(people,date,gs);
            Supervisor.getSupervisor().getEvent().add(Events.ChangeQuest,this);
            path = file;
        }
        catch(ClassNotFoundException | IOException e)
        {
            Gdx.app.error("Error in the replay the old game party (in)", e.getMessage());
        }
    }


    /***/
    public void playOldParty(String file , GraphicalSettings gs)
    {
        if (playingState != null)
            playOldParty(file,gs,playingState);
        else
            Gdx.app.error("Error in play an old game with PlayingState","Null pointer");
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
