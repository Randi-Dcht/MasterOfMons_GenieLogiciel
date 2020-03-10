package be.ac.umons.mom.g02.Other;

import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class RandomName
{
    public static ArrayList<String> listName;
    public static String pathName= "NamePnj/Name.LambdaPNJ.txt";


    public static  void createList()
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
        }
        catch (Exception e)
        {
            Gdx.app.error("Error in the reading text of the conversation", e.getMessage());
        }
    }

    public static String giveName()
    {
        if (listName == null)
            createList();
        return listName.get(random());
    }

    public static int random()
    {
        int nb = new Random().nextInt(listName.size());
        if (nb >= 0 )
            return nb;
        return 0;
    }
}
