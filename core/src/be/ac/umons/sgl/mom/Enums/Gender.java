package be.ac.umons.sgl.mom.Enums;

public enum Gender
{
    Women('F',"Women"),
    Men('H',"Men");

    private char name;
    private String full;

    private Gender(char name,String full)
    {
        this.name=name;
        this.full= full;
    }

    @Override
    public String toString()
    {
        return full;
    }

    public char getName()
    {
        return name;
    }
}
