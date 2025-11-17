package org.mylitespring.aop;

public interface PointCut {

    String getExpression();

    MethodMatcher getMethodMatcher();

}
