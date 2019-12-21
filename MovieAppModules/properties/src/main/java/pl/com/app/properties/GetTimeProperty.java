package pl.com.app.properties;

import pl.com.app.parsers.FileNames;
import pl.com.app.validations.CustomException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class GetTimeProperty {
    private Map<String, String> propMap = new HashMap<>();
    private InputStream inputStream;

    public Map<String, String> getPropValues() {

        try {
            Properties prop = new Properties();
            String propFileName = FileNames.TIMES_PROP;

            inputStream = new FileInputStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new CustomException("property file '" + propFileName + "' not found in the classpath");
            }

            propMap.put("gMax", prop.getProperty("gMax"));
            propMap.put("mMax", prop.getProperty("mMax"));


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
