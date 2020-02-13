package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;

import java.util.Date;
import java.util.HashMap;

public class HyperPlanning
{
    public static HashMap<Date, Lesson> createSchedule(MasterQuest quest)
    {
        for (Lesson lss : quest.getInterrogation())
        {
            int cnt = 0;
            while (cnt <= lss.numberOfCourse())
            {
                cnt++;
            }
        }
        return new HashMap<>();
    }
}
