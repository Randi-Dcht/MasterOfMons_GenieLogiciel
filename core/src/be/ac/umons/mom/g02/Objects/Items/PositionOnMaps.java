package be.ac.umons.mom.g02.Objects.Items;

import be.ac.umons.mom.g02.Enums.Maps;
import be.ac.umons.mom.g02.Regulator.Supervisor;
import com.badlogic.gdx.Gdx;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/***/
public class PositionOnMaps
{
    /***/
    private HashMap<Maps, HashMap<String, ArrayList<Point>>> listPoint;
    /***/
    private static final String PATH = "ResourceObjects/PositionItemsOnMaps";
    /***/
    private Maps actually;
    /***/
    private String ID;


    /***/
    public PositionOnMaps()
    {
        listPoint = new HashMap<>();
        for (Maps map : Maps.values())
            listPoint.put(map,new HashMap<>());

        createList();
    }


    /***/
    private void createList()
    {
        String vertical;
        try
        {
            BufferedReader line = new BufferedReader(Gdx.files.internal(PATH).reader());
            while ((vertical = line.readLine()) != null)
            {

                if (vertical.charAt(0) == '%')
                    actually = Supervisor.getSupervisor().getMaps(vertical.substring(1));
                else if (vertical.charAt(0) == '+')
                {
                    listPoint.get(actually).put(ID=vertical.substring(1),new ArrayList<>());
                }
                else
                {
                    String[] lst = vertical.split("-");
                    listPoint.get(actually).get(ID).add(new Point(Integer.parseInt(lst[0]),Integer.parseInt(lst[1])));
                }
            }
            line.close();
        }
        catch (Exception e)
        {
            Gdx.app.error("Error in the reading position of the item on the maps", e.getMessage());
        }
    }


    /***/
    public Point getPosition(Maps maps,String id)
    {
        if (listPoint.get(maps).containsKey(id))//TODO one list
            return listPoint.get(maps).get(id).get(new Random().nextInt(listPoint.get(maps).size()));
        return listPoint.get(maps).get("OTHER").get(new Random().nextInt(listPoint.get(maps).size()));
    }
}
