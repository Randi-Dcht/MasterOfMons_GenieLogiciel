package be.ac.umons.mom.g02.Enums;

/**
 * This class allows to define the different state of the people for the energizing
 * @author Umons_Group_2_ComputerScience_RandyDauchot
 */
public enum State
{
  /**
   * When the people on the map
   */
  normal("Normally",-0.00772),
  /**
   * When the people attack other
   */
  attack("attack", -1.5),
  /**
   * When the people study lesson
   */
  study("Study",-0.02313),
  /**
   * When the people nap in the kot
   */
  nap("Nap",2.0),
  /**
   * when the people running in the map
   */
  run("Running",-0.3088),
  /**
   * When the people listen the course in the auditory
   */
  listen("ListenCourse",-0.1544);


  final String state;
  final double energy;


  /**
   * This constructor define a state of the people
   * @param energy is the number of energizing decrement
   * @param state  is the name of the state
   */
  private State(String state, double energy)
  {
    this.state = state;
    this.energy = energy;
  }


  /**
   * This method return the energizing decrement
   * @return energy
   */
  public double getEnergy()
  {
    return energy;
  }
}
