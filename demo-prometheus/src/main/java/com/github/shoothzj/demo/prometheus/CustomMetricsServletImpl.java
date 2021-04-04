package com.github.shoothzj.demo.prometheus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;

public class CustomMetricsServletImpl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(200);
        resp.setContentType("text/plain; version=0.0.4; charset=utf-8");
        BufferedWriter writer = new BufferedWriter(resp.getWriter());
        writer.write("");
        writer.flush();
        writer.close();
    }
}