package twitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TwitterAuthentication {
    
    private static Properties getProperties() {
        Properties properties = new Properties();
        InputStream inputStream = TwitterAuthentication.class.getClassLoader().getResourceAsStream("twitter-authentication.properties");

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
    
    public static String getConsumerKey() {
        return getProperties().getProperty("consumerKey");
    }

    public static String getConsumerSecret() {
        return getProperties().getProperty("consumerSecret");
    }

    public static String getAccessToken() {
        return getProperties().getProperty("accessToken");
    }

    public static String getAccessTokenSecret() {
        return getProperties().getProperty("accessTokenSecret");
    }
}
