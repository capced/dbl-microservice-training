package fr.caa.common.test.mock;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import static org.mockserver.model.HttpClassCallback.callback;

import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.*;

import fr.caa.common.test.mock.domain.CustomExpectation;
import fr.caa.common.test.mock.helper.JsonHelper;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MockServerCustomInitializer {

    private static Logger LOGGER = Logger.getLogger(MockServerCustomInitializer.class);

    @Builder.Default
    private String mockAddr = "http://localhost:1080/mockserver";

    @Builder.Default
    private String pathPrefix = "mock";

    @Builder.Default
    private String filePattern = ".*\\_expectations.json";

    public void initializeExpectations(final MockServerClient mockClient, final Boolean resetEnabled,
            String... exclusions) {
//        MockServerAPI mockapi = MockServerAPI.builder().mockAddr(mockAddr).build();

        if (resetEnabled) {
//                mockapi.reset();
            mockClient.reset();
        }

        if (ArrayUtils.isEmpty(exclusions)) {
            exclusions = new String[] {};
        }

        List<String> excludes = Arrays.asList(exclusions);
        Collection<String> resources = ClasspathMockResolver.listFiles(pathPrefix, filePattern);

        if (CollectionUtils.isEmpty(resources)) {
            LOGGER.error("!! No files suffixed with _expectations.json in the classpath !!");
        }

        for (String expect : resources) {
            try {
                if (!excludes.contains(expect)) {
                    String sourceExpectation = IOUtils.resourceToString(expect, StandardCharsets.UTF_8,
                            this.getClass().getClassLoader());
                    List<CustomExpectation> expectations = JsonHelper.fromString(sourceExpectation,
                            new ArrayList<CustomExpectation>() {
                            }.getClass().getGenericSuperclass());
                    HttpResponse thisResponse = null;
                    HttpRequest thisRequest = null;
                    for (CustomExpectation expectation : expectations) {
                        thisRequest = request().withPath(expectation.getHttpRequest().getPath());
                        if (StringUtils.isNotEmpty(expectation.getHttpRequest().getMethod())) {
                            thisRequest = thisRequest.withMethod(expectation.getHttpRequest().getMethod());
                        }
                        if (StringUtils.isNoneEmpty(expectation.getHttpRequest().getContentType())) {
                            thisRequest = thisRequest.withContentType(MediaType.APPLICATION_JSON);
                        }
                        if ((expectation.getHttpRequest().getHeaders() != null)
                                && (expectation.getHttpRequest().getHeaders().size() > 0)) {
                            for (String key : expectation.getHttpRequest().getHeaders().keySet()) {
                                thisRequest = thisRequest.withHeader(Header.header(key, expectation.getHttpRequest().getHeaders().get(key)));
                            }
                        }

                        thisResponse = response().withContentType(MediaType.APPLICATION_JSON)
                                .withStatusCode(expectation.getHttpResponse().getStatusCode());
                        if (StringUtils.isNotEmpty(expectation.getHttpResponse().getFilePath())) {
                            thisResponse = thisResponse.withBody(IOUtils.resourceToString(
                                    pathPrefix + "/" + expectation.getHttpResponse().getFilePath(),
                                    StandardCharsets.UTF_8, this.getClass().getClassLoader()));
                            mockClient.when(thisRequest).respond(thisResponse);
                        }
                        if (StringUtils.isNotEmpty(expectation.getHttpResponse().getCallbackClass())) {
                            HttpClassCallback callback = callback().withCallbackClass(expectation.getHttpResponse().getCallbackClass());
                            mockClient.when(thisRequest).respond(callback);
                        }


                    }
                }
            } catch (Exception e1) {
                LOGGER.error(e1.getMessage(), e1);
            }
        }
    }

    public static void main(String[] args) {
        String host = args[0];
        Integer port = Integer.valueOf(args[1]);
        String context = args[2];
        Boolean resetEnabled = false;
        String exclusions = "";

        if (args.length > 3) {
            resetEnabled = Boolean.valueOf(args[3]);
        }

        if (args.length > 4) {
            exclusions = args[4];
        }

        MockServerClient mockClient = new MockServerClient(host, port);

        MockServerCustomInitializer mockServer = MockServerCustomInitializer.builder()
                .mockAddr("http://" + host + ":" + port + "/" + context).build();
        mockServer.initializeExpectations(mockClient, resetEnabled, exclusions);
    }
}
