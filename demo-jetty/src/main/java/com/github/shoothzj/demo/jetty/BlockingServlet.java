package com.github.shoothzj.demo.jetty;

import com.github.shoothzj.demo.jetty.module.JettyPutDto;
import com.github.shoothzj.javatool.service.JacksonService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hezhangjian
 */
@Slf4j
public class BlockingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        final JettyPutDto jettyPutDto = new JettyPutDto();
        jettyPutDto.setStatus("ok");
        resp.getWriter().println(JacksonService.toJson(jettyPutDto));
    }


}
