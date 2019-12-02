package be.ac.umons.sgl.mom.Objects;

import java.io.File;
import java.io.FileNotFoundException;

public class LoadFile {
    File file;
    Class typeOfFile;

    public LoadFile(String path, String fileType) throws ClassNotFoundException, FileNotFoundException {
        file = new File(path);
        if (! file.exists())
            throw new FileNotFoundException(path + "not found !");
        typeOfFile = Class.forName(fileType);
    }
}
