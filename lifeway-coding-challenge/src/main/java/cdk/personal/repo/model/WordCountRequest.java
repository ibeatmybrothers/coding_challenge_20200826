package cdk.personal.repo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Model representing the JSON request expected
 */
public class WordCountRequest {

    @NotNull @NotEmpty
    private String id;

    @NotNull
    private String message;

    @JsonCreator
    public WordCountRequest(@JsonProperty("id") String id,
                            @JsonProperty("message") String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage(){
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
