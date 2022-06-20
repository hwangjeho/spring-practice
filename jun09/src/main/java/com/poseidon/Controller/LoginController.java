package com.poseidon.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poseidon.DTO.LoginDTO;
import com.poseidon.Service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null) {
			session.removeAttribute("id");
		}
		return "redirect:/index";
	}
	// login화면 불러오기
	@GetMapping(value = "/login")
	public String login() {
		return "login";	
	}
	
	//로그인 로직 처리하기
	@PostMapping(value = "/login")
	public String login(HttpServletRequest request) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		LoginDTO dto = new LoginDTO();
		dto.setU_id(id);
		dto.setU_pw(pw);
		dto = loginService.login(dto);
		if(dto != null) {
			System.out.println(dto.getU_no());
			System.out.println(dto.getU_date());
			
			//정상로그인
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			System.out.println("생성된 세션 확인 : " + session.getAttribute("id"));
			return "redirect:/index";
		}else {
			//비정상로그인
			return "redirect:/login?error=1254";
		}
		
	}
	
	@PostMapping(value = "/checkID")
	public @ResponseBody String checkID(HttpServletRequest request) {
		String result = "1";
		System.out.println("ajax 통신 중 : " + request.getParameter("id"));
		
		//해야할 일 : 데이터베이스에게 물어보기 count(*)
		int count = loginService.checkID(request.getParameter("id"));
		
		result = String.valueOf(count);
		return result;
	}
	
	@GetMapping(value = "/join")
	public String join() {
		
		return "join";
	}
	
	@PostMapping(value = "/join")
	public String join(HttpServletRequest request) {
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("pw1"));
		System.out.println(request.getParameter("pw2"));
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("email"));
		
		LoginDTO dto = new LoginDTO();
		dto.setU_email(request.getParameter("email"));
		dto.setU_name(request.getParameter("name"));
		dto.setU_pw(request.getParameter("pw1"));
		dto.setU_id(request.getParameter("id"));
		
		int result = loginService.join(dto);
		
		System.out.println("가입결과 : " + result);
		if(result == 1) {
			return "redirect:/login";
		} else {
			System.out.println("로그인실패!");
			return "redirect:/join";
		}
	}
}
