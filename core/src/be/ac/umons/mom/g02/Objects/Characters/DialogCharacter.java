package be.ac.umons.mom.g02.Objects.Characters;

import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Events.Notifications.Dialog;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * This class define the dialog of the character and a people
 * The dialog is coding but also it is on the file .id in conversation
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class DialogCharacter
{

    /**
     * This is the path of the file for the conversation
     */
    private static final String PATH="Conversations/IdConversations/";
    /**
     * This is the mobile who speak with the people
     */
    private Mobile mobile;
    /**
     * The list with the dialog after the reading in file
     */
    private HashMap<String,ArrayList<String>> listDialog;
    /***
     * The list of the special ID in the dialog
     */
    private ArrayList<String> listID = new ArrayList<>();
    /**
     * This is the redirection of the specificID
     */
    private String specificID = null;
    /**
     * The before answer of the player
     */
    private String before="";
    /**
     * This is an instance of the SuperviserNormally
     */
    private Supervisor supervisor;

    /**
     * This constructor define the dialog of the character
     * @param nameDialog is the name of the dialog with the ID
     */
    public DialogCharacter(NameDialog nameDialog, Supervisor supervisor)//TODO add sp
    {
        this.supervisor = supervisor;
        listDialog = readFileConversation(nameDialog);
        addIdSpecific("ITEMSGIVE","ITEMSUSE","MAPS","PLACE","QUEST","RANDOM","NEXTLESSON");
    }


    /**
     * This method allows to add the specific ID in the conversation
     * @param id is a word or a list of the id
     */
    private void addIdSpecific(String ... id)
    {
        Collections.addAll(listID, id);
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
                    list.put(split[0],new ArrayList<>(Arrays.asList(split).subList(1, split.length)));
                }
            }
            line.close();
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


    /**
     * This method allows to give the dialog with answer of the player
     * @param answer is the answer of the player
     * @return a list with the new dialog for the graphic
     */
    private ArrayList<String> getDialog(String answer)
    {
        if (listDialog.containsKey(answer))
            return listDialog.get(answer);
        return listDialog.get("ESC");
    }


    /**
     * This method allows to prepared the dialog of the player when the specific ID is found
     * @param list is a list with the bad ID to replace
     * @return list with the good ID
     */
    private ArrayList<String> preparingDialog(ArrayList<String> list)
    {
        ArrayList<String> newList = new ArrayList<>();
        for (String str : list)
        {
            if (listID.contains(str))
                newList.addAll(replaceId(str));
            else
                newList.add(str);
        }
        return newList;
    }


    /**
     * This method allows to analyse the id for replace the id of the game
     * @return a list of the dialog
     */
    private ArrayList<String> replaceId(String id)
    {
        ArrayList<String> list = new ArrayList<>();
        if (id.equals("ITEMSGIVE"))
        {
            for (Items it : mobile.getInventory())
                list.add(it.getIdItems());
        }
        if (id.equals("ITEMSUSE"))
        {
            for (Items it : supervisor.getAllItems())
                list.add(it.getIdItems());
        }
        else if (id.equals("MAPS"))
            list.add(supervisor.getPeople().getMaps().getInformation());
        else if (id.equals("PLACE"))
            list.add("ESC");
        else if (id.equals("QUEST") || id.equals("RANDOM"))
            list.add(supervisor.getPeople().getQuest().question());
        else if (id.equals("NEXTLESSON") && supervisor.getActualCourse() != null)
            list.add(supervisor.getActualCourse().getLesson().location().getInformation());
        else
            list.add("ESC");
        return list;
    }


    /**
     * Check if the list contains the specific ID
     * @param list is the list of dialog
     * @return the boolean if list contains ID
     */
    public boolean check(ArrayList<String> list)
    {
        for (String str : list)
        {
            if (listID.contains(str))
            {
                specificID = str;
                return true;
            }
        }
        return false;
    }


    /**
     * This method allows to analyze the answer of people with the ID
     * @param mobile is the mobile who speak
     * @param answerID is the answer ID
     */
    public void analyzeAnswer(String answerID,Mobile mobile)
    {
        if (!answerID.equals(before))
        {
            this.mobile = mobile;

            if (specificID != null)
            {
                idAnswer(answerID);
            }
            else if (!listDialog.containsKey(answerID))
            {
                supervisor.getEvent().notify(new Dialog("ESC"));
                //classSp.getEvent().remove(Events.Answer,classSp);TODO
            }
            else if (check(getDialog(answerID)))
                supervisor.getEvent().notify(new Dialog(preparingDialog(getDialog(answerID))));
            else
                supervisor.getEvent().notify(new Dialog(getDialog(answerID)));
            before = answerID;
        }
    }


    /**
     * This method allows to analyze the answer with the specific ID
     * @param answer is the answer of the player
     */
    private void idAnswer(String answer)
    {
        if (specificID.equals("ITEMSGIVE"))
        {
            Items it = getItems(mobile.getInventory(),answer);
            if (it != null)
                supervisor.getPeople().pushObject(it);
            supervisor.getEvent().notify(new Dialog(getDialog("ESC")));
        }
        if (specificID.equals("ITEMSUSE"))
        {
            Items it = getItems(Arrays.asList(supervisor.getAllItems()),answer);
            if (it != null)
                supervisor.getEvent().notify(new Dialog(it.getIdUse(),"ESC"));
            else
                supervisor.getEvent().notify(new Dialog("ESC"));
        }
        specificID = null;
    }


    /**
     * This method allows to give the Items in the list
     * @param list is a list of the item
     * @param find is the word who research in list
     * @return item if found or null
     */
    private Items getItems(List<Items> list, String find)
    {
        for (Items it : list)
        {
            if (it.getIdItems().equals(find))
                return it;
        }
        return null;
    }


    /**
     * This method returns the list of the dialog for the debug with the ID
     * @return the dialog of the character
     */
    @Override
    public String toString()
    {
        return "DialogCharacter: "+ listDialog +" (#DEBUG#)";
    }
}
