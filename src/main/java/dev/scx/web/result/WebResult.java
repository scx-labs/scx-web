package dev.scx.web.result;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.web.WebContext;

/// WebResult 接口
///
/// @author scx567888
/// @version 0.0.1
public interface WebResult {

    void apply(ScxHttpServerRequest request, WebContext webContext) throws Throwable;

}
