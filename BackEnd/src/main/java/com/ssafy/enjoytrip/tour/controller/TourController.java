package com.ssafy.enjoytrip.tour.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.ssafy.enjoytrip.tour.model.GugunDto;
import com.ssafy.enjoytrip.tour.model.InfoDto;
import com.ssafy.enjoytrip.tour.model.InfoParameterDto;
import com.ssafy.enjoytrip.tour.model.ReviewDto;
import com.ssafy.enjoytrip.tour.model.SidoDto;
import com.ssafy.enjoytrip.tour.model.TourPlanDto;
import com.ssafy.enjoytrip.tour.model.service.TourPlanService;
import com.ssafy.enjoytrip.tour.model.service.TourService;
import com.ssafy.enjoytrip.util.ParameterCheck;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/tour")
@Api("관광지 정보 검색관련 컨트롤러  API V1")
@CrossOrigin(origins = "http://localhost:8080")
public class TourController {

	private static final Logger logger = LoggerFactory.getLogger(TourController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final int spp = 30;

	@Autowired
	private TourService tourService;
	@Autowired
	private TourPlanService tourPlanService;

	@ApiOperation(value = "시/도 목록", notes = "모든 시/도의 이름과 그 코드값을 반환한다.", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<?> listSido() {
		logger.info("listSido - 호출");
		try {
			List<SidoDto> list = tourService.getSidoList();
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<SidoDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "구/군 목록", notes = "주어진 시/도의 모든 구/군의 이름과 그 코드값을 반환한다.", response = List.class)
	@GetMapping("/gugun/{sidoCode}")
	public ResponseEntity<?> listGugun(@PathVariable("sidoCode") @ApiParam(value = "구/군 정보를 찾을 시/도의 번호", required = true) int sidoCode) {
		logger.info("listGugun - 호출");
		try {
			List<GugunDto> list = tourService.getGugunList(sidoCode);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<GugunDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "관광지 목록", notes = "검색조건에 맞는 관광지의 정보들를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<?> listPlace(
			@RequestParam @ApiParam(value = "관광지 정보을 얻기위한 부가정보.", required = true) Map<String, String> map)
			throws Exception {
		logger.info("listPlace - 호출 : {}", map);
		// 전처리
		InfoParameterDto infoParameterDto = new InfoParameterDto();
		infoParameterDto.setSidoCode(ParameterCheck.notNumberToZero(map.get("sidoCode")));
		infoParameterDto.setGugunCode(ParameterCheck.notNumberToZero(map.get("gugunCode")));
		infoParameterDto.setPg(ParameterCheck.notNumberToOne(map.get("pg")));
		infoParameterDto.setSpp(ParameterCheck.notNumberToInteger(map.get("spp"), spp));
		infoParameterDto.setStart(ParameterCheck.notNumberToZero(map.get("start")));
		if (map.get("selected") != null) {
			String[] sTypes = map.get("selected").split(",");
			int[] selected = new int[sTypes.length];
			for (int i = 0; i < sTypes.length; i++) {
				selected[i] = Integer.parseInt(sTypes[i]);
			}
			infoParameterDto.setSelectedAttTypes(selected);
		}
		infoParameterDto.setWord(map.get("word"));
		List<InfoDto> infos = tourService.listPlace(infoParameterDto);
		logger.debug("infos length: {}", infos.size());
		if (infos != null && !infos.isEmpty()) {
			return new ResponseEntity<List<InfoDto>>(infos, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "관광지 정보 조회", notes = "해당 아이디의 관광지의 정보들를 반환한다.", response = List.class)
	@GetMapping("/place/{contentId}")
	public ResponseEntity<?> viewPlace(
			@PathVariable("contentId") @ApiParam(value = "관광지 정보을 얻기위한 부가정보.", required = true) int contentId)
			throws Exception {
		logger.info("viewPlace - 호출, contentID : {}", contentId);
		
		InfoDto info = tourService.viewPlace(contentId);
		if (info != null) {
			// info의 maleRating과 femaleRating 구분하기
			int[][] maleRating = new int[2][6];
			int[][] femaleRating = new int[2][6];
	        int year = LocalDate.now().getYear();
			for(ReviewDto r:info.getReviewList()) {
				if (r.getUserSex().equals("남성")) {
					if (r.getUserBirthYear() >= year - 19) {
						maleRating[0][0]++;
						maleRating[1][0] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 29) {
						maleRating[0][1]++;
						maleRating[1][1] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 39) {
						maleRating[0][2]++;
						maleRating[1][2] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 49) {
						maleRating[0][3]++;
						maleRating[1][3] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 59) {
						maleRating[0][4]++;
						maleRating[1][4] += r.getRating();
					} else {
						maleRating[0][5]++;
						maleRating[1][5] += r.getRating();
					}
				} else {
					if (r.getUserBirthYear() >= year - 19) {
						femaleRating[0][0]++;
						femaleRating[1][0] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 29) {
						femaleRating[0][1]++;
						femaleRating[1][1] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 39) {
						femaleRating[0][2]++;
						femaleRating[1][2] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 49) {
						femaleRating[0][3]++;
						femaleRating[1][3] += r.getRating();
					} else if (r.getUserBirthYear() >= year - 59) {
						femaleRating[0][4]++;
						femaleRating[1][4] += r.getRating();
					} else {
						femaleRating[0][5]++;
						femaleRating[1][5] += r.getRating();
					}
				}
			}
			info.setMaleRating(maleRating);
			info.setFemaleRating(femaleRating);
			return new ResponseEntity<InfoDto>(info, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "여행계획 등록", notes = "여행 계획을 등록한다.", response = String.class)
	@PostMapping
	public ResponseEntity<?> writePlan(
			@RequestBody @ApiParam(value = "관광지 정보을 얻기위한 부가정보.", required = true) Map<String, Object> map)
			throws Exception {
		logger.info("writePlan - 호출 : {}", map);
		
		// 0이 나오면 비정상, 음수값이 나와야 리뷰와 여행계획 모두 제대로 등록된 것임
		if (tourPlanService.writeTourPlan(map) < 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "여행계획리뷰 등록", notes = "등록된 여행 계획에 후기를 등록한다.", response = String.class)
	@PostMapping("/plan/review/{id}")
	public ResponseEntity<?> writePlanReview(
			@PathVariable("id") int id,
			@RequestBody @ApiParam(value = "관광지 정보을 얻기위한 부가정보.", required = true) Map<String, Object> map)
			throws Exception {
		map.put("id", id);
		logger.info("writePlan - 호출 : {}", map);
		
		if (tourPlanService.writeTourPlanReview(map) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "여행계획 목록", notes = "검색조건에 맞는 여행 계획들를 반환한다.", response = List.class)
	@GetMapping("/plan")
	public ResponseEntity<?> listPlan(
			@RequestParam @ApiParam(value = "관광지 정보을 얻기위한 부가정보.", required = true) Map<String, String> map)
			throws Exception {
		logger.info("listPlan - 호출 : {}", map);
		// 전처리
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("pg", ParameterCheck.notNumberToOne(map.get("pg")));
		parameterMap.put("start", ParameterCheck.notNumberToOne(map.get("start")));
		parameterMap.put("spp", ParameterCheck.notNumberToInteger(map.get("spp"), spp));
		parameterMap.put("word", map.get("word"));
		List<TourPlanDto> plans = tourPlanService.listTourPlan(parameterMap);
		logger.debug("infos length: {}", plans.size());
		if (plans != null && !plans.isEmpty()) {
			return new ResponseEntity<List<TourPlanDto>>(plans, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "여행계획 상세조회", notes = "해당 아이디의 여행 계획을 반환한다.", response = List.class)
	@GetMapping("/plan/{id}")
	public ResponseEntity<?> viewPlan(
			@PathVariable("id") @ApiParam(value = "관광지 정보을 얻기위한 부가정보.", required = true) int id)
			throws Exception {
		logger.info("viewPlan - 호출 : {}", id);
		TourPlanDto plan = tourPlanService.getTourPlan(id);
		tourPlanService.updateHit(id);
		if (plan != null) {
			return new ResponseEntity<TourPlanDto>(plan, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "여행계획 수정", notes = "여행 계획을 수정한다.", response = String.class)
	@PutMapping("/plan/{id}")
	public ResponseEntity<?> updatePlan(
			@RequestBody @ApiParam(value = "수정된 정보", required = true) Map<String, Object> map)
			throws Exception {
		logger.info("updatePlan - 호출 : {}", map);
		
		if (tourPlanService.updateTourPlan(map) == -1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "여행계획 삭제", notes = "여행 계획을 삭제한다.", response = String.class)
	@DeleteMapping("/plan/{id}/{userId}")
	public ResponseEntity<?> deletePlan(
			@PathVariable("id") @ApiParam(value = "삭제할 글의 아이디", required = true) int id,
			@PathVariable("userId") @ApiParam(value = "삭제 요청자의 유저 아이디", required = true) String userId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("userId", userId);
		logger.info("deletePlan - 호출 : {}", map);
		
		if (tourPlanService.deleteTourPlan(map) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
	}

	////////////////////////////////////////// 이하 리뷰관련 //////////////////////////////////////////////////////////
	
	@ApiOperation(value = "최근 리뷰 조회", notes = "최근 리뷰 10개를 가져온다", response = List.class)
	@GetMapping("/review/recent")
	public ResponseEntity<?> listReviewRecent() throws Exception {
		logger.info("listReviewRecent - 호출 ");
		
		List<ReviewDto> reviews = tourService.listReviewRecent();
		if (reviews != null && !reviews.isEmpty()) {
			return new ResponseEntity<List<ReviewDto>>(reviews, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "리뷰등록", notes = "사용자가 보낸 리뷰를 등록한다", response = List.class)
	@PostMapping("/review/write")
	public ResponseEntity<?> insertReview(@RequestBody @ApiParam(value = "리뷰 내용", required = true) Map<String, Object> map) throws Exception {
		logger.info("insertReview - 호출 : {}", map);
		
		if (tourService.insertReview(map) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "리뷰수정", notes = "사용자가 보낸 리뷰를 수정한다", response = String.class)
	@PutMapping("/review/update")
	public ResponseEntity<?> updateReview(@RequestBody @ApiParam(value = "리뷰 내용", required = true) Map<String, Object> map) throws Exception {
		logger.info("updateReview - 호출 : {}", map);
		
		if (tourService.updateReview(map) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "리뷰삭제", notes = "사용자가 보낸 리뷰를 삭제한다", response = String.class)
	@DeleteMapping("/review/{id}")
	public ResponseEntity<?> deleteReview(@PathVariable("id") @ApiParam(value = "리뷰 내용", required = true) int id) throws Exception {
		logger.info("deleteReview - 호출 : {}", id);
		
		if (tourService.deleteReview(id) == 1) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
