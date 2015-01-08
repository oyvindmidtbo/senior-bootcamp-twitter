package twitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TwitterAuthentication {
    
    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("twitter-authentication.properties");

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                System.out.println("Filen twitter-authentication.properties ble ikke funnet på classpath.");
                e.printStackTrace();
            }
        }
        
        return properties;
    }
    
    public String getConsumerKey() {
        return getProperties().getProperty("consumerKey");
    }

    public String getConsumerSecret() {
        return getProperties().getProperty("consumerSecret");
    }

    public String getAccessToken() {
        return getProperties().getProperty("accessToken");
    }

    public String getAccessTokenSecret() {
        return getProperties().getProperty("accessTokenSecret");
    }
}
