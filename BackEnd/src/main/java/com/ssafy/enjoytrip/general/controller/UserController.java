package com.ssafy.enjoytrip.general.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.service.JwtService;
import com.ssafy.enjoytrip.general.model.service.UserService;
import com.ssafy.enjoytrip.util.ParameterCheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("사용자 관련 컨트롤러  API V1")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
	
	@Value("${spring.mail.username}")
	private String userEmail;
	
	@Value("${spring.mail.password}")
	private String password;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "아이디 중복 검사", notes = "요청된 아이디가 사용가능한지 확인한다.", response = String.class)
	@GetMapping("/idCheck/{id}")
	public ResponseEntity<String> idCheck(@PathVariable("id") @ApiParam(value = "검사할 아이디", required = true) String id)
			throws Exception {
		logger.info("idCheck - 호출: {}", id);
		if (userService.idCheck(id) != 0) {
			return new ResponseEntity<String>("duplicated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@ApiOperation(value = "닉네임 중복 검사", notes = "요청된 닉네임이 사용가능한지 확인한다.", response = String.class)
	@GetMapping("/nickCheck/{nickname}")
	public ResponseEntity<String> nickCheck(@PathVariable("nickname") @ApiParam(value = "검사할 아이디", required = true) String nickname)
			throws Exception {
		logger.info("nickCheck - 호출: {}", nickname);
		if (userService.nickCheck(nickname) != 0) {
			return new ResponseEntity<String>("duplicated", HttpStatus.OK);
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}

	
	@ApiOperation(value = "회원가입", notes = "주어진 회원정보를 DB에 저장한다.", response = String.class)
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody @ApiParam(value = "회원정보", required = true) UserDto userDto)
			throws Exception {
		logger.info("join - 호출");
		logger.debug("user - {}", userDto);
		if (userService.joinUser(userDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "로그인", notes = "userId와 password를 확인하고, 로그인 결과 메세지와 Access Token을 반환", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "로그인 할 정보", required = true) UserDto userDto) throws Exception {
		logger.info("login userDto: {}", userDto);
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto authUserDto = userService.loginUser(userDto.getUserId(), userDto.getUserPw());
			logger.info("authUserDto: {}", authUserDto);
			if(authUserDto != null) {
				String accessToken = jwtService.createAccessToken("userId", authUserDto.getUserId());// key, data
				String refreshToken = jwtService.createRefreshToken("userId", authUserDto.getUserId());// key, data
				userService.saveRefreshToken(authUserDto.getUserId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				result.put("access-token", accessToken);
				result.put("refresh-token", refreshToken);
				result.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				result.put("message", FAIL);
//				status = HttpStatus.BAD_REQUEST;
				status = HttpStatus.METHOD_NOT_ALLOWED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			result.put("message", FAIL);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.debug("result : {} ", result.get("message"));
		return new ResponseEntity<Map<String,Object>>(result, status);
////				cookie 설정
//			if ("ok".equals("ok")) { // 아이디 저장을 체크 했다면.
//				Cookie cookie = new Cookie("userId", userDto.getUserId());
//				cookie.setPath(request.getContextPath());
//				cookie.setMaxAge(60 * 60 * 24 * 60);
//				response.addCookie(cookie);
//			} else { // 아이디 저장을 해제 했다면.
//				Cookie cookies[] = request.getCookies();
//				if (cookies != null) {
//					for (Cookie cookie : cookies) {
//						if ("userId".equals(cookie.getName())) {
//							cookie.setMaxAge(0);
//							response.addCookie(cookie);
//							break;
//						}
//					}
//				}
	}
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
		logger.debug("userId : {} ", userId);
		logger.debug("request: {} ", request);
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		System.out.println(request.getHeader("access-token"));
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰입니다");
			try {
//				로그인 사용자 정보.
				UserDto userDto = userService.getUserInfo(userId);
				logger.info("조회된 데이터: {}", userDto);
				result.put("userInfo", userDto);
				result.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				result.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰입니다");
			result.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	@ApiOperation(value = "비밀번호 확인", notes = "현재 사용자가 회원정보 수정 페이지에 들어가기 전의 확인절차.", response = UserDto.class)
	@GetMapping("/chk")
	private ResponseEntity<UserDto> passwordCheck(
			@RequestParam(value = "passwordChk") @ApiParam(value = "확인용 비밀번호", required = true) String password,
			HttpSession session) throws Exception {
		logger.info("passwordCheck - 호출");
		UserDto userDto = (UserDto) session.getAttribute("userInfo");
		UserDto result = userService.loginUser(userDto.getUserId(), password);
		if (result != null) {
			return new ResponseEntity<UserDto>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<UserDto>(result, HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "회원정보 수정", notes = "현재 사용자의 회원정보를 수정한다.", response = String.class)
	@PutMapping("/modify/info")
	private ResponseEntity<String> modify(
			@RequestBody @ApiParam(value = "수정된 회원정보", required = true) Map<String, Object> map) throws Exception {
		logger.info("modify - 호출");
		System.out.println(map);
		UserDto userDto = new UserDto();
		userDto.setUserId((String) map.get("userId"));
		userDto.setName((String) map.get("name"));
		userDto.setNickname((String) map.get("nickname"));
		userDto.setEmailId((String) map.get("emailId"));
		userDto.setEmailDomain((String) map.get("emailDomain"));
		userDto.setSido((int) map.get("sido"));
		System.out.println(userDto.toString());
		if (userService.updateUser(userDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "비밀번호 수정", notes = "현재 사용자의 회원정보를 수정한다.", response = String.class)
	@PostMapping("/modify/password")
	private ResponseEntity<String> updatePassword(
			@RequestBody @ApiParam(value = "수정된 회원정보", required = true) Map<String, Object> map) throws Exception {
		logger.info("passwordModify - 호출");
//		System.out.println(userId + " " + userPw + " " + changePw);
//		System.out.println(map);
		if(userService.changePassword(map) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
//		String userPw = userService.getUserPw(map.get("userId"));
//		userDto.setUserId(map.get("userId"));
		
//		if (userService.updateUser(userDto) != 0) {
//			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
//		}
	}
	
	@ApiOperation(value = "회원정보 삭제", notes = "현재 사용자를 탈퇴시킨다.", response = String.class)
	@DeleteMapping("/delete")
	public ResponseEntity<String> delete(@RequestParam @ApiParam(value = "삭제 할 정보", required = true) Map<String, String> map) throws Exception {
		UserDto userDto = new UserDto();
		userDto.setUserId(map.get("userId"));
		userDto.setUserPw(map.get("userPw"));
		System.out.println(userDto);
		if (userService.deleteUser(userDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "로그아웃", notes = "현재 사용자를 로그아웃 시킨다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable("userId") String userId) {
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			userService.deleteRefreshToken(userId);
			result.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			result.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> result = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, userDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(userService.getRefreshToken(userDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userId", userDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("액세스토큰 재발급 됨");
				result.put("access-token", accessToken);
				result.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰 만료됨");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(result, status);
	}
	
	@ApiOperation(value = "이메일 인증 코드", notes = "이메일 인증 코드 제공.")
	@GetMapping("/getcode")
	public ResponseEntity<String> getCode(@RequestParam @ApiParam(value = "확인용 비밀번호", required = true) String email)
			throws Exception {
		logger.info("code - 호출");
		System.out.println(email);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
    	mailSender.setUsername(userEmail);
    	mailSender.setPassword(password);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        mailSender.setJavaMailProperties(props);
        System.out.println("test");
        System.out.println(mailSender);
		MimeMessage message = mailSender.createMimeMessage();
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(".com");
			helper.setTo(email);
			helper.setSubject("이메일 인증");
			// true 전달 > html 형식으로 전송 , 작성하지 않으면 단순 텍스트로 전달.
			helper.setText("홈페이지를 방문해주셔서 감사합니다." + 	//html 형식으로 작성 ! 
	                "<br><br>" + 
				    "인증 번호는 " + checkNum + "입니다." + 
				    "<br>" + 
				    "해당 인증번호를 인증번호 확인란에 기입하여 주세요.",true);
			mailSender.send(message);
			return new ResponseEntity<String> (Integer.toString(checkNum), HttpStatus.OK);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String> (FAIL, HttpStatus.NO_CONTENT);
	}
}
