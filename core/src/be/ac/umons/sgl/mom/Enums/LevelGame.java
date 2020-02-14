package be.ac.umons.sgl.mom.Enums;

public enum LevelGame
{
    Easy(3,85,5),
    Medium(0,93,4),
    Hard(0,98,3);

    private int upLevel;
    private int percent;
    private int object;

    private LevelGame(int upLevel,int percent, int object)
    {
        this.object = object;
        this.percent = percent;
        this.upLevel = upLevel;
    }

}
