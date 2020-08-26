package cdk.personal.repo;

import cdk.personal.repo.resources.WordCountResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class WordCountApplication extends Application<WordCountConfiguration> {

    public static void main(final String[] args) throws Exception {
        new WordCountApplication().run(args);
    }

    @Override
    public String getName() {
        return "WordCount";
    }

    @Override
    public void initialize(final Bootstrap<WordCountConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<WordCountConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(WordCountConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final WordCountConfiguration configuration,
                    final Environment environment) {
        final WordCountResource resource = new WordCountResource();

        environment.jersey().register(resource);
    }

}
