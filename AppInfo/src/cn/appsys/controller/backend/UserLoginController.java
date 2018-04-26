package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping(value="/manager")
public class UserLoginController {
	private Logger logger = Logger.getLogger(UserLoginController.class);
	
	@Resource
	private BackendUserService backendUserService;
	
	@RequestMapping(value="/login")
	public String login(){
		logger.debug("LoginController welcome AppInfoSystem backend==================");
		return "backendlogin";
	}
	
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String userCode,@RequestParam String userPassword,HttpServletRequest request,HttpSession session){
		logger.debug("doLogin====================================");
		//璋冪敤service鏂规硶锛岃繘琛岀敤鎴峰尮閰�
		BackendUser user = null;
		try {
			user = backendUserService.login(userCode,userPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != user){//鐧诲綍鎴愬姛
			//鏀惧叆session
			session.setAttribute(Constants.USER_SESSION, user);
			//椤甸潰璺宠浆锛坢ain.jsp锛�
			return "redirect:/manager/backend/main";
		}else{
			//椤甸潰璺宠浆锛坙ogin.jsp锛夊甫鍑烘彁绀轰俊鎭�-杞彂
			request.setAttribute("error", "鐢ㄦ埛鍚嶆垨瀵嗙爜涓嶆纭�");
			return "backendlogin";
		}
	}
	
	@RequestMapping(value="/backend/main")
	public String main(HttpSession session){
		if(session.getAttribute(Constants.USER_SESSION) == null){
			return "redirect:/manager/login";
		}
		return "backend/main";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		//娓呴櫎session
		session.removeAttribute(Constants.USER_SESSION);
		return "backendlogin";
	}
}
