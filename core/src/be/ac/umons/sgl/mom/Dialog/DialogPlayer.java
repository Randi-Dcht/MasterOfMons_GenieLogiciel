package be.ac.umons.sgl.mom.Dialog;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/***/
public class DialogPlayer
{

    /***/
    public DialogPlayer(){}

    /**
     * This method allows to read the file with the speech of PNJ when the people speak.
     * @return Map with the key is the possibility answer of people and other is the response
     */
    private HashMap<String, ArrayList<String>> getPeople()
    {
        String vertical;
        String[] split;
        HashMap<String,ArrayList<String>> list = new HashMap<>();
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal("Conversations/IdConversations/Conversation.people.id")))));
            while ((vertical = line.readLine()) != null)
            {
                if(vertical.charAt(0) != '%' &&  (split = vertical.split("/")).length >= 2 )
                {
                    list.put(split[0],new ArrayList<>(Arrays.asList(split).subList(0, split.length)));
                }
            }
        }
        catch (Exception e)
        {
            Gdx.app.error("Error in the reading text of people", e.getMessage());
        }
        finally
        {
            return list;
        }
    }
}
