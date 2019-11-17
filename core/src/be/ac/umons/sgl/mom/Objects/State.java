package be.ac.umons.sgl.mom.Objects;
public enum State
{
  normal("Normal",-0.000772),
  sleep("Dormir", 0.0028),
  study("Etudier",-0.002313),
  nap("Sieste",0.0014),
  run("Courrir",-0.003088),
  listen("Ecouter cours",-0.001544);

  final String state;
  final double energy;

  private State(String state, double energy)
  {
    this.state = state;
    this.energy = energy;
  }

  public double getEnergy()
  {
    return energy;
  }
}
