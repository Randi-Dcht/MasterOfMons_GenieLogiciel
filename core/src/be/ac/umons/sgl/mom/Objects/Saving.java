package be.ac.umons.sgl.mom.Objects;

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

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.People;
import com.badlogic.gdx.Gdx;


/**
 * This class allows to save this party, every end party or every time define.
 */
public class Saving implements Observer
{
    private String nameSave;
    private String oldSave;
    private People people;
    private DateFormat format = new SimpleDateFormat("dd/MM/yy_HH:mm:ss");//TODO : modifier en fct
    final static String prefixe = ""; //TODO : à modifier


    /**
     * This constructor allows give the class who are saving.
     * @param nameSave who is the name of the saving
     * @param people who is the people who play this game with Quest
     */
    public Saving(People people, String nameSave)
    {
        SuperviserNormally.getSupervisor().getEvent().add(Events.ChangeQuest,this);
        this.people = people;
        this.nameSave = nameSave;
    }


    /**
     * This method allows to play in the old game who is saving.
     * @param oldSave who is the full name of the saving (with the timeDate).
     */
    public Saving(String oldSave)
    {
        SuperviserNormally.getSupervisor().getEvent().add(Events.ChangeQuest,this);
        this.oldSave = oldSave;
        nameSave = cleanName(oldSave,0);
        playOldParty(oldSave);
    }


    /**
     * This method allows to clean the String of the file.
     * @param name who is teh name to clean
     * @param who who is place of word to return
     * @return word of name, after clean.
     */
    private String cleanName(String name,int who)
    {
        String[] list = name.split("_");
        return list[who];
    }


    /**
     * This method who is called to save this party
     */
    public void signal()
    {
        Date date = new Date();
        oldSave =  nameSave+"_"+format.format(date);
        /*newSave(people,oldSave);*/ System.out.println("False save automatic : "+ oldSave );
    }


    /**
     * This method allows to create a file with the save of the game
     * @param file who is the file with the saving game.
     * @param people qui est l'objet a safeguarded
     */
    private void newSave(People people,String file)
    {
        try
        {
            ObjectOutputStream sortie;
            sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(prefixe + file+".mom"))));
            sortie.writeObject(people);
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
    private void savingGraphic(Settings setting)
    {
        try
        {
            ObjectOutputStream sortie;
            sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(prefixe +oldSave+"_Settings"+".mom"))));
            sortie.writeObject(setting);
            sortie.close();
        }
        catch(IOException e)
        {
            Gdx.app.error("Error in the saving the game (out)", e.getMessage());
        }
    }


    /**
     * Cette méthode permet de reprendre les objets sauvguarder dans un fichier et démarer une nouvelle partie
     * @param file qui est le nom de fichier complet
     */
    private void playOldParty(String file)
    {
        try
        {
            ObjectInputStream entree;
            entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(prefixe + file+".mom"))));
            people = (People) entree.readObject();
        }
        catch(ClassNotFoundException | IOException e)
        {
            Gdx.app.error("Error in the replay the old game party (in)", e.getMessage());
        }
    }


    @Override
    public void update(Notification notify)
    {
        signal();
    }
}
