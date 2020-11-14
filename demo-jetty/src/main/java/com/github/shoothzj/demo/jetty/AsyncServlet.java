package com.github.shoothzj.demo.jetty;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author hezhangjian
 */
@Slf4j
public class AsyncServlet extends HttpServlet {

    private static String HEAVY_SOURCE = "This is some heavy resource that will be served in an async way";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ByteBuffer content = ByteBuffer.wrap(HEAVY_SOURCE.getBytes(StandardCharsets.UTF_8));
        final AsyncContext async = req.startAsync();
        final ServletOutputStream servletOutputStream = resp.getOutputStream();
        servletOutputStream.setWriteListener(new WriteListener() {
            @Override
            public void onWritePossible() throws IOException {
                while (servletOutputStream.isReady()) {
                    if (!content.hasRemaining()) {
                        resp.setStatus(200);
                        async.complete();
                        return;
                    }
                    servletOutputStream.write(content.get());
                }
            }

            @Override
            public void onError(Throwable t) {
                getServletContext().log("Async Error", t);
                async.complete();
            }
        });
    }
}
