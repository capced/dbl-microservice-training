package fr.caa.common.test.mock.sample;

import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.model.MediaType;

import static org.mockserver.model.HttpResponse.response;

public class SampleCallbackResponse implements org.mockserver.mock.action.ExpectationResponseCallback {

    public SampleCallbackResponse() {

    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        HttpResponse thisResponse = response().withContentType(MediaType.APPLICATION_JSON)
            .withStatusCode(HttpStatusCode.OK_200.code());

        // TODO: what do you wan't to do
        //thisResponse = thisResponse.withBody("<your response here>");
        return thisResponse;
    }

}
