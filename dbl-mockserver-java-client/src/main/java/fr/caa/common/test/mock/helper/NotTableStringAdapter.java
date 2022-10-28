package fr.caa.common.test.mock.helper;

import java.lang.reflect.Type;

import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.adapter.JsonbAdapter;

import org.mockserver.model.HttpRequest;
import org.mockserver.model.NottableString;

public class NotTableStringAdapter implements JsonbAdapter<NottableString, String> {

    @Override
    public NottableString adaptFromJson(String arg0) throws Exception {
        return NottableString.string(arg0);
    }

    @Override
    public String adaptToJson(NottableString arg0) throws Exception {
        return arg0.getValue();
    }
}
