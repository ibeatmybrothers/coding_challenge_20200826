package cdk.personal.repo;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
        // TODO: application initialization
    }

    @Override
    public void run(final WordCountConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
