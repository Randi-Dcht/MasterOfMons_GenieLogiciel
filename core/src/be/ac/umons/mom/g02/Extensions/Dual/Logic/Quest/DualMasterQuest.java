package be.ac.umons.mom.g02.Extensions.Dual.Logic.Quest;

import be.ac.umons.mom.g02.Enums.Bloc;
import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Events.Events;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Enum.TypeDual;
import be.ac.umons.mom.g02.Extensions.Dual.Logic.Regulator.SupervisorDual;
import be.ac.umons.mom.g02.Objects.Characters.People;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import be.ac.umons.mom.g02.Quests.Master.MasterQuest;
import be.ac.umons.mom.g02.Quests.Under.UnderQuest;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import java.util.HashMap;


/**
 * This class define the masterQuest for the extension dual
 */
public class DualMasterQuest extends MasterQuest
{
    /**
     * The list with the association between people and underQuest
     */
    private HashMap<People,UnderQuest> list = new HashMap<>();
    /**
     * This is the second people
     */
    private People peopleSecond;
    /**
     * This is the actual dual to choose by player
     */
    private TypeDual dual;
    /**
     * boolean to know if this quest can pass to next quest
     */
    private boolean canPass = true;


    /**
     * This constructor define the masterQuest
     * @param dual   is the actual dual
     * @param first  is the people
     * @param second is the second player
     */
    public DualMasterQuest(TypeDual dual, People first, People second)
    {
        super(null,first, Bloc.Extend,first.getDifficulty());
        peopleSecond = second;
        this.dual = dual;
        createUnderQuest(dual.getStart());
        Supervisor.getEvent().add(this,Events.Attack,Events.UseItems);
    }


    /**
     * This method allows to know the winner of the dual
     * @return the winner
     */
    public People getWinner()
    {
        if (list.get(people).getAdvancement() > list.get(peopleSecond).getAdvancement())
            return people;
        else
            return peopleSecond;
    }


    /**
     * This method allows to create the underQuest of this
     * @param under is a class of underQuest
     */
    private void createUnderQuest(Class<? extends DualUnderQuest> under)
    {
        try
        {
            list.put(people,under.getConstructor(People.class,MasterQuest.class).newInstance(people,this));
            list.put(peopleSecond,under.getConstructor(People.class,MasterQuest.class).newInstance(peopleSecond,this));
            addUnderQuest(list.get(people),list.get(peopleSecond));
        }
        catch (Exception e)
        {
            Gdx.app.error("Error fatal in init the underQuest of Dual :", String.valueOf(e));
        }
    }


    /**
     * @return the maximum of progress between two underQuest
     */
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


    /**
     * This method check can pass to the next quest
     */
    public void checkNext()
    {
        if ((list.get(people).getAdvancement() >= 99 || list.get(peopleSecond).getAdvancement() >= 99) && canPass)
           {SupervisorDual.getSupervisorDual().isFinish();}
    }


    /**
     * @return the underQuest with the people
     * @param player is the player
     */
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
        listItems = ((DualUnderQuest)list.get(people)).getListItems();
        listItems.addAll(((DualUnderQuest)list.get(peopleSecond)).getListItems());
    }


    /**
     * This method allows to create the list of the mobiles
     */
    @Override
    protected void createListMobiles() throws Exception
    {
        listMobs = ((DualUnderQuest)list.get(people)).getListMobile();
        listMobs.addAll(((DualUnderQuest)list.get(peopleSecond)).getListMobile());
    }


    /**
     * Setter of can pass the Quest
     * @param condition is the boolean can pass quest
     */
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
