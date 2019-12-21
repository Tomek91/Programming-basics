package pl.com.app.properties;

import pl.com.app.parsers.FileNames;
import pl.com.app.validations.CustomException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetScreeningProperty {
    private InputStream inputStream;

    public Map<String, String> getPropValues() {

        Map<String, String> propMap = new HashMap<>();

        try {
            Properties prop = new Properties();
            String propFileName = FileNames.SCREENING_PROP;

            inputStream = new FileInputStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new CustomException("property file '" + propFileName + "' not found in the classpath");
            }


            propMap.put("CENA_MIN", prop.getProperty("CENA_MIN"));
            propMap.put("CENA_MAX", prop.getProperty("CENA_MAX"));
            propMap.put("CZAS_FILMU1", prop.getProperty("CZAS_FILMU1"));
            propMap.put("CZAS_FILMU2", prop.getProperty("CZAS_FILMU2"));
            propMap.put("CZAS_FILMU3", prop.getProperty("CZAS_FILMU3"));
            propMap.put("SALA_MIN", prop.getProperty("SALA_MIN"));
            propMap.put("SALA_MAX", prop.getProperty("SALA_MAX"));


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
        return propMap;
    }
}
