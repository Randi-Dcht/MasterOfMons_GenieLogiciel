package be.ac.umons.mom.g02.Objects;

import be.ac.umons.mom.g02.Other.Date;
import be.ac.umons.mom.g02.Enums.Lesson;


/**
 * This class define the course who is an associate between lesson and date
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class Course
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
     * This method allows to give the full name of the course with:
     *  - date
     *  - lesson
     *  - maps
     * @return string of the course
     */
    @Override
    public String toString()
    {
        return date.getHour() + "H" + date.getMin() + " : " + lesson.toString() + " ("+lesson.location() + ")";
    }
}
