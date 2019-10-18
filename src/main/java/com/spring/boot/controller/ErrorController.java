package com.spring.boot.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.spring.boot.constant.DeclaracionVariable;

@ControllerAdvice
public class ErrorController {	
	@ExceptionHandler(Exception.class)
	public String ShowInternalServerError() {
		return DeclaracionVariable.ERROR_500;
	}
}
