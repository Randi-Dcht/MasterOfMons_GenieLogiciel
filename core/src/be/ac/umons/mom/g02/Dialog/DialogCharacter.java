package be.ac.umons.mom.g02.Dialog;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Events.SuperviserNormally;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Items.Items;
import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class define the dialog of the character and a people
 * The dialog is coding but also it is on the file .id in conversation
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class DialogCharacter
{
    private static final String PATH="Conversations/IdConversations/";
    /***/
    private Mobile mobile;
    /**
     * The list with the dialog after the reading in file
     */
    private HashMap<String,ArrayList<String>> listDialog;


    /**
     * This constructor define the dialog of the character
     * @param nameDialog is the name of the dialog with the ID
     */
    public DialogCharacter(NameDialog nameDialog)
    {
        listDialog = readFileConversation(nameDialog);
    }


    /**
     * This static method read the file and create a tab with first column is speech of PNJ.
     * And the second columns is a tab with three columns who are answers of question of PNJ
     * @param name is the name of the file in the repertory Conversation with ID
     * @return table[speech PNJ][table[choose speech people]]
     */
    private HashMap<String,ArrayList<String>> readFileConversation(NameDialog name)
    {
        String vertical;
        String[] split;
        HashMap<String,ArrayList<String>> list = new HashMap<>();
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal(PATH + name.getFile())))));
            while ((vertical = line.readLine()) != null)
            {
                if(vertical.charAt(0) != '%' &&  (split = vertical.split("/")).length >= 2 )
                {
                    //list.put(split[0],new ArrayList<>(Arrays.asList(split).subList(1, split.length)));
                    list.put(split[0], processString(Arrays.asList(split).subList(1, split.length)));
                }
            }
        }
        catch (Exception e)
        {
            Gdx.app.error("Error in the reading text of the conversation", e.getMessage());
        }
        finally
        {
            return list;
        }
    }


    /***/
    public ArrayList<String> getDialog(String answer)
    {
        if (listDialog.containsKey(answer))
            return listDialog.get(answer);
        return listDialog.get("ESC");
    }


    /***/
    private ArrayList<String> processString(List<String> lst)
    {
        ArrayList<String> list = new ArrayList<>();
        for (String str : lst)
        {
            if (str.equals("ITEMS") || str.equals("MAPS") || str.equals("PLACE") || str.equals("QUEST") ||  str.equals("RANDOM") || str.equals("NEXTLESSON"))
                list.addAll(replaceId(str));
            else
                list.add(str);
        }
        return list;
    }


    /**
     * This method allows to analyse the id for replace the id of the game
     * @return a list of the dialog
     */
    private ArrayList<String> replaceId(String id)
    {
        ArrayList<String> list = new ArrayList<>();
        if (id.equals("ITEMS"))
        {
            for (Items it : SuperviserNormally.getSupervisor().getAllItems())
                list.add(it.getIdItems());
        }

        if (id.equals("MAPS"))
        {
            list.add(Maps.Nimy.getInformation());
        }

        if (id.equals("PLACE"))
        {
            list.add("ESC");
        }

        if (id.equals("QUEST") || id.equals("RANDOM") || id.equals("NEXTLESSON"))
            list.add(SuperviserNormally.getSupervisor().getPeople().getQuest().question());
        return list;
    }


    /**
     * This method returns the list of the dialog for the debug
     * @return the dialog of the character
     */
    @Override
    public String toString()
    {
        return "DialogCharacter: {"+ listDialog +'}';
    }
}
