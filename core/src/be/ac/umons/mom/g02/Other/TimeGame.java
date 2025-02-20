package be.ac.umons.mom.g02.Other;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.ChangeHour;
import be.ac.umons.mom.g02.Events.Notifications.ChangeMonth;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Events.Observer;
import be.ac.umons.mom.g02.Regulator.SuperviserNormally;
import be.ac.umons.mom.g02.Events.Notifications.ChangeDay;
import be.ac.umons.mom.g02.Regulator.Supervisor;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class is the time in the game who changed with the refresh frame.
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public class TimeGame implements Observer, Serializable
{
    /***/
    public static int FASTER = 10;

    /***/
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
     * @param date is the date of the start day in the university.
     */
    public TimeGame(Date date)
    {
        setDate(date);
        Supervisor.getEvent().add(Events.ChangeQuest,this);
    }


    /***/
    public void setDate(Date date)
    {
        NBmonth   = date.getMonth()-1;
        this.day  = date.getDay();
        this.hour = date.getHour();
        this.year = date.getYear();
        this.min  = date.getMin();
        NByear    = leap(date.getYear());
        second    = 0;
    }


    /**
     * This method allows to notify the event at this class
     * @param notify  is the notification with the event
     */
    @Override
    public void update(Notification notify)
    {
        if (notify.getEvents().equals(Events.ChangeQuest))
            newYear();
    }


    /**
     * This method allows to refresh the time of the game
     * @param time is the time between two frame
     */
    public void updateSecond(double time)
    {
        second = second + time *FASTER;
        if(second>= 60)
        {
            changeMin();
            second = second%60;
        }
    }


    /**
     * This method return a new instance of the date
     * @return new instance the date
     */
    public Date getDate()
    {
        return new Date(day,NBmonth+1,this.year,hour,min);
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


    /**
     * This method allows change the day in the game
     */
    private void changeDay()
    {
        if(( day = (day+1)%years[NByear][NBmonth] )== 0)
            changeMonth();
        Supervisor.getEvent().notify(new ChangeDay());
    }


    /**
     * This method allows to change month and create an event to change the month
     */
    private void changeMonth()
    {
        if((NBmonth =(NBmonth+1)%timeYr) == 0)
            changeYear();
        Supervisor.getEvent().notify(new ChangeMonth());
    }


    /**
     * This method allows to change the month and re calculus the leap year
     */
    private void changeYear()
    {
        year++;
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


    /**
     * This method allows to calculus the change of minute
     */
    private void changeMin()
    {
        if((min =( min+1)%timeSec)==0)
            changeHour();
    }


    /**
     * This method allows to calculus the change of hour
     */
    private void changeHour()
    {
        if((hour = (hour+1)%timeHour)==0)
            changeDay();
        Supervisor.getEvent().notify(new ChangeHour());
    }


    /**
     * This method allows to check if two object TimeGame is equals
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TimeGame schedule = (TimeGame) o;
        return NBmonth == schedule.NBmonth &&
                hour == schedule.hour &&
                min == schedule.min &&
                day == schedule.day &&
                years == schedule.years;
    }


    /**
     * This method allows to hash the object
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(NBmonth, hour, min, day+1, years);
    }


    /**
     * This method returns the string of the actual date of the game
     * @return the format of the date in the game
     */
    public String toString()
    {
        return String.format("%02d / %02d / %s  %02d:%02d",day,NBmonth+1,year,hour,min);
    }


    /**
     * This method to call when the people pass an period in the game
     * @param addDay is the day to add
     * @param addHour is the hour to add
     * @param addMin is the minute to add
     * @param notif is the notification must be send
     */
    public void refreshTime(int addDay, int addHour, int addMin,boolean notif)
    {
        int memM,memH;
        min  = (memM = min + addMin)%60;
        hour = (memH = hour +(memM/60)+addHour)%24;
        day  = (day+memH/24+addDay)%years[NByear][NBmonth];

        if(addHour != 0 && notif)
            Supervisor.getEvent().notify(new ChangeHour());
        if(addDay != 0 && notif)
            Supervisor.getEvent().notify(new ChangeDay());
    }


    /**
     * This method to call when the people pass an period in the game
     * @param addDay is the day to add
     * @param addHour is the hour to add
     * @param addMin is the minute to add
     */
    public void refreshTime(int addDay, int addHour, int addMin)
    {
        refreshTime(addDay,addHour,addMin,true);
    }
}