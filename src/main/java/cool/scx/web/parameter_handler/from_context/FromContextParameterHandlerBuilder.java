package cool.scx.web.parameter_handler.from_context;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.http.ScxHttpServerResponse;
import dev.scx.http.headers.ScxHttpHeaders;
import dev.scx.http.headers.cookie.Cookies;
import dev.scx.http.routing.RoutingContext;
import dev.scx.io.ByteInput;
import dev.scx.reflect.ParameterInfo;
import cool.scx.web.parameter_handler.ParameterHandler;
import cool.scx.web.parameter_handler.ParameterHandlerBuilder;
import cool.scx.web.parameter_handler.RequestInfo;

/// 类型为 基本 的参数处理器
///
/// @author scx567888
/// @version 0.0.1
public final class FromContextParameterHandlerBuilder implements ParameterHandlerBuilder {

    @Override
    public ParameterHandler tryBuild(ParameterInfo parameter) {
        // todo 这里可能有问题
        var rawClass = parameter.parameterType().rawClass();
        if (rawClass == RoutingContext.class) {
            return RequestInfo::routingContext;
        }
        if (rawClass == ScxHttpServerRequest.class) {
            return (requestInfo) -> requestInfo.routingContext().request();
        }
        if (rawClass == ScxHttpServerResponse.class) {
            return (requestInfo) -> requestInfo.routingContext().request().response();
        }
        if (rawClass == ScxHttpHeaders.class) {
            return (requestInfo) -> requestInfo.routingContext().request().headers();
        }
        if (rawClass == ByteInput.class) {
            return (requestInfo) -> requestInfo.routingContext().request().body();
        }
        if (rawClass == Cookies.class) {
            return (requestInfo) -> requestInfo.routingContext().request().cookies();
        }
        return null;
    }

}
