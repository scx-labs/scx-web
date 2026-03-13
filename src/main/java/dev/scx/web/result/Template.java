package dev.scx.web.result;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.web.WebContext;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static dev.scx.http.media_type.MediaType.TEXT_HTML;
import static java.nio.charset.StandardCharsets.UTF_8;

/// Template
///
/// @author scx567888
/// @version 0.0.1
public final class Template implements WebResult {

    private final String templatePath;
    private final Map<String, Object> dataMap = new HashMap<>();

    private Template(String templatePath) {
        this.templatePath = templatePath;
    }

    public static Template of(String templatePath) {
        return new Template(templatePath);
    }

    public Template add(String key, Object value) {
        dataMap.put(key, value);
        return this;
    }

    @Override
    public void apply(ScxHttpServerRequest request, WebContext webContext) throws Throwable {
        var templateHandler = webContext.templateHandler();
        if (templateHandler == null) {
            throw new IllegalStateException("No templateHandler configured in WebContext");
        }

        var sw = new StringWriter();
        var template = templateHandler.getTemplate(templatePath);
        template.process(dataMap, sw);

        request.response()
            .contentType(ScxMediaType.of(TEXT_HTML).charset(UTF_8))
            .send(sw.toString());
    }

}
