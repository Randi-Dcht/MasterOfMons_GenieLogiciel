package be.ac.umons.sgl.mom.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import be.ac.umons.sgl.mom.Events.Events;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Objects.Characters.People;

/**
 * Cette classe permet de créer une sauveguarde automatique du jeu ou à la fin de la partie
 * */
public class Saving implements Observer
{
    private String nameSave;
    private String oldSave;
    private People people;
    private GraphicalSettings graSet;
    private DateFormat format = new SimpleDateFormat("dd/MM/yy_HH:mm:ss");//TODO : modifier en fct
    final static String prefixe = ""; //TODO : à modifier

    /**
     * Permet de créer une instance de sauveguarde
     * @param nameSave qui est le nom de la partie
     * @param people qui est le joueur à sauveguarder
     * @param gs qui est les paramètres grapgiques à retenir
     * */
    public Saving(People people, String nameSave,GraphicalSettings gs) //TODO: ajouter les maps en safe !
    {
        this.people = people;
        this.nameSave = nameSave;
        graSet = gs;
    }

    /**
     * Permet d'appler quand on veut jouer une ancienne partie
     * @param oldSave qui est le nom complet de la partie (avec date)
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
     * Cette méthode doit être appeler régulièrement pour créer une sauveguarde
     * */
    public void Signal()
    {
        Date date = new Date();
        oldSave =  nameSave+"_"+format.format(date);
        /*newSave(people,graSet,oldSave);*/ System.out.println("False save automatic : "+ oldSave );
    }

    /**
     * Cette méthode permet de sauveguarder dans un fichier (.save) les objets utils
     * @param fichier qui est le nom de fichier complet
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
