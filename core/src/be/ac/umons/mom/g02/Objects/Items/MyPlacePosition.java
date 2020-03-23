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
public class MyPlacePosition
{
    /***/
    private HashMap<Maps, ArrayList<Point>> listPoint;
    /***/
    private static final String PATH = "ResourceObjects/PositionRandomOfItem";
    /***/
    private Maps actually;


    /***/
    public MyPlacePosition()
    {
        listPoint = new HashMap<>();
        for (Maps map : Maps.values())
            listPoint.put(map,new ArrayList<>());

        createList();
    }


    /***/
    private void createList()
    {
        String vertical;
        try
        {
            BufferedReader line = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(Gdx.files.internal(PATH)))));
            while ((vertical = line.readLine()) != null)
            {

                if (vertical.charAt(0) == '%')
                    actually = Supervisor.getSupervisor().getMaps(vertical.substring(1));
                else
                {
                    String[] lst = vertical.split("-");
                    listPoint.get(actually).add(new Point(Integer.parseInt(lst[0]),Integer.parseInt(lst[1])));
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
    public Point getPosition(Maps maps)
    {
        return listPoint.get(maps).get(new Random().nextInt(listPoint.get(maps).size()));
    }
}
