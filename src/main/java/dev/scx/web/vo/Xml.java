package dev.scx.web.vo;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.http.media_type.ScxMediaType;

import static dev.scx.http.media_type.MediaType.APPLICATION_XML;
import static dev.scx.serialize.ScxSerialize.toXml;
import static java.nio.charset.StandardCharsets.UTF_8;

/// Xml 格式的返回值
///
/// @author scx567888
/// @version 0.0.1
public final class Xml implements BaseVo {

    private final Object data;

    private Xml(Object data) {
        this.data = data;
    }

    public static Xml of(Object data) {
        return new Xml(data);
    }

    @Override
    public void apply(ScxHttpServerRequest request) {
        request.response()
            .contentType(ScxMediaType.of(APPLICATION_XML).charset(UTF_8))
            .send(toXml(data));
    }

}
