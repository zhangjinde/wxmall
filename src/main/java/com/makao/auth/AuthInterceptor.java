package com.makao.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.makao.controller.UserController;
import com.makao.entity.TokenModel;
import com.makao.utils.MakaoConstants;
import com.makao.utils.TokenManager;
import com.makao.utils.TokenUtils;

/**
 * @description: TODO
 * @author makao
 * @date 2016年5月10日 简单的角色权限验证：
 *       1.因为restful是无状态的，为了记录登录情况，在登录时为用户生成一个token，对于标注了AuthPassport注解的
 *       请求，都会通过该拦截器拦截验证
 *       2.拦截器除了进行用户是否登录验证外，还进行角色权限验证。在token中记录用户名，身份(在其登录时访问的url路径中获取)，登录时间。
 *       3.验证流程： 对于访问AuthPassport注解的url，从request中获取其token，
 *       1)如果为空，拦截，返回到登录页面(从url路径中判断将其导向到supervisor还是vendor)
 *       2)token不为空，查找服务器缓存是否有该token
 *       A)没有，拦截，返回到登录页面(从url路径中判断将其导向到supervisor还是vendor)
 *       B)服务器有该token，解析出登录用户名，用户身份，登录时间 a)检验登录时间与当前时间差，判断token是否过期
 *       b)检查用户访问路径是否与服务器中该token解析出的用户身份对应
 * 
 *       对于访问Supervisor专用接口注解的url，出上面的检查外，还要必须是supervisor身份
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger
			.getLogger(AuthInterceptor.class);
	@Autowired
	private TokenManager tokenManager;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI();// 获取到的是/product/1这类地址
		String method = request.getMethod();

		System.out.println("***********拦截器************");
		
		if (MakaoConstants.DEBUG){
			TokenModel tm = new TokenModel(1,"u","test");
			tm.setOpenid("aaaaa");
			request.setAttribute("tokenmodel", tm);
			return true;
		}
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {// 判断该请求的路径对应的方法有没有AuthPassport标注，即有没有需要验证
			AuthPassport authPassport = ((HandlerMethod) handler)
					.getMethodAnnotation(AuthPassport.class);

			// 没有声明需要权限,或者声明不验证权限
			if (authPassport == null || authPassport.validate() == false)
				return true;
			else {
				// 要验证的都有token，没有token则失败
				String token = request.getParameter("token");
				if (token == null || "".equals(token.trim())) {
					token = request.getHeader("token");
					if (token == null || "".equals(token.trim())) {
						if(MakaoConstants.DEBUG){
							return true;
						}
						logger.info("*****" + url + " 的 " + method
								+ " 方法需要验证token，但token不存在*****");
						tokenFailResponse(response, "{\"msg\":401}");
						return false;
					}
				}
				System.out.println("2");
				String type = "";// 请求是用户、区域管理还是超级管理
				if (token.length() == 36) {
					type = "u";
					if(MakaoConstants.DEBUG)
						return true;
				} else if (token.length() > 36
						&& "v".equals(token.substring(token.length() - 1))) {// vendor请求
					type = "v";
				} else {
					type = "s";
				}System.out.println("1");
				
				boolean isValid = false;
				TokenModel tm = null;
				switch (type) {
				case "u":
					logger.info("handle user auth");
					tm = tokenManager.getUserToken(token);
					isValid = tokenManager.checkUserToken(tm, token);
					if (!isValid) {
						tokenFailResponse(response, "{\"msg\":401}");
					} else {// 如果token验证成功，将token对应的TokenModel存在request中，便于之后取
						request.setAttribute("tokenmodel", tm);
					}
					return isValid;
				case "v":
					logger.info("handle vendor auth");
					tm = tokenManager.getToken(token);
					isValid = tokenManager.checkToken(tm, token);
					System.out.println("3");
					if (!isValid) {
						tokenFailResponse(response, "{\"msg\":401}");
					} else {// 如果token验证成功，将token对应的TokenModel存在request中，便于之后取
						request.setAttribute("tokenmodel", tm);
					}
					return isValid;
				case "s":
					logger.info("handle supervisor auth");
					tm = tokenManager.getToken(token);
					isValid = tokenManager.checkToken(tm, token);
					if (!isValid) {
						tokenFailResponse(response, "{\"msg\":401}");
					} else {// 如果token验证成功，将token对应的TokenModel存在request中，便于之后取
						request.setAttribute("tokenmodel", tm);
					}
					return isValid;
				default:
					tokenFailResponse(response,"{\"msg\":401}");
					return false;
				}
			}
		} else {
			return true;
		}
	}

	/**
	 * @param string
	 * @param position
	 * @return 在string里找'/'第position次出现的坐标
	 */
	private static int getCharacterPosition(String string, int position) {
		// 这里是获取"/"符号的位置
		Matcher slashMatcher = Pattern.compile("/").matcher(string);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			// 当"/"符号第三次出现的位置
			if (mIdx == position) {
				break;
			}
		}
		return slashMatcher.start();
	}

	/**
	 * @param response
	 * @throws IOException
	 *             token验证失败时，向前端返回内容
	 */
	private void tokenFailResponse(HttpServletResponse response, String msg)
			throws IOException {
		response.reset();
		response.setHeader("content-type", "application/json;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		// 注意out的什么不能在preHandler中return为true经过的路径上，否则会报错:
		// java.lang.IllegalStateException: getWriter() has already been called
		// for this response
		// 因为true后再执行后面Controller中@responseBody
		// 返回json格式字符串时会自动调用getOutputStream方法。
		// 一个请求不能有两个getWriter()或getOutputStream()
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
	}
}
