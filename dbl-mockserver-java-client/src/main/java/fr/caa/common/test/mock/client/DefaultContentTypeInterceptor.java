package fr.caa.common.test.mock.client;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultContentTypeInterceptor implements Interceptor {
	
	private String contentType;
	
	public DefaultContentTypeInterceptor(final String contentType) {
		this.contentType = contentType;
	}
    
    public Response intercept(Interceptor.Chain chain) 
      throws IOException {

        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest
          .newBuilder()
          .header("Content-Type", contentType)
          .build();

        return chain.proceed(requestWithUserAgent);
    }
}