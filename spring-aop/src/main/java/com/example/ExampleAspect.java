package com.example;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ExampleAspect {
   @Around("execution(** com.example.ExampleBean.exampleMethod(..))")
   public String exampleAdvice(ProceedingJoinPoint jp) throws Throwable {
      String result = (String) jp.proceed();
      return result + "c";
   }
}
