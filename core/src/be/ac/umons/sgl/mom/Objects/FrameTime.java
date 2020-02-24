package be.ac.umons.sgl.mom.Objects;

/**
 * This class define the refresh with the time between two frames
 */
public interface FrameTime
{

    /**
     * This method allows to give the time between two frames
     * This method allows to refresh the class every call
     * @param dt us the time between two frame
     */
    public void update(double dt);


}
