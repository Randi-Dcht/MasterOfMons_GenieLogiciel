package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.Helpers.FileHelper;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GameColorManager {
    /**
     * The instance of the GameColorManager
     */
    private static GameColorManager instance;

    /**
     * @return The instance of the GameColorManager
     */
    public static GameColorManager getInstance() {
        if (instance == null)
            instance = new GameColorManager();
        return instance;
    }

    /**
     * The map making the link between an id and the color associated
     */
    protected LinkedHashMap<String, Color> colorsMap;

    protected GameColorManager() {
        loadColorFile();
    }

    /**
     * Read the file <code>keys</code> and add the id and keycode to the map
     */
    protected void loadColorFile() {
        colorsMap = new LinkedHashMap<>();
        HashMap<String, String> map = FileHelper.readSettingsFile("colors");
        for (String key : map.keySet())
            colorsMap.put(key, StringHelper.getColorFromString(map.get(key)));
    }

    /**
     * Save the current map into the file <code>keys</code>
     */
    public void saveColorsMap() {
        try {
            FileHandle ef = Gdx.files.getFileHandle("colors", Files.FileType.External);
            BufferedWriter bw = new BufferedWriter(new FileWriter(ef.file()));
            for (String id : colorsMap.keySet())
                bw.write(String.format("%s=%s\n", id, colorsMap.get(id).toString()));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id The id
     * @return The key code associated with the given id
     */
    public Color getColorFor(String id) {
        return colorsMap.get(id);
    }

    public void setColorFor(String id, Color color) {
        colorsMap.put(id, color);
    }
    public void setColorFor(String id, String color) {
        colorsMap.put(id, StringHelper.getColorFromString(color));
    }

    public HashMap<String, Color> getColorsMap() {
        return colorsMap;
    }
}
