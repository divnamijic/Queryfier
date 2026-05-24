package edu.rit.croatia.iste422.g6.qf.util;

public class FileUtil {
    private FileUtil(){
        
    }
    
    /**
     * RemoveExtension
     * @param fileName
     * @return String extension
     */
    public static String removeExtension(String fname) {
        int pos = fname.lastIndexOf('.');
        if (pos > -1) {
            return fname.substring(0, pos);
        } else {
            return fname;
        }
    }

    /**
     * GetExtension
     * @param fileName
     * @return String extension
     */
    public static String getExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) // if the name is not empty
            return fileName.substring(i + 1).toLowerCase();

        return extension;
    }

}


