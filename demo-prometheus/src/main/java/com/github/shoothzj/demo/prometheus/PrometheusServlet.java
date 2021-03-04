package com.github.shoothzj.demo.prometheus;

import io.prometheus.client.exporter.common.TextFormat;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author hezhangjian
 */
@Slf4j
public class PrometheusServlet extends HttpServlet {

    PrometheusMetricsProvider provider;

    public PrometheusServlet(PrometheusMetricsProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(TextFormat.CONTENT_TYPE_004);

        try (Writer writer = resp.getWriter()) {
            provider.writeAllMetrics(writer);
            writer.flush();
        }
    }

}
