package be.ac.umons.mom.g02.Managers;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class GameKeyManager {

    private static GameKeyManager instance;

    public static GameKeyManager getInstance() {
        if (instance == null)
            instance = new GameKeyManager();
        return instance;
    }

    protected HashMap<String, Integer> keysMap;

    protected GameKeyManager() {
        keysMap = new HashMap<>();
        loadKeyFile();
    }

    protected void loadKeyFile() {
        BufferedReader br;
        int actualLine = 0;
        try {
            FileHandle ef = Gdx.files.getFileHandle("keys", Files.FileType.Internal);
            br = new BufferedReader(new FileReader(ef.file()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] key = line.split("=");
                keysMap.put(key[0], Integer.parseInt(key[1]));
                actualLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            Gdx.app.log("GameKeyManager", "An error has been detected line " + actualLine, e);
        }
    }

    public void saveKeysMap() {
        // TODO
    }

    public int getKeyCodeFor(String id) {
        return keysMap.get(id);
    }

    public HashMap<String, Integer> getKeysMap() {
        return keysMap;
    }
}
