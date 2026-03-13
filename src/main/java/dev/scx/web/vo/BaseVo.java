package dev.scx.web.vo;

import dev.scx.http.ScxHttpServerRequest;

/// BaseVo 接口
///
/// @author scx567888
/// @version 0.0.1
public interface BaseVo {

    void apply(ScxHttpServerRequest request) throws Throwable;

}
