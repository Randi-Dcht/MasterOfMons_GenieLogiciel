package be.ac.umons.sgl.mom.Other;

public class Date
{
    private int year;
    private int day;
    private int month;
    private int hour;
    private int min;

    public Date(int day, int month, int year)
    {
        this.day   = day;
        this.month = month;
        this.year  = year;
    }

    public Date(int day, int month, int year, int hour, int min)
    {
        this.day   = day;
        this.month = month;
        this.year  = year;
        this.min   = min;
        this.hour  = hour;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

    public int getHour()
    {
        return hour;
    }

    public int getMin()
    {
        return min;
    }

    public int getYear()
    {
        return year;
    }

    @Override
    public String toString() {
        return "Date{" +
                ", day=" + day +
                ", month=" + month +
                '}';
    }
}
