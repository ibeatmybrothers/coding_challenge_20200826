package cdk.personal.repo.resources;
import cdk.personal.repo.model.WordCountRequest;
import cdk.personal.repo.model.WordCountResponse;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Path("/word-count")
@Produces(MediaType.APPLICATION_JSON)
@Api(value="Word Count Service")
/**
 * WordCountResource - This is where the work gets done in DropWizard. This can house multiple operations,
 * here I'm just keeping a single POST method based off the exercise ask.
 */
public class WordCountResource {
    AtomicInteger totalCount = new AtomicInteger();
    Set<String> idSet = ConcurrentHashMap.newKeySet();
    private static final Logger log = LoggerFactory.getLogger(WordCountResource.class);

    public WordCountResource() {}

    /**
     * This POST request handles the word count ask. Receives an id and message and returns a count.
     * @param request - The JSON request POJO
     * @return - JSON response containing a count of the persisted value + the number of words in the request message.
     */
    @Path("/put")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    @ApiResponses(value = {
            @ApiResponse(code=HttpURLConnection.HTTP_OK, message = "OK", response = WordCountResponse.class),
            @ApiResponse(code=422, message="Invalid or Malformed Request Body"),
            @ApiResponse(code=500, message="Internal Server Error")
    })
    public WordCountResponse put(@Valid WordCountRequest request) {
        log.debug("Received request with id={} and message={}", request.getId(), request.getMessage());

        // Check if the set already houses this ID
        if(idSet.contains(request.getId())) {
            log.info("id={} already processed", request.getId());
            log.debug("id={} already processed, set={}", request.getId(), idSet);
            // Ignore message and return the current count
            return new WordCountResponse(totalCount.get());
        } else {
            // Add the id to the set
            idSet.add(request.getId());
            log.debug("Added id={} to set", request.getId());
            // Filter, add, and return
            return new WordCountResponse(
                    totalCount.addAndGet(Arrays.stream(request.getMessage().replaceAll("[^a-zA-Z\\s]", "").split(" "))
                            .filter(entry -> entry.matches("[a-zA-Z]+"))
                            .collect(Collectors.toList())
                            .size()
                    ));
        }
    }
}
