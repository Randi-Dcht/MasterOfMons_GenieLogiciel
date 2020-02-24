package be.ac.umons.sgl.mom.Other;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Objects.Course;
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
            ArrayList<Course> ll = null;
            ll = new ArrayList<>();
            for (double i=0; i<average; i++) //new Random().nextInt(4)
            {
                int random = new Random().nextInt(monthL.size());
                ll.add(new Course(monthL.get(random),new Date(dayy,date.getMonth(),date.getYear())));
                monthL.remove(random);
                if(monthL.size()==0)//TODO modifier cela pour éviter le bound of arraylist
                    return list;
            }
            list.put(dayy,ll);
            dayy++;
        }
        return list;
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
        average= monthL.size()/31;
    }
}
