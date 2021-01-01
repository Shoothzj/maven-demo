package com.github.shoothzj.demo.agent.test.ex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Slf4j
@RestControllerAdvice("com.github.shoothzj.demo.agent.test.controller")
public class RestExceptionAdvice {

    private final MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(mappingJackson2JsonView);
        if (ex instanceof RestException) {
            RestException restException = (RestException) ex;
            modelAndView.addObject("reason", "restException");
            modelAndView.setStatus(HttpStatus.valueOf(restException.getStatusCode()));
        } else {
            modelAndView.addObject("reason", "otherException");
            modelAndView.setStatus(HttpStatus.valueOf(500));
        }
        return modelAndView;
    }

}
