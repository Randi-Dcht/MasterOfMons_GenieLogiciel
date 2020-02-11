package be.ac.umons.sgl.mom.Objects.Characters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.badlogic.gdx.Gdx;

/**
 * This class allows to read the file with the speech of people and PNJ.
 * This class follow the design pattern singleton.
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */
public class ReadConversation
{
    /*This HashMap stock the dialog of the all PNJ in the game*/
    private HashMap<String,String> pnj;
    /*This HashMap stock the dialog of the people in the game*/
    private HashMap<String, ArrayList<String>> people;
    /*This is a single instance of the ReadConversation*/
    private static ReadConversation instance = new ReadConversation();


    /**
     * This method return the single instance of this class
     * @return instance of this class
     */
    public static ReadConversation getInstance()
    {
        return instance;
    }


    /**
     * This constructor define this class who read a file Id and stock them
     */
    private ReadConversation()
    {
        pnj = getPnj();
        people = getPeople();
    }


    /***/
    public ArrayList<String> choosePeople(String answer)
    {
        return people.get(answer);
    }


    /***/
    public String getAnswer(String answer)
    {
        return pnj.get(answer);
    }


    /**
     * This method return the dialog ID of the people
     * @return hashMap of dialog
     */
    HashMap<String,ArrayList<String>> getDialogPeople()
    {
        return people;
    }


    /**
     * This method return the dialog ID of the PNJ
     * @return hashMap of dialog
     */
    HashMap<String,String> getDialogPNJ()
    {
        return pnj;
    }


    /**
     * This method allows to read the file with the speech of PNJ when the people speak.
     * @return Map with the key is the possibility answer of people and other is the response
     */
    private HashMap<String,ArrayList<String>> getPeople()
    {
        String vertical;
        String[] split;
        HashMap<String,ArrayList<String>> list = new HashMap<>();
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal("Conversations/Conversation.people.id")))));
            while ((vertical = line.readLine()) != null)
            {
                if(vertical.charAt(0) != '%' &&  (split = vertical.split("/")).length >= 2 )
                {
                    list.put(split[0],new ArrayList<>(Arrays.asList(split).subList(1, split.length)));
                }
            }
        }
        catch (Exception e)
        {
            Gdx.app.error("Error in the reading texte of people", e.getMessage());
        }
        return list;
    }


    /**
     * This static method read the file and create a tab with first column is speech of PNJ.
     * And the second columns is a tab with three columns who are answers of question of PNJ
     * @return table[speech PNJ][table[choose speech people]]
     */
    private HashMap<String,String> getPnj()
    {
        String vertical;
        String[] split;
        HashMap<String,String> list = new HashMap<>();
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal("Conversations/Conversation.pnj.id")))));
            while ((vertical = line.readLine()) != null)
            {
                if(vertical.charAt(0) != '%' &&  (split = vertical.split("/")).length >= 2 )
                {
                    list.put(split[0],split[1]);
                }
            }
        }
        catch (Exception e)
        {
            Gdx.app.error("Error in the read of pnj",e.getMessage());
        }
        return list;
    }
}
