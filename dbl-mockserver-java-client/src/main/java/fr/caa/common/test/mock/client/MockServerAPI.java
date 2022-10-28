package fr.caa.common.test.mock.client;

import java.io.IOException;

import org.apache.log4j.Logger;

import fr.caa.common.test.mock.MockServerInitializer;

import lombok.Builder;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Builder
public class MockServerAPI {

    private static Logger LOGGER = Logger.getLogger(MockServerAPI.class);

    @Builder.Default
    private JSONParser jsonParser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);

    private String mockAddr;

    @Builder.Default
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new DefaultContentTypeInterceptor("application/json")).build();

    public void addExpectation(final String expectation) throws IOException, ParseException {
        if (expectationExists(expectation)) {
            return;
        }

        RequestBody jsonBody = RequestBody.create(expectation, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(mockAddr + "/expectation").put(jsonBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                LOGGER.info(arg1.message());
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                LOGGER.error(arg1.getMessage(), arg1);
            }
        });
    }

    public boolean expectationExists(final String newExpectation) throws IOException, ParseException {
        RequestBody jsonBody = RequestBody.create(newExpectation, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(mockAddr + "/retrieve?format=json&type=active_expectations").put(jsonBody).build();

        Call call = client.newCall(request);
        Response searchExpectation = call.execute();

        JSONObject newExpectationObject = (JSONObject) jsonParser.parse(newExpectation);
        JSONArray expectations = (JSONArray) jsonParser.parse(searchExpectation.body().string());

        if (expectations.isEmpty()) {
            return false;
        }

        JSONObject newHttpRequest = (JSONObject) newExpectationObject.get("httpRequest");
        JSONObject existingHttpRequest = (JSONObject) ((JSONObject) expectations.get(0)).get("httpRequest");

        return newHttpRequest.get("path").equals(existingHttpRequest.get("path"));
    }

    public boolean reset() throws IOException, ParseException {
        Request request = new Request.Builder().url(mockAddr + "/reset")
                .put(RequestBody.create("", MediaType.parse("application/json; charset=utf-8"))).build();

        Call call = client.newCall(request);
        Response resetResponse = call.execute();

        return resetResponse.code() == 200;
    }
}
