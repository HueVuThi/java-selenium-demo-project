package helper;

import java.io.File;

public class FileHelper {
    private static final String USER_DIR = "user.dir";

    public static String getCorrectJsonFilePath(String jsonFilePath, String folder) {
        String correctXlFilePath = System.getProperty(USER_DIR) + File.separator + folder
                + File.separator + jsonFilePath;
        return correctXlFilePath;
    }
}
