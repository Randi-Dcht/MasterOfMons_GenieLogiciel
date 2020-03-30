package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.Character;
/**
 * Event when the energy of the player change
 */
public class EnergyChanged implements Notification {

    private Character concernedOne;
    private Events evt;
    private double buffer;

    /**
     * @param concernedOne The concerned character
     * @param buffer The new energy
     */
    public EnergyChanged(Character concernedOne, double buffer) {
        this.concernedOne = concernedOne;
        evt = Events.EnergyChanged;
        this.buffer = buffer;
    }


    /**
     * This method return the events
     *
     * @return events is the events
     */
    @Override
    public Events getEvents() {
        return evt;
    }

    /**
     * @return The concerned character
     */
    public Character getConcernedOne() {
        return concernedOne;
    }

    /**
     * This method allows to return the object
     *
     * @return the object
     */
    @Override
    public Object getBuffer() {
        return buffer;
    }

    /**
     * This method return if the buffer is occupy
     *
     * @return boolean if the buffer contains element
     */
    @Override
    public boolean bufferNotEmpty() {
        return true;
    }

}
