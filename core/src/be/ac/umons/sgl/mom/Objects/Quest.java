package be.ac.umons.sgl.mom.Objects;

import java.util.ArrayList;
import java.util.List;

public class Quest {
    protected String name;
    protected List<Quest> subQuests;
    protected boolean active = false;
    protected Quest parentQuest;
    protected boolean finished = false;
    protected float progress = 0;
    protected int totalQuest = 1;

    public Quest(String name) {
        this.name = name;
        subQuests = new ArrayList<>();
    }
    public Quest(String name, Quest... subQuest) {
        this.name = name;
        subQuests = new ArrayList<>();
        addSubQuests(subQuest);
    }

    public String getName() {
        return name;
    }

    public List<Quest> getSubQuests() {
        return subQuests;
    }

    public void addSubQuests(Quest... subQuest) {
        for (Quest q :
                subQuest) {
            subQuests.add(q);
            q.setParentQuest(this);
        }
        totalQuest = getTotalSubQuestsNumber(true) + 1;
    }

//    public String toString() {
//        return toString("");
//    }
//
//    public String toString(String prefix) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(prefix);
//        sb.append(active ? "<!> " : "<> ");
//        sb.append(name);
//        sb.append('\n');
//        for (Quest q : subQuests) {
//            sb.append(q.toString(prefix + "   "));
//        }
//        return sb.toString();
//    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        if (parentQuest != null)
            parentQuest.activate();
        active = true;
    }
    public void desactivate() {
        active = true;
        for (Quest q :
                subQuests) {
            q.desactivate();
        }
//        parentQuest.desactivate();
    }

    public void finish() {
        finished = true;
        progress = 1;
        if (parentQuest != null)
            parentQuest.calculateProgress();
    }

    public boolean isFinished() {
        return finished;
    }

    protected void setParentQuest(Quest parentQuest) {
        this.parentQuest = parentQuest;
        if (active)
            parentQuest.activate();
    }

    public int getTotalSubQuestsNumber() {
        return totalQuest;
    }

    protected int getTotalSubQuestsNumber(boolean main) {
        int res = subQuests.size();
        for (Quest q :
                subQuests) {
            res += q.getTotalSubQuestsNumber(false);
        }
        return res;
    }

    public float getProgress() {
        return progress;
    }

    protected void calculateProgress() {
        int questFinished = 0;
        int totalQuest = 0;
        for (Quest q :
                subQuests) {
            totalQuest++;
            if (q.isFinished())
                questFinished++;
        }
        progress = (float)questFinished / totalQuest;
    }
}
