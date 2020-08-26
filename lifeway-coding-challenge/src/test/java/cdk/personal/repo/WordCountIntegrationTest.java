package cdk.personal.repo;

import cdk.personal.repo.model.WordCountRequest;
import cdk.personal.repo.model.WordCountResponse;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class WordCountIntegrationTest {

    final String uri = "http://localhost:8080/word-count/post";

    @ClassRule
    public static final DropwizardAppRule<WordCountConfiguration> RULE = new DropwizardAppRule<>(
            WordCountApplication.class,
            ResourceHelpers.resourceFilePath("config.yml")
    );

    @Test
    public void test_word_call() {

        Client client = RULE.client();
        WordCountRequest request = new WordCountRequest("1", "First call");
        Response response = client.target(uri).request().post(Entity.json(request));

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(WordCountResponse.class).getCount()).isEqualTo(2);

        request.setId("20");
        request.setMessage("Follow up");
        response = client.target(uri).request().post(Entity.json(request));
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(WordCountResponse.class).getCount()).isEqualTo(4);

        request.setId("20");
        request.setMessage("should ignore duplicate id");
        response = client.target(uri).request().post(Entity.json(request));
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(WordCountResponse.class).getCount()).isEqualTo(4);

        request.setId("39");
        request.setMessage("for  ");
        response = client.target(uri).request().post(Entity.json(request));
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(WordCountResponse.class).getCount()).isEqualTo(5);

        request.setId("04");
        request.setMessage(". . .");
        response = client.target(uri).request().post(Entity.json(request));
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(WordCountResponse.class).getCount()).isEqualTo(5);

        request.setId("005");
        request.setMessage("39 should have accumulated one, " +
                "04 should have been ignored based off the regex, " +
                "and 005 should have excluded the numbers and included this-hyphenated-word-as-one-single-word");
        response = client.target(uri).request().post(Entity.json(request));
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(WordCountResponse.class).getCount()).isEqualTo(26);

    }

}
