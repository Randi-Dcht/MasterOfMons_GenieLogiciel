package be.ac.umons.mom.g02.Other;

import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class allows to give a random name to the mobile
 * The name of the mobile stocks in the file .txt
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class RandomName
{
    /**
     * This is a list with the all name doesn't choose
     */
    private static ArrayList<String> listName;
    /**
     * This is tha path to read the file with name
     */
    private static final String pathName= "NamePnj/Name.LambdaPNJ.txt";


    /**
     * This method allows to read the file with the name of the PNJ
     */
    private static void createList()
    {
       listName  = new ArrayList<>();
       String vertical;
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal(pathName)))));
            while ((vertical = line.readLine()) != null)
            {
                listName.add(vertical);
            }
            line.close();
        }
        catch (Exception e)
        {
            Gdx.app.error("Error in the reading text of the random name of the mobile", e.getMessage());
        }
    }


    /**
     * This method allows to give the random name for a PNJ in the game
     * @return the string's name
     */
    public static String giveName()
    {
        if (listName == null || listName.size()==0)
            createList();
        String name = listName.get(random(listName.size()));
        listName.remove(name);
        return name;
    }


    /**
     * This method return the number between max of size list and 0
     * @return number >= 0
     */
    public static int random(int max)
    {
        return Math.max(new Random().nextInt(max), 0);
    }
}
