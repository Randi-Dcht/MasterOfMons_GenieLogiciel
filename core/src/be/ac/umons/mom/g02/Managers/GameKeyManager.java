package be.ac.umons.mom.g02.Managers;

import be.ac.umons.mom.g02.Helpers.FileHelper;
import be.ac.umons.mom.g02.Helpers.StringHelper;
import be.ac.umons.mom.g02.MasterOfMonsGame;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GameKeyManager {
    /**
     * The instance of the GameKeyManager
     */
    private static GameKeyManager instance;

    /**
     * @return The instance of the GameKeyManager
     */
    public static GameKeyManager getInstance() {
        if (instance == null)
            instance = new GameKeyManager();
        return instance;
    }

    /**
     * The map making the link between an id and the keycode of the key associated
     */
    protected LinkedHashMap<String, Integer> keysMap;

    protected GameKeyManager() {
        loadKeyFile();
    }

    /**
     * Read the file <code>keys</code> and add the id and keycode to the map
     */
    protected void loadKeyFile() {
        keysMap = new LinkedHashMap<>();
        HashMap<String, String> map = FileHelper.readSettingsFile("keys");
        try {
            for (String key : map.keySet())
                keysMap.put(key, Integer.parseInt(map.get(key)));

        } catch (NumberFormatException e) {
            Gdx.app.error("GameKeyManager", "An error has been detected in the keys' file", e);
        }
    }

    /**
     * Save the current map into the file <code>keys</code>
     */
    public void saveKeysMap() {
        try {
            FileHandle ef = Gdx.files.getFileHandle("keys", Files.FileType.Internal);
            BufferedWriter bw = new BufferedWriter(new FileWriter(ef.file()));
            for (String id : keysMap.keySet())
                bw.write(String.format("%s=%d\n", id, keysMap.get(id)));
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id The id
     * @return The key code associated with the given id
     */
    public int getKeyCodeFor(String id) {
        return keysMap.get(id);
    }

    /**
     * @return The map making the link between an id and the keycode of the key associated
     */
    public HashMap<String, Integer> getKeysMap() {
        return keysMap;
    }
}
