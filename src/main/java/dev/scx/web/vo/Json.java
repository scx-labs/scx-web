package dev.scx.web.vo;

import dev.scx.http.media_type.ScxMediaType;
import dev.scx.http.routing.RoutingContext;

import static dev.scx.http.media_type.MediaType.APPLICATION_JSON;
import static java.nio.charset.StandardCharsets.UTF_8;

/// Json 格式的返回值
///
/// @author scx567888
/// @version 0.0.1
public final class Json implements BaseVo {

    private final Object data;

    private Json(Object data) {
        this.data = data;
    }

    public static Json of(Object data) {
        return new Json(data);
    }

    @Override
    public void apply(RoutingContext context) {
        // todo
        context.request().response()
                .contentType(ScxMediaType.of(APPLICATION_JSON).charset(UTF_8))
                .send(data.toString());
    }

}
