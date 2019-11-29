package be.ac.umons.sgl.mom.Objects;

/**
 * Cette classe permet d'implementer le temps dans le jeu où le temps va plus vite
 */
public class Schedule
{
    private int timePast =0;
    private int hour;
    private int min;
    private double seconde;
    private int day;
    private int month;
    private int years;

    public Schedule()
    {}

    public void updateSecond(double time)
    {}

    private void changeDay()
    {}

    private void changeMonth()
    {}

    private void changeYear()
    {}

    private void changeMin()
    {}

    private void changeHour()
    {}

    public void peopleSleep(double many)
    {}

    private void updateDate()
    {}

    public boolean equals(Schedule date)
    {
        return false;
    }

    public boolean sameDate()
    {
        return false;
    }

    public boolean sameTime()
    {
        return false;
    }
}