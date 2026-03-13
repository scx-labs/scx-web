package dev.scx.web.parameter_handler.last;

import dev.scx.reflect.ParameterInfo;
import dev.scx.web.parameter_handler.ParameterHandler;
import dev.scx.web.parameter_handler.RequestInfo;

/// LastParameterHandler
///
/// @author scx567888
/// @version 0.0.1
public final class LastParameterHandler implements ParameterHandler {

    private final ParameterInfo parameter;

    public LastParameterHandler(ParameterInfo parameter) {
        this.parameter = parameter;
    }

    @Override
    public Object handle(RequestInfo requestInfo) throws Exception {
        var javaType = parameter.parameterType();
        var name = parameter.name();
        //------ 这里针对没有注解的参数进行赋值猜测 ---------------
        //  从 body 里进行猜测 先尝试 根据参数名称进行转换
//        Object value = ignore(() -> getValueFromBody(name, false, false, javaType, requestInfo));
//        if (value == null) {
//            // 再尝试将整体转换为 参数
//            value = ignore(() -> getValueFromBody(null, true, false, javaType, requestInfo));
//            if (value == null) {
//                //从查询参数里进行猜测
//                value = ignore(() -> getValueFromQuery(name, false, false, javaType, requestInfo));
//                if (value == null) {
//                    //从路径进行猜测
//                    value = ignore(() -> getValueFromPath(name, false, false, javaType, requestInfo));
//                }
//            }
//        }
//        return value;
        return null;
    }

}
