package be.ac.umons.sgl.mom.Objects;

import be.ac.umons.sgl.mom.Events.Notifications.ChangeMonth;
import be.ac.umons.sgl.mom.Events.Notifications.Notification;
import be.ac.umons.sgl.mom.Events.Observer;
import be.ac.umons.sgl.mom.Events.SuperviserNormally;
import java.util.Objects;

/**
 * This class is the time in the game who changed with the refresh frame.
 */
public class TimeGame implements Observer
{
    static final int[][] years = { {31,28,31,30,31,30,31,31,30,31,30,31},
                                   {31,29,31,30,31,30,31,31,30,31,30,31}
                                 };

    static final int   timeSec      = 60;
    static final int   timeHour     = 24;
    static final int   timeYr       = 12;

    private int NBmonth;
    private int NByear;

    private double second;
    private int hour;
    private int min;
    private int day;
    private int year;

    /**
     * This constructor allows to define a time of game
     * @param month who is the month of start
     * @param day who is the day of start
     * @param hour who is the hour of start
     * @param years who is year of start
     */
    public TimeGame(int month,int day,int hour,int years)
    {
        NBmonth = month-1;
        this.day = day-1;
        this.hour = hour;
        this.year = years;
        NByear = leap(years);
        second = 0;

    }

    @Override
    public void update(Notification notify)
    {
        changeMin();
    }

    public void updateSecond(double time)
    {
       changeMin();
    }

    /**
     * This method allows to know leap year
     * @param years is the years (after 1900)
     * @return 1 if leap else 0
     */
    private int leap(int years)
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
        SuperviserNormally.getSupervisor().getEvent().notify(new ChangeMonth());
        if((NBmonth =(NBmonth+1)%timeYr) == 0)
            changeYear();
    }

    private void changeYear()
    {
        year = year + 1;
        NByear= leap(year);
    }

    /**
     * This method allows to replay the time for a new year in the university
     * This method changes the timeGame !!!
     */
    public void newYear()
    {
        this.hour = 8;
        this.min  = 0;
        this.day  = 16;
        this.NBmonth = 8;
        this.year++;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeGame schedule = (TimeGame) o;
        return NBmonth == schedule.NBmonth &&
                hour == schedule.hour &&
                min == schedule.min &&
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

    /**
     * This methods is only for the test of JunitTest
     */
    int[] getValueTest()
    {
        return new int[]{min,hour,day,year};
    }
}