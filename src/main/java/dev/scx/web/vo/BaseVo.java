package dev.scx.web.vo;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.web.WebContext;

/// BaseVo 接口
///
/// @author scx567888
/// @version 0.0.1
public interface BaseVo {

    void apply(ScxHttpServerRequest request, WebContext webContext) throws Throwable;

}
