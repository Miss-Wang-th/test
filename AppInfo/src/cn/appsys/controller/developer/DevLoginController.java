package cn.appsys.controller.developer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping(value="/dev")
public class DevLoginController {
	private Logger logger = Logger.getLogger(DevLoginController.class);
	
	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value="/login")
	public String login(){
		logger.debug("LoginController welcome AppInfoSystem develpor==================");
		return "devlogin";
	}
	
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(@RequestParam String devCode,@RequestParam String devPassword,HttpServletRequest request,HttpSession session){
		logger.debug("doLogin====================================");
		//璋冪敤service鏂规硶锛岃繘琛岀敤鎴峰尮閰�
		DevUser user = null;
		try {
			user = devUserService.login(devCode,devPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(null != user){//鐧诲綍鎴愬姛
			//鏀惧叆session
			session.setAttribute(Constants.DEV_USER_SESSION, user);
			//椤甸潰璺宠浆锛坢ain.jsp锛�
			return "redirect:/dev/flatform/main";
		}else{
			//椤甸潰璺宠浆锛坙ogin.jsp锛夊甫鍑烘彁绀轰俊鎭�-杞彂
			request.setAttribute("error", "账号密码不正确");
			return "devlogin";
		}
	}
	
	@RequestMapping(value="/flatform/main")
	public String main(HttpSession session){
		if(session.getAttribute(Constants.DEV_USER_SESSION) == null){
			return "redirect:/dev/login";
		}
		return "developer/main";
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpSession session){
		//娓呴櫎session
		session.removeAttribute(Constants.DEV_USER_SESSION);
		return "devlogin";
	}
}

