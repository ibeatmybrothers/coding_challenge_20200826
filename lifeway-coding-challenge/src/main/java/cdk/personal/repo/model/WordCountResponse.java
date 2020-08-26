package cdk.personal.repo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model representing the JSON response
 */
public class WordCountResponse {
    int count;

    @JsonCreator
    public WordCountResponse(@JsonProperty("count") int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
