package cdk.personal.repo.resources;
import cdk.personal.repo.model.WordCountRequest;
import cdk.personal.repo.model.WordCountResponse;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Path("/word-count")
@Produces(MediaType.APPLICATION_JSON)
@Api
public class WordCountResource {
    AtomicInteger totalCount = new AtomicInteger();
    Set<String> idSet = new HashSet<>();

    private static final Logger log = LoggerFactory.getLogger(WordCountResource.class);

    public WordCountResource() {
    }


    @Path("/post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public WordCountResponse post(@Valid WordCountRequest request) {
        log.debug("Received request with id={} and message={}", request.getId(), request.getMessage());

        if(idSet.contains(request.getId())) {
            log.info("id={} already processed", request.getId());
            log.debug("id={} already processed, set={}", request.getId(), idSet);
            return new WordCountResponse(totalCount.get());
        } else {
            idSet.add(request.getId());
            log.debug("Added id={} to set", request.getId());
            return new WordCountResponse(
                    totalCount.addAndGet(Arrays.stream(request.getMessage().replaceAll("[^a-zA-Z-\\s]", "").split(" "))
                            .filter(entry -> entry.matches("[a-zA-Z-]+"))
                            .collect(Collectors.toList())
                            .size()
                    ));
        }
    }
}
