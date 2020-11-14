package com.github.shoothzj.demo.jetty;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author hezhangjian
 */
@Slf4j
public class JettyServer {

    private Server server;

    public static void main(String[] args) throws Exception {
        final JettyServer jettyServer = new JettyServer();
        jettyServer.start();
    }

    public void start() throws Exception {
        server = new Server();
        final ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});
        final ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(BlockingServlet.class, "/status");
        servletHandler.addServletWithMapping(AsyncServlet.class, "/heavy/async");
        server.setHandler(servletHandler);
        server.start();
    }

    @Test
    public void clientStatus() throws IOException {
        String url = "http://localhost:8090/status";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);

        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    }

}
