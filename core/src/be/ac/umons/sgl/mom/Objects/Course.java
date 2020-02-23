package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Other.Date;


/**
 * This class define the course who is an associate between lesson and date
 * @author Umons_Group_2_ComputerScience
 */
public class Course
{
    private Lesson lesson;
    private Date date;


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
     * This method allows to give the full name of the course with:
     *  - date
     *  - lesson
     *  - place
     * @return string of the course
     */
    @Override
    public String toString()
    {
        return date.getHour() + "H" + date.getMin() + " : " + lesson.toString() + " ("+lesson.location() + ")";
    }
}
