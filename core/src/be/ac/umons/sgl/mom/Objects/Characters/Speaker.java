package be.ac.umons.sgl.mom.Objects.Characters;

import java.util.Map;

/**
 * This class allows to read the file with the speech of people and PNJ.
 * This class is static
 * @author Randy Dauchot (étudiant en sciences informatique Umons)
 */

public class Speaker
{
    /*this the road of access of the repertory.*/
    private static final String url = "./";

    /**
     * This static method allows to read the repertory with the file with speech.
     * @return list of file in the repertory.
     * @param folder who is the repertory where there are the files.
     */
    public static String[] readFileTarget(String folder)
    {
        return null; //TODO implementer ici
    }

    /**
     * This static method read the file and create a tab with first column is speech of PNJ.
     * And the second columns is a tab with three columns who are answers of question of PNJ
     * @param file who is a file with speech
     * @return table[speech PNJ][table[choose speech people]]
     */
    public static String[][] readSpeechPnj(String file)
    {
        return null; //TODO implementer ici
    }

    /**
     * This method allows to read the file with the speech of PNJ when the people speak.
     * @param file who is the file with the speech
     * @return Map with the key is the possibility answer of people and other is the response
     */
    public Map<String,String> readSpeechPeople(String file)
    {
        return null; //TODO implementer ici
    }

}
