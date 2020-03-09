package be.ac.umons.mom.g02.Events;

import be.ac.umons.mom.g02.Dialog.DialogCharacter;
import be.ac.umons.mom.g02.Enums.Actions;
import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Difficulty;
import be.ac.umons.mom.g02.Enums.Gender;
import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Enums.MobileType;
import be.ac.umons.mom.g02.Enums.NameDialog;
import be.ac.umons.mom.g02.Enums.State;
import be.ac.umons.mom.g02.Enums.Type;
import be.ac.umons.mom.g02.Events.Notifications.*;
import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.GraphicalObjects.QuestShower;
import be.ac.umons.mom.g02.Objects.Characters.Character;
import be.ac.umons.mom.g02.Objects.Course;
import be.ac.umons.mom.g02.Objects.FrameTime;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Regulator.Regulator;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Objects.Characters.Attack;
import be.ac.umons.mom.g02.Objects.Characters.Mobile;
import be.ac.umons.mom.g02.Objects.Characters.MovingPNJ;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Characters.Social;
import be.ac.umons.mom.g02.Objects.Items.Energizing;
import be.ac.umons.mom.g02.Objects.Items.Flower;
import be.ac.umons.mom.g02.Objects.Items.Gun;
import be.ac.umons.mom.g02.Objects.Items.Items;
import be.ac.umons.mom.g02.Objects.Items.OldExam;
import be.ac.umons.mom.g02.Objects.Items.PaperHelp;
import be.ac.umons.mom.g02.Objects.Items.Pen;
import be.ac.umons.mom.g02.Objects.Items.Phone;
import be.ac.umons.mom.g02.Objects.Items.TheKillBoot;
import be.ac.umons.mom.g02.Other.TimeGame;
import be.ac.umons.mom.g02.Quests.Master.MyFirstYear;
import be.ac.umons.mom.g02.Regulator.Superviser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * This class allows to monitor the game in the normally game without extension .
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class SuperviserNormally extends Superviser
{
    /**
     * This method to give the only instance of <code>SuperviserNormaly</code>
     */
    public static Superviser getSupervisor()
    {
        if(instance == null)
            instance = new SuperviserNormally();
        return instance;
    }

    /**
     * This constructor allows to define the class who monitor the game
     */
    protected SuperviserNormally()
    {
        super();
    }
}