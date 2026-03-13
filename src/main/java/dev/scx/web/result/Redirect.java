package dev.scx.web.result;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.http.status_code.ScxHttpStatusCode;
import dev.scx.web.WebContext;

import static dev.scx.http.headers.HttpHeaderName.LOCATION;
import static dev.scx.http.status_code.HttpStatusCode.FOUND;
import static dev.scx.http.status_code.HttpStatusCode.MOVED_PERMANENTLY;

/// 重定向
///
/// @author scx567888
/// @version 0.0.1
public final class Redirect implements WebResult {

    private final String location;
    private final ScxHttpStatusCode status;

    private Redirect(String location, ScxHttpStatusCode status) {
        this.location = location;
        this.status = status;
    }

    /// 永久重定向
    ///
    /// @param location 重定向地址
    /// @return r
    public static Redirect ofPermanent(String location) {
        return new Redirect(location, MOVED_PERMANENTLY);
    }

    /// 临时重定向
    ///
    /// @param location 重定向地址
    /// @return r
    public static Redirect ofTemporary(String location) {
        return new Redirect(location, FOUND);
    }

    @Override
    public void apply(ScxHttpServerRequest request, WebContext webContext) throws Throwable {
        request.response().setHeader(LOCATION, location).statusCode(status).send();
    }

}
