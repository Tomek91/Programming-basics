package pl.com.app.properties;


import pl.com.app.parsers.FileNames;
import pl.com.app.validations.CustomException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;

public class GetGenreProperty {
    private String result = "";
    private InputStream inputStream;

    public String getPropValues() {

        try {
            Properties prop = new Properties();
            String propFileName = FileNames.GENRE_PROP;

            inputStream = new FileInputStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new CustomException("property file '" + propFileName + "' not found in the classpath");
            }

            LocalDateTime time = LocalDateTime.now();

            result = prop.getProperty("gatunki");


        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
