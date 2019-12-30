package be.ac.umons.sgl.mom.Objects.Characters;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
