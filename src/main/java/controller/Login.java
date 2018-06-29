package controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import service.LoginService;


@Controller
public class Login {
	@Autowired
	LoginService loginservice;
	
	@RequestMapping("/loginvalidate")
	public String loginvalidate(@RequestParam("username") String username,@RequestParam("pic") String pic,@RequestParam("password") String pwd,HttpSession httpSession){
		String picode=(String) httpSession.getAttribute("rand");
		picode = picode.toUpperCase();
		if(!picode.equals(pic.toUpperCase())) {
			httpSession.setAttribute("tmp_username", username);
			return "failcode";
		}
		if(username==null) {
			httpSession.setAttribute("tmp_username", username);
			return "login";
		}
		String realpwd=loginservice.getpwdbyname(username);
		if(realpwd!=null&&pwd.equals(realpwd))
		{
			httpSession.setAttribute("username", username);
			return "index";
		}else {
			httpSession.setAttribute("tmp_username", username);
			return "fail";
		}
	}
	
	@RequestMapping("/login")
	public String login(){
//	public String login(HttpSession httpSession){
//		httpSession.setAttribute("tmp_username", "");
		return "login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession httpSession){
		httpSession.removeAttribute("username");
		return "login";
	}
  }
