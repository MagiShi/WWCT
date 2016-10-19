package src.model;

/**
 * Created by Ji Won Lee on 10/18/2016.
 */
public class FileFormatException extends Exception{
    private String line;

    public FileFormatException(String str) {
        line = str;
    }

    public String getOriginalLine() { return line; }
}
