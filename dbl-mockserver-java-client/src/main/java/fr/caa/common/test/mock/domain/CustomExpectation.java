package fr.caa.common.test.mock.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomExpectation implements Serializable {

    private static final long serialVersionUID = 1L;

    private MockRequest httpRequest;
    
    private MockResponse httpResponse;
    
}
