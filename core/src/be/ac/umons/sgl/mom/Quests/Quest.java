package be.ac.umons.sgl.mom.Quests;

/**
*Cette classe définit ce qu'est une quête de bachelier 1
*@author Randy Dauchot (étudiant en Sciences informatique)
*/

public interface Quest
{
/**
*Cette méthode permet de retourner l'avancement entre 0 et 1
*/
  public double getProgress();

/**
*Cette méthode permet de retourner l'avancement
*/
  public double getAdvancement();

/**
*Cette méthode permet de savoir si la quete est toujours active
*/
  public boolean isActive();

/**
*Cette méthode permet de retourner les sous quete de celle-ci
*/
  public Quest[] getSubQuests();

/**
*Cette méthode permet de retourner le nom de la méthode
*/
  public String getName();

/**
*Permet de retourner le nombre de sous quete
*/
  public int getTotalSubQuestsNumber();

/**
*Cette méthode permet d'ajouter une progression dans la quete
*/
  public void addProgress(double many);

  public boolean isFinished();
}
