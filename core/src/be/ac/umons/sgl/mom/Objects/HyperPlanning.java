package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Lesson;
import java.util.Date;
import java.util.HashMap;

public class HyperPlanning
{
    public HashMap<Date, Lesson> createSchedule(Lesson ... list)
    {
        for (Lesson lss : list)
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
