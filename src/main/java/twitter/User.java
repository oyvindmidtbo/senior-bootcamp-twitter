package twitter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Stig-Rune Skansgård on 09.01.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class User {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
