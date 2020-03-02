package be.ac.umons.mom.g02.Other;

/**
 * This define a date with day-moth-year and hour-min
 * @author Umons_Group_2_ComputerScience
 */
public class Date
{
    private int year;
    private int day;
    private int month;
    private int hour;
    private int min;


    /**
     * This constructor define a date with 3 parameters
     * @param day   is the day in month
     * @param month is the month in the years
     * @param year  is the years
     */
    public Date(int day, int month, int year)
    {
        this.day   = day;
        this.month = month;
        this.year  = year;
    }


    /**
     * This constructor define a date with 5 parameters
     * @param day   is the day in month
     * @param month is the month in the years
     * @param year  is the years
     * @param hour is the hour is the day
     * @param min is the minute in the our
     */
    public Date(int day, int month, int year, int hour, int min)
    {
        this.day   = day;
        this.month = month;
        this.year  = year;
        this.min   = min;
        this.hour  = hour;
    }


    /**
     * This method return the month
     * @return the month
     */
    public int getMonth()
    {
        return month;
    }


    /**
     * This method return the day
     * @return the day
     */
    public int getDay()
    {
        return day;
    }


    /**
     * This method return the hour
     * @return hour
     */
    public int getHour()
    {
        return hour;
    }


    /**
     * This method return the minute
     * @return minute
     */
    public int getMin()
    {
        return min;
    }


    /**
     * The method return the year
     * @return the year
     */
    public int getYear()
    {
        return year;
    }


    /**
     * This method return the date in a String
     * @return a string of the date
     */
    @Override
    public String toString()
    {
        return String.format("%02d / %02d / %s  %02d:%02d",day,month,year,hour,min);
//        return "Date{" +
//                ", day=" + day +
//                ", month=" + month +
//                '}';
    }
}
