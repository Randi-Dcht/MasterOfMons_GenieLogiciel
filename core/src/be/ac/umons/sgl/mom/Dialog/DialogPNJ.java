package be.ac.umons.sgl.mom.Dialog;

import be.ac.umons.sgl.mom.Objects.Characters.Mobile;
import be.ac.umons.sgl.mom.Objects.Items.Items;
import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/***/
public class DialogPNJ
{
    /***/
    private Mobile mobile;

    /***/
    public DialogPNJ(Mobile mobile)
    {
        this.mobile = mobile;
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
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal("Conversations/IdConversations/Conversation.pnj.id")))));
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
            Gdx.app.error("Error in the read text of pnj",e.getMessage());
        }
        finally
        {
            return list;
        }
    }

    private ArrayList<String> replaceId(String id, ArrayList<String> list)
    {
        if (id.equals("MYITEMS"))
        {
            list.add("NoItems");//TODO
            for (Items it : mobile.getListItems())
                list.add(it.getIdItems());
        }

        if (id.equals("MYMAPS"))
            list.add(mobile.getMaps().getInformation());

        if (id.equals("MYPLACES"))
        {
        }


        return list;
    }
}
