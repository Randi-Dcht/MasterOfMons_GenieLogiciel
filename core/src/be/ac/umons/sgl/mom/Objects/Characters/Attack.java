package be.ac.umons.sgl.mom.Objects.Characters;

public interface Attack
{
    /**Méthode pour voir la possibilité d'esquivé l'attaque*/
    public double dodge();
    /**Méthode pour voir le temps de récupération*/
    public double recovery();
    /**Méthode qui retire les points de vie lorsque l'esquive échoue*/
    public void attack();
}