package be.ac.umons.mom.g02.Events.Notifications;

import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Items.Items;

public class InventoryChanged implements Notification {

    protected People involvedOne;
    protected Items item;
    protected Type type;

    /**
     * @param involvedOne The player involved
     * @param item The <code>Items</code> that was added/removed
     * @param type What happened with the item
     */
    public InventoryChanged(People involvedOne, Items item, Type type) {
        this.involvedOne = involvedOne;
        this.item = item;
        this.type = type;
    }

    /**
     * This method return the events
     *
     * @return events is the events
     */
    @Override
    public Events getEvents() {
        return Events.InventoryChanged;
    }

    /**
     * This method allows to return the object
     *
     * @return The new inventory
     */
    @Override
    public People getBuffer() {
        return involvedOne;
    }

    /**
     * @return The player involved
     */
    public Items getItem() {
        return item;
    }

    /**
     * @return What happened with the item
     */
    public Type getType() {
        return type;
    }

    /**
     * This method return if the buffer is occupy
     *
     * @return boolean if the buffer contains element
     */
    @Override
    public boolean bufferNotEmpty() {
        return involvedOne != null;
    }

    public enum Type {
        Added, Removed
    }
}
