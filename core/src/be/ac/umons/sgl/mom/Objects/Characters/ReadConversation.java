package be.ac.umons.sgl.mom.Objects.Characters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class allows to read the file with the speech of people and PNJ.
 * This class is static
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */

public class ReadConversation
{
    private static String url = "./core/assets/Conversation/";

    private HashMap<String,String> pnj;
    private HashMap<String, ArrayList<String>> people;
    private String language;

    public ReadConversation(String language)
    {
        if(language.equals("fr")||language.equals("en"))
            this.language = language;
        else
            this.language = "en";
        initialize();
    }

    private void initialize()
    {
        pnj = getPnj("pnj."+language+".cvt");
        people = getPeople("people."+language+".cvt");
    }

    ArrayList<String> choosePeople(String answer)
    {
        return people.get(answer);
    }

    String getAnswer(String answer)
    {
        return pnj.get(answer);
    }

    /**
     * This method allows to read the file with the speech of PNJ when the people speak.
     * @param file who is the file with the speech
     * @return Map with the key is the possibility answer of people and other is the response
     */
    private HashMap<String,ArrayList<String>> getPeople(String file)
    {
        String vertical;
        String[] split;
        HashMap<String,ArrayList<String>> list = new HashMap<>();
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
            System.err.println(e);
        }
        return list;
    }
    /**
     * This static method read the file and create a tab with first column is speech of PNJ.
     * And the second columns is a tab with three columns who are answers of question of PNJ
     * @param file who is a file with speech
     * @return table[speech PNJ][table[choose speech people]]
     */
    private HashMap<String,String> getPnj(String file)
    {
        String vertical;
        String[] split;
        HashMap<String,String> list = new HashMap<>();
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
            System.err.println(e);
        }
        return list;
    }

}
