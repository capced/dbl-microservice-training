package com.capgemini.fs.dbl.digitalacademy.shoppingcart;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.model.MediaType;
import java.nio.charset.StandardCharsets;

import static org.mockserver.model.HttpResponse.response;

public class DynamicProductResponse implements org.mockserver.mock.action.ExpectationResponseCallback {
    public static String PRODUCT_API_PATH_PREFIX = "/products/";
    public static String PRODUCT_LIST_FILESOURCE = "mock/product/product_list_ok.json";

    public DynamicProductResponse() {

    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) throws Exception {
        String productId = StringUtils.substringAfter(httpRequest.getPath().getValue(), PRODUCT_API_PATH_PREFIX);

        String productsJson = IOUtils.resourceToString(PRODUCT_LIST_FILESOURCE, StandardCharsets.UTF_8,
            this.getClass().getClassLoader());
        JSONObject json = new JSONObject(productsJson);
        JSONArray products = json.getJSONObject("_embedded").getJSONArray("products");

        if (Integer.parseInt(productId) > products.length()) {
            return response().withContentType(MediaType.APPLICATION_JSON).withStatusCode(HttpStatusCode.NOT_FOUND_404.code());
        }

        HttpResponse thisResponse = response().withContentType(MediaType.APPLICATION_JSON)
            .withStatusCode(HttpStatusCode.OK_200.code());

        thisResponse = thisResponse.withBody(String.valueOf(products.get(Integer.parseInt(productId) - 1)));
        return thisResponse;
    }

}
