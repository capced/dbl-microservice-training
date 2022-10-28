package fr.caa.common.test.mock.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MockResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String filePath;

    private String callbackClass;

    private Integer statusCode = 200;
    
}
