package com.github.shoothzj.demo.agent.match;

import net.bytebuddy.matcher.ElementMatcher;

import java.util.Set;

/**
 * @author hezhangjian
 */
public class AnnotationNameMatcher<T> extends ElementMatcher.Junction.AbstractBase<T> {

    private Set<String> annotationNameSet;

    public AnnotationNameMatcher(Set<String> annotationNameSet) {

    }

    @Override
    public boolean matches(T t) {
        return false;
    }

}
