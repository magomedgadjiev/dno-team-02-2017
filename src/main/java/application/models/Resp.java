package application.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.jdbc.core.ResultSetSupportingSqlParameter;

public class Resp {
    @JsonProperty
    private int key;

    @JsonIgnore
    private String message;

    @JsonCreator
    public Resp(@JsonProperty("key") int key, @JsonProperty("message") String message) {
        this.key = key;
        this.message = message;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
