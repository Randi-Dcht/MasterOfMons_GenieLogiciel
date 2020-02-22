package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Other.Date;

public class Course
{
    private Lesson lesson;
    private Date date;

    public Course(Lesson lesson, Date date)
    {
        this.date = date;
        this.lesson = lesson;
    }

    public Date getDate()
    {
        return date;
    }

    public Lesson getLesson()
    {
        return lesson;
    }

    @Override
    public String toString() {
        return date.getHour() + "H" + date.getMin() + " : " + lesson.toString() + " ("+lesson.location() + ")";
    }
}
