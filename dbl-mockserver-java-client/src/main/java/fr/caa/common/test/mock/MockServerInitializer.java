package fr.caa.common.test.mock;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import fr.caa.common.test.mock.client.MockServerAPI;
import lombok.Builder;
import lombok.Getter;
import net.minidev.json.parser.ParseException;

@Getter
@Builder
public class MockServerInitializer {

    private static Logger LOGGER = Logger.getLogger(MockServerInitializer.class);

    @Builder.Default
    private String mockAddr = "http://localhost:1080/mockserver";

    @Builder.Default
    private String pathPrefix = "mock";

    @Builder.Default
    private String filePattern = ".*\\.json";

    public void initializeExpectations(final Boolean resetEnabled, String... exclusions) {
        MockServerAPI mockapi = MockServerAPI.builder().mockAddr(mockAddr).build();

        try {
            if (resetEnabled) {
                mockapi.reset();
            }
        } catch (IOException e2) {
            LOGGER.error(e2.getMessage(), e2);
        } catch (ParseException e2) {
            LOGGER.error(e2.getMessage(), e2);
        }

        if (ArrayUtils.isEmpty(exclusions)) {
            exclusions = new String[] {};
        }
        List<String> excludes = Arrays.asList(exclusions);
        ClasspathMockResolver.listFiles(pathPrefix, filePattern).stream().forEach(t -> {
            try {
                if (!excludes.contains(t)) {
                    mockapi.addExpectation(
                            IOUtils.resourceToString(t, StandardCharsets.UTF_8, this.getClass().getClassLoader()));
                }
            } catch (IOException e1) {
                LOGGER.error(e1.getMessage(), e1);
            } catch (ParseException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }

    public static void main(String[] args) {
        String host = args[0];
        Integer port = Integer.valueOf(args[1]);
        String context = args[2];
        Boolean resetEnabled = false;
        String exclusions =  "";

        if (args.length > 3) {
            resetEnabled = Boolean.valueOf(args[3]);
        }
        
        if (args.length > 4) {
            exclusions = args[4];
        }
        

        MockServerInitializer mockServer = MockServerInitializer.builder()
                .mockAddr("http://" + host + ":" + port + "/" + context).build();
        mockServer.initializeExpectations(resetEnabled, exclusions);
    }
}
