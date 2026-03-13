package dev.scx.web.return_value_handler;

import dev.scx.http.routing.RoutingContext;
import dev.scx.web.result.WebResult;

/// BaseVo 处理器
///
/// @author scx567888
/// @version 0.0.1
public final class BaseVoReturnValueHandler implements ReturnValueHandler {

    @Override
    public boolean canHandle(Object returnValue) {
        return returnValue instanceof WebResult;
    }

    @Override
    public void handle(Object returnValue, RoutingContext routingContext) throws Exception {
        if (returnValue instanceof WebResult baseVo) {
            baseVo.apply(routingContext);
        } else {
            throw new IllegalArgumentException("参数不是 BaseVo 类型 !!! " + returnValue.getClass());
        }
    }

}
