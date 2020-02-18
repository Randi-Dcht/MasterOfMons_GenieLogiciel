package be.ac.umons.sgl.mom.Other;

import be.ac.umons.sgl.mom.Enums.Lesson;
import be.ac.umons.sgl.mom.Quests.Master.MasterQuest;
import java.util.ArrayList;
import java.util.HashMap;

public class HyperPlanning
{
    public static HashMap<Integer, ArrayList<Lesson>> encours(MasterQuest quest, Date date) //TODO changer cela pour conrespondre à guillaume + gauillaume uniqyuement jour
    {
        return new HashMap<>();
    }

    public static HashMap<Integer, ArrayList<Lesson>> createSchedule(MasterQuest quest, Date date) //TODO changer cela pour conrespondre à guillaume + gauillaume uniqyuement jour
    {
        HashMap<Integer,ArrayList<Lesson>> list = new HashMap<>();
        ArrayList<Lesson> ll = new ArrayList<>();
        ll.add(Lesson.statistique);ll.add(Lesson.algbio);ll.add(Lesson.statistique);ll.add(Lesson.algbio);ll.add(Lesson.statistique);ll.add(Lesson.algbio);
        list.put(1,ll);
        list.put(2,ll);
        list.put(3,ll);
        list.put(4,ll);
        return list;
    }
}
