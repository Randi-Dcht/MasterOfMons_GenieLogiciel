package be.ac.umons.sgl.mom.Objects;

import java.util.Objects;

/**
 * Cette classe permet d'implementer le temps dans le jeu où le temps va plus vite
 */
public class Schedule
{
    static final int[][] years = {{31,28,31,30,31,30,31,31,30,31,30,31},{31,29,31,30,31,30,31,31,30,31,30,31}};
    static final int   timeSec      = 60;
    static final int   timeHour     = 24;
    static final int   timeYr       = 12;

    private int NBmonth;
    private int NByear;

    private int timePast =0;
    private int hour;
    private int min;
    private double seconde;
    private int day;
    private int year;

    public Schedule(int month,int day,int hour,int years)
    {
        NBmonth = month-1;
        this.day = day-1;
        this.hour = hour;
        this.year = years;
        NByear = leap(years);

    }

    public void updateSecond(double time)
    {
        changeMin();
    }

    public int leap(int years)
    {
        if(years%4 == 0)
            return 1;
        return 0;
    }

    private void changeDay()
    {
        if(( day = (day+1)%years[NByear][NBmonth] )== 0)
            changeMonth();
    }

    private void changeMonth()
    {
        if((NBmonth =(NBmonth+1)%timeYr) == 0)
            changeYear();
    }

    private void changeYear()
    {
        year = year + 1;
        NByear= leap(year);
    }

    private void changeMin()
    {
        if((min =( min+1)%timeSec)==0)
            changeHour();
    }

    private void changeHour()
    {
        if((hour = (hour+1)%timeHour)==0)
            changeDay();
    }

    public void peopleSleep(double many)
    {}

    private void updateDate()
    {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return NBmonth == schedule.NBmonth &&
                hour == schedule.hour &&
                min == schedule.min &&
                Double.compare(schedule.seconde, seconde) == 0 &&
                day == schedule.day &&
                years == schedule.years;
    }

    @Override
    public int hashCode() {
        return Objects.hash(NBmonth, hour, min, day, years);
    }

    public String toString()
    {
        return (day+1)+"/"+(NBmonth+1)+"/"+year + "  " + hour + ":"+ min;
    }
}