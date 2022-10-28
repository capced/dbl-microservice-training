package fr.caa.common.test.mock.domain;

import kotlin.Pair;
import lombok.Getter;
import lombok.Setter;
import org.mockserver.model.Header;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MockRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String path = "/";
    
    private String method = "GET";
    
    private String contentType = null;

    private Map<String, String> headers = new HashMap<>();

    {};
}
