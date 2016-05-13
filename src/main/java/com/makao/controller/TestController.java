package com.makao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.makao.auth.AuthPassport;

/**
 * @description: TODO
 * @author makao
 * @date 2016年5月10日
 */
@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value="/supervisor")
	public String supervisor(){
		return "s";
	}
	
	@RequestMapping(value="/vendor")
	public String vendor(){
		return "v_orderOn";
	}
	@RequestMapping(value="/login", method = {RequestMethod.GET})
    public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.addObject("message", "Hello World!");  
	    modelAndView.setViewName("login");  
	    return modelAndView;
	}
	
	@AuthPassport
	@RequestMapping(value={"/index"})
	public ModelAndView index(){
	    
	    ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.addObject("message", "Hello World!");  
	    modelAndView.setViewName("index");  
	    return modelAndView;
	}
	
}
