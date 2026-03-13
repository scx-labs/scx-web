package dev.scx.web;

import dev.scx.web.annotation.ScxRoute;
import dev.scx.web.parameter_handler.ParameterHandler;
import dev.scx.function.Function1Void;
import dev.scx.http.method.HttpMethod;
import dev.scx.http.method.ScxHttpMethod;
import dev.scx.http.routing.Route;
import dev.scx.http.routing.RoutingContext;
import dev.scx.http.routing.method_matcher.MethodMatcher;
import dev.scx.http.routing.path_matcher.PathMatcher;
import dev.scx.http.routing.request_matcher.RequestMatcher;
import dev.scx.reflect.MethodInfo;
import dev.scx.websocket.x.ScxServerWebSocketHandshakeRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static dev.scx.web.RouteRegistrar.findScxRouteOrThrow;
import static dev.scx.web.ScxWeb.ROUTING_CONTEXT_SCOPED_VALUE;
import static dev.scx.constant.AnnotationValues.getRealValue;

/// ScxRouteHandler
///
/// @author scx567888
/// @version 0.0.1
public final class ScxRouteHandler implements Route, Function1Void<RoutingContext, Throwable> {

    public final MethodInfo method;
    public final boolean isVoid;
    public final Object instance;
    public final Class<?> clazz;
    private final ScxWeb scxWeb;
    public final String path;
    public final Set<HttpMethod> methods;
    private final int order;
    private final RequestMatcher typeMatcher;
    private final PathMatcher pathMatcher;
    private final MethodMatcher methodMatcher;
    private final ParameterHandler[] parameterHandlers;

    ScxRouteHandler(MethodInfo method, Object instance, ScxWeb scxWeb) {
        this.scxWeb = scxWeb;
        this.clazz = instance.getClass();
        this.method = method;
        this.method.setAccessible(true);
        this.isVoid = method.returnType().rawClass() == void.class;
        this.instance = instance;
        //根据注解初始化值
        var clazzAnnotation = clazz.getAnnotation(ScxRoute.class);
        var methodAnnotation = findScxRouteOrThrow(method);
        this.path = initPath(clazzAnnotation, methodAnnotation);
        this.methods = Set.of(methodAnnotation.methods());
        this.order = methodAnnotation.order();
        this.typeMatcher = RequestMatcher.typeNot(ScxServerWebSocketHandshakeRequest.class);
        this.pathMatcher = path.isBlank() ? PathMatcher.any() : PathMatcher.ofTemplate(path);
        this.methodMatcher = methods.isEmpty() ? MethodMatcher.any() : MethodMatcher.of(methods.toArray(ScxHttpMethod[]::new));
        this.parameterHandlers = scxWeb.buildParameterHandlers(this.method.parameters());
    }

    private String initPath(ScxRoute classAnnotation, ScxRoute methodAnnotation) {
        var classUrl = "";
        var methodUrl = "";
        //处理 类 级别的注解的 url
        if (classAnnotation != null && !methodAnnotation.ignoreParentUrl()) {
            var value = getRealValue(classAnnotation.value());
            if (value != null) {
                classUrl = value;
            }
        }
        // todo 待优化
        //处理 方法 级别的注解的 url
//        var value = getRealValue(methodAnnotation.value());
//        if (value != null) {
//            methodUrl = value;
//        } else if (methodAnnotation.useNameAsUrl()) {
//            methodUrl = CaseUtils.toKebab(this.method.name());
//        }
//        return URIUtils.addSlashStart(URIUtils.join(classUrl, methodUrl));
        return null;
    }

    @Override
    public void apply(RoutingContext context) throws Throwable {
        ScopedValue.where(ROUTING_CONTEXT_SCOPED_VALUE, context).call(() -> {
            this.accept0(context);
            return null;
        });
    }

    public void accept0(RoutingContext context) throws Throwable {
        try {
            //1, 执行前置处理器 (一般用于校验权限之类)
            this.scxWeb.interceptor().preHandle(context, this);
            //2, 根据 method 参数获取 invoke 时的参数
            var methodParameters = this.scxWeb.buildMethodParameters(parameterHandlers, context);
            //3, 执行具体方法 (用来从请求中获取参数并执行反射调用方法以获取返回值)
            var tempResult = this.method.invoke(this.instance, methodParameters);
            //4, 执行后置处理器
            var finalResult = this.scxWeb.interceptor().postHandle(context, this, tempResult);
            //5, 如果方法返回值不为 void 并且 response 可用 , 则调用返回值处理器
            if (!isVoid) {
                this.scxWeb.findReturnValueHandler(finalResult).handle(finalResult, context);
            }
        } catch (Throwable e) {
            //1, 如果是反射调用时发生异常 则使用反射异常的内部异常 否则使用异常
            //2, 如果是包装类型异常 (ScxWrappedRuntimeException) 则使用其内部的异常
            throw e instanceof InvocationTargetException ? e.getCause() : e;
        }
    }

    @Override
    public RequestMatcher requestMatcher() {
        return typeMatcher;
    }

    @Override
    public PathMatcher pathMatcher() {
        return pathMatcher;
    }

    @Override
    public MethodMatcher methodMatcher() {
        return methodMatcher;
    }

    public int order() {
        return order;
    }

    @Override
    public Function1Void<RoutingContext, Throwable> handler() {
        return this;
    }

}
