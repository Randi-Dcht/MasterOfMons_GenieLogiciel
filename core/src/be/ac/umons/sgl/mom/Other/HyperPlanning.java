package be.ac.umons.sgl.mom.Other;

import be.ac.umons.sgl.mom.Enums.Lesson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class HyperPlanning
{
    public static ArrayList<Lesson> monthL;

    public static HashMap<Integer, ArrayList<Lesson>> createSchedule(ArrayList<Lesson> lesson, Date date) //TODO changer cela pour conrespondre à guillaume + gauillaume uniqyuement jour
    {
        HashMap<Integer,ArrayList<Lesson>> list = new HashMap<>();
        createMonthLesson(lesson);
        while (monthL.size() != 0)
        {
            int random = new Random().nextInt(monthL.size());

        }
        for (int i=0; i < 31 ; i++)
            list.put(i,new ArrayList<Lesson>());
        return list;
    }

    private static void createMonthLesson(ArrayList<Lesson> list)
    {
        monthL = new ArrayList<>();
        for (Lesson ls : list)
        {
            for (int i =0; i < ls.numberOfCourse();i++)
                monthL.add(ls);
        }
    }
}
