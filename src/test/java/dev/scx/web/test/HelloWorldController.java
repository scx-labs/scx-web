package dev.scx.web.test;

import dev.scx.http.exception.ForbiddenException;
import dev.scx.web.annotation.ScxRoute;
import dev.scx.web.vo.Result;

@ScxRoute
public class HelloWorldController {

    @ScxRoute("hello")
    public Object hello() {
        return Result.ok().put("name", "scx567888😁");
    }

    @ScxRoute("no-perm")
    public Object noPerm() {
        throw new ForbiddenException();
    }

}
