package com.github.shoothzj.demo.prometheus;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * @author hezhangjian
 */
@Slf4j
public class PrometheusJettyServer {

    private Server server;

    public static void main(String[] args) throws Exception {
        final PrometheusJettyServer jettyServer = new PrometheusJettyServer();
        jettyServer.start();
    }

    public void start() throws Exception {
        server = new Server();
        final ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});
        final ServletHandler servletHandler = new ServletHandler();
        final ServletHolder promServletHolder = new ServletHolder(new PrometheusServlet(new PrometheusMetricsProvider()));
        final ServletHolder customServletHolder = new ServletHolder(new CustomMetricsServletImpl());
        servletHandler.addServletWithMapping(promServletHolder, "/metrics");
        servletHandler.addServletWithMapping(customServletHolder, "/custom");
        server.setHandler(servletHandler);
        server.start();
    }

}
