package be.ac.umons.mom.g02.Helpers;

import be.ac.umons.mom.g02.MasterOfMonsGame;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileHelper {

    public static HashMap<String, String> readSettingsFile(String file) {
        HashMap<String, String> map = new HashMap<>();
        BufferedReader br;
        int actualLine = 0;
        try {
            FileHandle ef = Gdx.files.getFileHandle(file, Files.FileType.Internal);
            if (! ef.exists()) {
                Gdx.app.error("FileHelper", String.format("The file \"%s\" wasn't found !", file));
                MasterOfMonsGame.showAnError(String.format("The file \"%s\" wasn't found !", file));
                return null;
            }
            br = new BufferedReader(new FileReader(ef.file()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] color = line.split("=");
                if (color.length < 2)
                    Gdx.app.error("FileHelper", "An error has been detected line " + actualLine);
                map.put(color[0], color[1]);
                actualLine++;
            }
        } catch (IOException e) {
            MasterOfMonsGame.showAnError(String.format("The file \"%s\" wasn't loaded due to an error !", file));
            Gdx.app.error("FileHelper", String.format("The file \"%s\" wasn't loaded due to an error !", file), e);
        } catch (NumberFormatException e) {
            Gdx.app.error("FileHelper", String.format("An error has been detected line %d of file \"%s\"", actualLine, file), e);
        }
        return map;
    }

}
