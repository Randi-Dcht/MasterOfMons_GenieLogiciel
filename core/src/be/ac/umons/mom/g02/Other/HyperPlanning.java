package be.ac.umons.mom.g02.Other;

import be.ac.umons.mom.g02.Enums.Lesson;
import be.ac.umons.mom.g02.Objects.Course;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * This class define the planning of the people with the lesson associate with a date
 * @author Umons_Group_2_ComputerScience
 */
public class HyperPlanning
{
    public static ArrayList<Lesson> monthL;
    public static double average = 1;


    /***/
    public static HashMap<Integer, ArrayList<Course>> createSchedule(ArrayList<Lesson> lesson, Date date)
    {
        HashMap<Integer,ArrayList<Course>> list = new HashMap<>();
        createMonthLesson(lesson);
        int dayy = date.getDay();
        while (monthL.size() != 0)
        {
            int rdh;
            ArrayList<Course> ll = new ArrayList<>();
            ArrayList<Integer> hour = new ArrayList<>();hour.add(8);hour.add(10);hour.add(13);hour.add(15);hour.add(17);
            for (double i=0; i<average; i++) //new Random().nextInt(4)
            {
                int random = new Random().nextInt(monthL.size());
                ll.add(new Course(monthL.get(random),new Date(dayy,date.getMonth(),date.getYear(),hour.get(rdh=random(hour.size())),0)));
                monthL.remove(random);
                hour.remove(rdh);
                if(monthL.size()==0)//TODO modifier cela pour éviter le bound of arraylist
                    return list;
            }
            list.put(dayy,ll);
            dayy++;
        }
        return list;
    }


    private static int random(int max)
    {
        int rdh;
        if((rdh=new Random().nextInt(max))>= 0)
            return rdh;
        return 0;
    }


    /***/
    private static void createMonthLesson(ArrayList<Lesson> list)
    {
        monthL = new ArrayList<>();
        for (Lesson ls : list)
        {
            for (int i =0; i < ls.numberOfCourse();i++)
                monthL.add(ls);
        }

        if (monthL.size()/31 <= 4)//TODO max
            average= monthL.size()/31;
        else
            average = 4;
    }
}
