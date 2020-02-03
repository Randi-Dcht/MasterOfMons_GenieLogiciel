package be.ac.umons.sgl.mom.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Objects.Characters.People;

/**
 * This class allows to save this party, every end party or every time define.
 */
public class Saving implements Observer
{
    private String nameSave;
    private String oldSave;
    private People people;
    private GraphicalSettings graSet;
    private DateFormat format = new SimpleDateFormat("dd/MM/yy_HH:mm:ss");//TODO : modifier en fct
    final static String prefixe = ""; //TODO : à modifier

    /**
     * This constructor allows give the class who are saving.
     * @param nameSave who is the name of the saving
     * @param people who is the people who play this game with Quest
     * @param gs who is the graphic param to save.
     */
    public Saving(People people, String nameSave,GraphicalSettings gs) //TODO: ajouter les maps en safe !
    {
        this.people = people;
        this.nameSave = nameSave;
        graSet = gs;
    }

    /**
     * This method allows to play in the old game who is saving.
     * @param oldSave who is the full name of the saving (with the timeDate).
     * */
    public Saving(String oldSave)
    {
        this.oldSave = oldSave;
        nameSave = cleanName(oldSave,0);
        playOldParty(oldSave);
    }

    /**
     * Cette méthode permet de nettoyer le nom de la partie
     * @param name qui est le nom a nettoyer
     * @param who qui est la place du mot à récupérer
     * @return mot(string) qui est celui voulu*/
    private String cleanName(String name,int who)
    {
        String[] list = name.split("_");
        return list[who];
    }

    /**
     * This method who is called to save this party
     * */
    public void Signal()
    {
        Date date = new Date();
        oldSave =  nameSave+"_"+format.format(date);
        /*newSave(people,graSet,oldSave);*/ System.out.println("False save automatic : "+ oldSave );
    }

    /**
     * This method allows to create a file with the save of the game
     * @param fichier
     * @param people qui est l'objet a sauveguarder
     * */
    private void newSave(People people,GraphicalSettings gs, String fichier)
    {
        try
        {
            ObjectOutputStream sortie;
            sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(prefixe + fichier+".mom"))));
            sortie.writeObject(people);
            sortie.close();
        }
        catch(IOException e){}
    }

    /**
     * Cette méthode permet de reprendre les objets sauvguarder dans un fichier et démarer une nouvelle partie
     * @param fichier qui est le nom de fichier complet
     * */
    private void playOldParty(String fichier)
    {
        try
        {
            ObjectInputStream entree;
            entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(prefixe + fichier+".mom"))));
            people = (People) entree.readObject();
            graSet = (GraphicalSettings) entree.readObject();
        }
        catch(ClassNotFoundException  e){}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }

    @Override
    public void update(Events event) {

    }
}
