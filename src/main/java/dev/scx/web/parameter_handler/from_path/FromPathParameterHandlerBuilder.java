package dev.scx.web.parameter_handler.from_path;

import dev.scx.reflect.ParameterInfo;
import dev.scx.web.annotation.FromPath;
import dev.scx.web.parameter_handler.ParameterHandler;
import dev.scx.web.parameter_handler.ParameterHandlerBuilder;

/// FromPathParameterHandler
///
/// @author scx567888
/// @version 0.0.1
public final class FromPathParameterHandlerBuilder implements ParameterHandlerBuilder {

    @Override
    public ParameterHandler tryBuild(ParameterInfo parameter) {
        var fromPath = parameter.findAnnotation(FromPath.class);
        if (fromPath == null) {
            return null;
        }
        return new FromPathParameterHandler(fromPath, parameter);
    }

}
