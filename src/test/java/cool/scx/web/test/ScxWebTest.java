package cool.scx.web.test;

import dev.scx.http.exception.ForbiddenException;
import dev.scx.http.routing.Route;
import dev.scx.http.routing.Router;
import dev.scx.http.x.HttpServer;
import cool.scx.web.ScxWeb;
import org.testng.annotations.Test;

import java.io.IOException;

import static dev.scx.http.status_code.HttpStatusCode.FORBIDDEN;

public class ScxWebTest {

    public static void main(String[] args) throws IOException {
        test0();
        test1();
    }

    /// 测试 bindErrorHandler
    @Test
    public static void test0() throws IOException {
        var httpServer = new HttpServer();

        var router = Router.of();

        router.route("/no-perm",c -> {
            //这里可以直接抛出 异常
            throw new ForbiddenException(new RuntimeException("你没有权限 !!!"));
        });

        router.route("/no-perm2",c -> {
            //或者用这种 httpServer 的形式 和上方是一样的
            c.request().response().statusCode(FORBIDDEN).send("Error");
        });

        httpServer.onRequest(router).start(8080);

        for (var route : router.routeTable().entries()) {
            System.out.println("http://127.0.0.1:" + httpServer.localAddress().getPort() + route.route().pathMatcher());
        }

    }

    /// 测试  registerHttpRoutes
    public static void test1() throws IOException {

        var httpServer = new HttpServer();

        var router = Router.of();

        // 直接将 class 扫描并注册到 router 中 这样可以实现类似 spring mvc 的写法
        // 具体参照 HelloWorldController
        new ScxWeb().registerHttpRoutes(router, new HelloWorldController());

        // 原有的并不会收到任何影响
        router.route("/my-route",c -> {
            //这里直接抛出会由 ScxWeb 进行处理
            c.request().response().send("my-route");
        });

        httpServer.onRequest(router).start(8081);

        for (var route : router.routeTable().entries()) {
            System.out.println("http://127.0.0.1:" + httpServer.localAddress().getPort() + route.route().pathMatcher());
        }

    }

}
