package dev.scx.web.parameter_handler.from_body;

import dev.scx.web.annotation.FromBody;
import dev.scx.web.parameter_handler.ParameterHandler;
import dev.scx.web.parameter_handler.ParameterHandlerBuilder;
import dev.scx.reflect.ParameterInfo;

/// FromBodyParameterHandler
///
/// @author scx567888
/// @version 0.0.1
public final class FromBodyParameterHandlerBuilder implements ParameterHandlerBuilder {

    @Override
    public ParameterHandler tryBuild(ParameterInfo parameter) {
        var fromBody = parameter.findAnnotation(FromBody.class);
        if (fromBody == null) {
            return null;
        }
        return new FromBodyParameterHandler(fromBody, parameter);
    }

}
