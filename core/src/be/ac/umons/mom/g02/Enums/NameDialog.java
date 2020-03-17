package be.ac.umons.mom.g02.Enums;


/**
 * This class define the dialog of the character in the game
 * With the file of the ID dialog
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public enum NameDialog
{
    Lambda( "Conversation.Lambda.id"),
    Move(   "Conversation.Move.id"),
    Seller( "Conversation.Seller.id"),
    Student("Conversation.Student.id");


    /**
     * The name of the file with conversation id
     */
    final String file;


    /**
     * This constructor define the dialog of the character
     * @param file is the name of the file
     */
    NameDialog(String file)
    {
        this.file = file;
    }


    /**
     * This method returns the name of the file of the discussions
     */
    public String getFile()
    {
        return file;
    }
}
