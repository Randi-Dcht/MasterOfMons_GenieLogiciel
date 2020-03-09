package be.ac.umons.mom.g02.Extensions.Multiplayer.Regulator;

import be.ac.umons.mom.g02.Objects.Characters.People;
import com.badlogic.gdx.Gdx;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Saving
{
    /**
     * This method allows you to resume the objects saved in a file and start a new game.
     * @param file which is the full file name
     */
    public static People getPlayer(String file)//TODO add playingState param
    {
        try
        {
            ObjectInputStream entree;
            entree = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(file))));
            return (People) entree.readObject();
        }
        catch(ClassNotFoundException | IOException e)
        {
            Gdx.app.error("Error in the replay the old game party (in)", e.getMessage());
        }
        return null;
    }
}
