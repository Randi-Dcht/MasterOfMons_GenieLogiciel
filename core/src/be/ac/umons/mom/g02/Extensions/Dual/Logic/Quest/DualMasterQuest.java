package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Events.Notifications.Notification;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import java.util.ArrayList;
import java.util.HashMap;

public class DualMasterQuest extends MasterQuest
{
    private HashMap<People,UnderQuest> list = new HashMap<>();
    private DualUnderQuest[] ll = new DualUnderQuest[2];
    private People peopleSecond;
    private TypeDual dual;
    private boolean canPass = true;


    public DualMasterQuest(TypeDual dual, People first, People second)
    {
        super(null,first, Bloc.Extend,first.getDifficulty());
        peopleSecond = second;
        this.dual = dual;
        createUnderQuest(dual.getStart());
        Supervisor.getEvent().add(this,Events.Attack,Events.UseItems);
    }

    public People getWinner()
    {
        if (list.get(people).getAdvancement() > list.get(peopleSecond).getAdvancement())
            return people;
        else
            return peopleSecond;
    }


    private void createUnderQuest(Class<? extends DualUnderQuest> under)
    {
        try
        {
            ll[0] = under.getConstructor(People.class,MasterQuest.class).newInstance(people,this);
            ll[1] = under.getConstructor(People.class,MasterQuest.class).newInstance(peopleSecond,this);
            list.put(people,ll[0]);
            list.put(peopleSecond,ll[1]);
            addUnderQuest(ll);
        }
        catch (Exception e)
        {
            Gdx.app.error("Error fatal in init the underQuest of Dual :", String.valueOf(e));
        }
    }




    /***/
    @Override
    public double getProgress()
    {
        return Math.max(list.get(people).getProgress(),list.get(peopleSecond).getProgress());
    }


    /**
     * This method allows to define the next MasterQuest
     */
    @Override
    public void nextQuest()
    {
        checkNext();
    }


    /***/
    public void checkNext()
    {
        if ((ll[0].getAdvancement() >= 99 || ll[1].getAdvancement() >= 99) && canPass)
           {SupervisorDual.getSupervisorDual().isFinish();}
    }


    /**/
    public UnderQuest getUnderQuest(People player)
    {
        return list.get(player);
    }


    /**
     * This method returns the name of this Quest
     * @return the name of the quest
     */
    @Override
    public String getName()
    {
        return GraphicalSettings.getStringFromId("StartDualQuest");
    }


    /**
     * This method allow to say what is this quest
     * @return a goal (=question) of this quest
     */
    @Override
    public String question()
    {
        return "StartDualQuest";
    }


    /**
     * This method allows to create the list of the items
     */
    @Override
    protected void createListItems() throws Exception
    {
        listItems = ll[0].getListItems();
        listItems.addAll(ll[1].getListItems());//TODO pass static
    }


    /**
     * This method allows to create the list of the mobiles
     */
    @Override
    protected void createListMobiles() throws Exception
    {
        listMobs = ll[0].getListMobile();
        listMobs.addAll(ll[1].getListMobile());//TODO pass static
    }


    public void setPass(boolean condition)
    {
        canPass = condition;
    }

    /**
     * This method return the maps for this Quest
     * @return list of the maps
     */
    @Override
    public Maps[] getListMaps()
    {
        return new Maps[]{dual.getStartMaps()};
    }
}
