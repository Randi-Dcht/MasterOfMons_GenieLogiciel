package be.ac.umons.sgl.mom.Objects.Characters;

import java.util.ArrayList;
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
        return null;
    }

    private HashMap<String,String> getPnj(String file)
    {
        return null;
    }

}
