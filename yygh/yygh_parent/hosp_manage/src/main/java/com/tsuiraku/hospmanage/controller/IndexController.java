package com.tsuiraku.hospmanage.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author qy
 *
 */
@Controller
public class IndexController {

	private final String LIST_INDEX = "redirect:/";

	private final static String PAGE_INDEX  = "frame/index";
	private final static String PAGE_MAIN  = "frame/main";
	private final static String PAGE_LOGIN  = "frame/login";
	private final static String PAGE_AUTH = "frame/auth";
	
	/**
	 * 框架首页
	 * @return
	 */
	@RequestMapping(value = "/")
	public String index(ModelMap model, HttpServletRequest request) {

		return PAGE_INDEX;
	}

	/**
	 * 框架主页
	 * @return
	 */
	@RequestMapping(value = "/main",method = RequestMethod.GET)
	public String main() {

		return PAGE_MAIN;
	}

	/**
	 * 框架主页
	 *
	 * @return
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public String auth() {
		return PAGE_AUTH;
	}

	/**
	 * 登录
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login() {

		return PAGE_LOGIN;
	}

}

