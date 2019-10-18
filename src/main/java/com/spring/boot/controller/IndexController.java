package com.spring.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.spring.boot.constant.DeclaracionVariable;

@Controller
public class IndexController {


	@GetMapping("/")
	public String indexController() {
		return DeclaracionVariable.INDEX;
	}
}
