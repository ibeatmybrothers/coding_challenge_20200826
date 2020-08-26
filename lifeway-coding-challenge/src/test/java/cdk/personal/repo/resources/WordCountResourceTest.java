package cdk.personal.repo.resources;

import cdk.personal.repo.model.WordCountRequest;
import cdk.personal.repo.model.WordCountResponse;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit test for the resource class. I am not testing for null/empty values
 * as those should be covered by the @Valid, @NotNull, and @NotEmpty annotations
 */
public class WordCountResourceTest {

    private WordCountResource resource;
    private WordCountRequest request;
    private WordCountResponse response;

    @Before
    public void beforeEach() {
        resource = new WordCountResource();
        response = new WordCountResponse();
        request = new WordCountRequest("1", "message");
    }

    @Test
    public void test_empty_message_does_not_add_to_count() {
        request.setMessage("");
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    public void test_space_only_message_does_not_add_to_count() {
        request.setMessage("                ");
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    public void test_return_character_message_does_not_add_to_count() {
        request.setMessage("\n");
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    public void test_escape_character_message_does_not_add_to_count() {
        request.setMessage("\\");
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    public void test_numeric_message_does_not_add_to_count() {
        request.setMessage("1234578 65424");
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    public void test_special_character_message_does_not_add_to_count() {
        request.setMessage("! ? ,. $#$%  -_=+/*  %*%^&^ \'\" []() {}");
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(0);
    }

    @Test
    public void test_mixed_message_does_add_to_count() {
        request.setMessage("code-challenge, 1223 ?<>!!@> !one_word");
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(2);
    }

    @Test
    public void test_different_messages_with_same_id_does_not_add_to_count() {
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(1);

        request.setMessage("new message");

        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(1);
    }

    @Test
    public void test_same_messages_with_different_id_adds_to_count() {
        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(1);

        request.setId("2");

        response = resource.put(request);
        assertThat(response.getCount()).isEqualTo(2);
    }
}
