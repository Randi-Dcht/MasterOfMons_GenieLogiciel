package be.ac.umons.sgl.mom.Dialog;

import be.ac.umons.sgl.mom.Enums.Maps;
import be.ac.umons.sgl.mom.Enums.Places;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/***/
public class DialogCharacter
{
    /***/
    private Mobile mobile;
    /***/
    private HashMap<String,ArrayList<String>> listDialog;

    /***/
    public DialogCharacter(/*Mobile mobile*/)
    {
       // this.mobile = mobile;
        listDialog = readFileConversation();
    }

    /**
     * This static method read the file and create a tab with first column is speech of PNJ.
     * And the second columns is a tab with three columns who are answers of question of PNJ
     * @return table[speech PNJ][table[choose speech people]]
     */
    private HashMap<String,ArrayList<String>> readFileConversation()
    {
        String vertical;
        String[] split;
        HashMap<String,ArrayList<String>> list = new HashMap<>();
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal("Conversations/IdConversations/Conversation.Landa.id")))));
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


    public ArrayList<String> getDialog(String answer)
    {
        if (listDialog.containsKey(answer))
            return listDialog.get(answer);
        return listDialog.get("ESC");
    }

    private ArrayList<String> processString(List<String> lst)
    {
        ArrayList<String> list = new ArrayList<>();
        for (String str : lst)
        {
            if (str.equals("ITEMS") || str.equals("MAPS") || str.equals("PLACE"))
                list.addAll(replaceId(str));
            else
                list.add(str);
        }
        return list;
    }


    private ArrayList<String> replaceId(String id)
    {
        ArrayList<String> list = new  ArrayList<String>();
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
        return list;
    }
}
