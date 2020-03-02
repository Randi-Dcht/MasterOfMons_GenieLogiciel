package be.ac.umons.mom.g02.Extensions.Dual;

import be.ac.umons.mom.g02.GameStates.PlayingState;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.Saving;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;

public class RuleDuals //TODO: extends avec supervisor qui sera en instance
{
    private People player1;
    private People player2;
    private MasterQuest masterQuest;
    final PlayingState gui;
    private Saving save;
    private Maps maps; //TODO : changer avec futur maps de Guillaume

    public RuleDuals(PlayingState gui)
    {
        this.gui = gui;
    }

    public void newParty(People p1,People p2)
    {
        player1 = p1;
        player2 = p2;
    }

    private void launchNewQuest(People people)
    {}

    private void addThisQuest(People people)
    {}

    private void finishQuest()
    {}

    public boolean attackOther(People attacker , People victim)
    {
        return false;
    }



}