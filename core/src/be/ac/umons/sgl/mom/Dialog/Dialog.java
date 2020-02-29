package be.ac.umons.sgl.mom.Dialog;

import java.util.List;


/***/
public interface Dialog
{
    /***/
    void readFile(String file);

    /***/
    List<String> correspondentAnswer(String answer);
}
