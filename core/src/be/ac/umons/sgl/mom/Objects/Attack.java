package be.ac.umons.sgl.mom.Objects;

interface Attack
{
    /**Méthode pour voir la possibilité d'esquivé l'attaque*/
    double dodge();
    /**Méthode pour voir le temps de récupération*/
    double recovery();
    /**Méthode qui retire les points de vie lorsque l'esquive échoue*/
    void attack();
}