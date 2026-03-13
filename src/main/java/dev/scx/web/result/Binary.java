package dev.scx.web.result;

import dev.scx.http.ScxHttpServerRequest;
import dev.scx.http.ScxHttpServerResponse;
import dev.scx.http.media_type.MediaType;
import dev.scx.http.media_type.ScxMediaType;
import dev.scx.http.routing.x.static_files.StaticFilesSupport;
import dev.scx.web.WebContext;

import java.io.File;
import java.io.InputStream;

import static dev.scx.http.media_type.MediaType.APPLICATION_OCTET_STREAM;
import static java.nio.charset.StandardCharsets.UTF_8;

/// 基本写入程序 可以直接向相应体中写入数据
///
/// @author scx567888
/// @version 0.0.1
public class Binary implements WebResult {

    protected final Object bin;

    protected Binary(InputStream inputStream) {
        this.bin = inputStream;
    }

    protected Binary(File file) {
        this.bin = file;
    }

    protected Binary(byte[] bytes) {
        this.bin = bytes;
    }

    public static ScxHttpServerResponse fillContentType(ScxHttpServerResponse response, MediaType contentType) {
        if (contentType == null) {
            return response.contentType(APPLICATION_OCTET_STREAM);
        }
        //文本我们统一加上 UTF-8 编码
        if (contentType.type().equals("text") && contentType.charset() == null) {
            return response.contentType(ScxMediaType.of(contentType).charset(UTF_8));
        } else {
            return response.contentType(contentType);
        }
    }

    @Override
    public final void apply(ScxHttpServerRequest request, WebContext webContext) {
        var response = request.response();
        switch (bin) {
            case byte[] bytes -> response.send(bytes);
            case File file -> StaticFilesSupport.sendFile(file, request);
            case InputStream inputStream -> response.send(inputStream);
            default -> throw new IllegalStateException("Unexpected value: " + bin.getClass());
        }
    }

}
