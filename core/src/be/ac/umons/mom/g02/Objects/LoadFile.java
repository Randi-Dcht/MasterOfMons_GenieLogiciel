package be.ac.umons.mom.g02.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.FileNotFoundException;

/**
 * Represent a file to load for an extension.
 */
public class LoadFile {
    /**
     * The file to load.
     */
    protected FileHandle file;
    /**
     * The type of that file.
     */
    protected Class typeOfFile;

    /**
     * @param path The file to load.
     * @param fileType The type of that file.
     * @throws ClassNotFoundException If the type isn't found.
     * @throws FileNotFoundException If the file isn't found.
     */
    public LoadFile(String path, String fileType) throws ClassNotFoundException, FileNotFoundException {
        file = Gdx.files.internal(path);
        if (! file.exists())
            throw new FileNotFoundException(path + "not found !");
        typeOfFile = Class.forName(fileType);
    }

    /**
     * @return The file to load
     */
    public FileHandle getFile() {
        return file;
    }

    /**
     * @return The type of the file to load
     */
    public Class getTypeOfFile() {
        return typeOfFile;
    }
}
