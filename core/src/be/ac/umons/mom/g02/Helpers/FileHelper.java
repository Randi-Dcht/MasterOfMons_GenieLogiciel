package be.ac.umons.mom.g02.Helpers;

import be.ac.umons.mom.g02.MasterOfMonsGame;
import be.ac.umons.mom.g02.Objects.GraphicalSettings;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FileHelper {

    /**
     * Read a file formatted as "ID=Value" and return an HashMap where each value is associated with the red value.
     * @param file The file to read.
     * @return An HashMap where each value is associated with the red value.
     */
    public static HashMap<String, String> readSettingsFile(String file) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        BufferedReader br;
        int actualLine = 0;
        try {
            FileHandle ef = Gdx.files.getFileHandle(file, Files.FileType.External);
            if (! ef.exists()) {
                ef = Gdx.files.getFileHandle(file, Files.FileType.Internal);
                if (! ef.exists()) {
                    Gdx.app.error("FileHelper", String.format("The file \"%s\" wasn't found !", file));
                    MasterOfMonsGame.showAnError(String.format(GraphicalSettings.getStringFromId("fileNotFound"), file));
                    return null;
                }
            }
            br = new BufferedReader(ef.reader());
            String line;
            while ((line = br.readLine()) != null) {
                String[] color = line.split("=");
                if (color.length < 2)
                    Gdx.app.error("FileHelper", "An error has been detected line " + actualLine);
                map.put(color[0], color[1]);
                actualLine++;
            }
        } catch (IOException e) {
            Gdx.app.error("FileHelper", String.format("The file \"%s\" wasn't loaded due to an error !", file), e);
            MasterOfMonsGame.showAnError(String.format(GraphicalSettings.getStringFromId("fileNotLoaded"), file));
        } catch (NumberFormatException e) {
            Gdx.app.error("FileHelper", String.format("An error has been detected line %d of file \"%s\"", actualLine, file), e);
        }
        return map;
    }

}
