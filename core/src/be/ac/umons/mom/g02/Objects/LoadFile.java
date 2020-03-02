package be.ac.umons.mom.g02.Objects;

import java.io.File;
import java.io.FileNotFoundException;

public class LoadFile {
    protected File file;
    protected Class typeOfFile;

    public LoadFile(String path, String fileType) throws ClassNotFoundException, FileNotFoundException {
        file = new File(path);
        if (! file.exists())
            throw new FileNotFoundException(path + "not found !");
        typeOfFile = Class.forName(fileType);
    }

    public File getFile() {
        return file;
    }

    public Class getTypeOfFile() {
        return typeOfFile;
    }
}
