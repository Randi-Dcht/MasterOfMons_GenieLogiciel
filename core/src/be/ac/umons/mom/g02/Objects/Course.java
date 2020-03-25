package be.ac.umons.mom.g02.Objects;

import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Enums.Lesson;
import java.io.Serializable;


/**
 * This class define the course who is an associate between lesson and date
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class Course implements Serializable
{
    /**
     * The lesson of this course (enum)
     */
    private Lesson lesson;
    /**
     * The date of the lesson
     */
    private Date date;
    /**
     * If the people go to the lesson
     */
    private boolean go = false;
    /***/
    private int late = 0;


    /**
     * This constructor define a course
     * @param date is the date of the course
     * @param lesson is the lesson of the course
     */
    public Course(Lesson lesson, Date date)
    {
        this.date = date;
        this.lesson = lesson;
    }


    /**
     * This method  return the lesson of the course
     * @return lesson of course
     */
    public Lesson getLesson()
    {
        return lesson;
    }


    /**
     * This method is called when the people go to the lesson
     */
    public void goCourse()
    {
        go = true;
    }


    /**
     * This method returns the boolean if the people go the course
     * @return boolean if people isGo
     */
    public boolean isGo()
    {
        return go;
    }


    /**
     * This method returns the date of the course
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }


    /**
     * This method allows to calculus the time before the finish course
     * @param now is the actual time of the game
     * @return a date with the time before end
     */
    public Date howTimeFinish(Date now)
    {
        double ccl = difference(date,now,2);
        return new Date(0,0,0,(int)(ccl/60),(int)(ccl%60));
    }


    private double difference(Date time,Date now,int many)
    {
        double there = (time.getHour()+many)*60 + time.getMin();
        double actu  = now.getHour()*60 + now.getMin();
        return there - actu;
    }


    public void arrivedAtCourse(Date now)//TODO
    {
        Double ccl = difference(date,now,0);
    }


    /***/
    public boolean isLate()
    {
        return late >= 30;
    }


    /***/
    public double getLate()
    {
        return late;
    }


    /**
     * This method allows to give the full name of the course with:
     *  - date
     *  - lesson
     *  - maps
     * @param gs The graphical settings to use
     * @return string of the course
     */
    public String toString(GraphicalSettings gs)
    {
        return date.getHour() + "H" + date.getMin() + " : " + gs.getStringFromId(lesson.getName()) + " ("+lesson.location() + ")";
    }
}
