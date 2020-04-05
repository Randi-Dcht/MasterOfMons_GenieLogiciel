package be.ac.umons.mom.g02.Extensions.DualLAN.Quests;

import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.LAN.Managers.NetworkManager;
import be.ac.umons.mom.g02.Objects.Characters.People;
import com.badlogic.gdx.Gdx;

import java.net.SocketException;

/**
 * Represent the MasterQuest in the dual extension adapted to the LAN extension
 * @see be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualMasterQuest
 */
public class DualMasterQuest extends be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest.DualMasterQuest {

    /**
     * If we already sent the end of this MasterQuest or not
     */
    protected boolean alreadySent = false;

    /**
     * @param dual The type of dual
     * @param first The first player
     * @param second The second player
     */
    public DualMasterQuest(TypeDual dual, People first, People second) {
        super(dual, first, second);
    }

    @Override
    public void nextQuest() {
        super.nextQuest();
        if (! alreadySent) {
            try {
                NetworkManager.getInstance().sendOnTCP("EMQ", getName());
            } catch (SocketException e) {
                Gdx.app.error("MasterQuest", "Unable to get the NetworkManager", e);
            }
            alreadySent = true;
        }
    }
}
