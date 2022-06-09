package com.spring.board.advice;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

//@Component
//@EnableAspectJAutoProxy
//@Aspect
public class LoggingAdvice {
//	@Around("execution(* com.spring.board..*.*(..))")
//	public Object loggingAround(ProceedingJoinPoint pjp) throws Throwable {
//		System.out.println("Method Call================================");
//		System.out.println(pjp.getTarget() + "." + pjp.getSignature().getName());
//		System.out.println("ParamList================================");
//		System.out.println(Arrays.toString(pjp.getArgs()));
//		Object result = pjp.proceed();				
//		return result;	
//	}
}
