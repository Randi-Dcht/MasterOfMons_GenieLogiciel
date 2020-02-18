package be.ac.umons.sgl.mom.Other;

public class Date
{
    private int year;
    private int day;
    private int month;

    public Date(int day, int month, int year)
    {
        this.day   = day;
        this.month = month;
        this.year  = year;
    }

    public int getMonth()
    {
        return month;
    }

    public int getDay()
    {
        return day;
    }

}
