package com.ssafy.enjoytrip.general.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.ssafy.enjoytrip.general.model.BoardDto;
import com.ssafy.enjoytrip.general.model.BoardParameterDto;
import com.ssafy.enjoytrip.general.model.NoticeDto;
import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/notice")
@Api("공지사항 게시판 컨트롤러  API V1")
@CrossOrigin(origins = "http://localhost:8080")
public class NoticeController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	// 임시로 사용할 디폴트 userId
//	private static final String defaultUserId = "admin";
	
	@Autowired
	private NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@ApiOperation(value = "공지사항 글목록", notes = "모든 공지사항의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<NoticeDto>> list(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception {
		logger.info("list - 호출");
		return new ResponseEntity<List<NoticeDto>>(noticeService.listNotice(boardParameterDto), HttpStatus.OK);
	}
	@ApiOperation(value = "공지사항 검색", notes = "공지사항 검색.", response = List.class)
	@GetMapping("/search")
	public ResponseEntity<List<NoticeDto>>search(@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, Object> map) throws Exception {
		logger.info("search - 호출");
		return new ResponseEntity<List<NoticeDto>>(noticeService.searchNotice(map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 글작성", notes = "새로운 공지사항 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody @ApiParam(value = "게시글 정보.", required = true) NoticeDto noticeDto) throws Exception {
		logger.info("write - 호출");
//		System.out.println(noticeDto);
//		noticeDto.setUserId(defaultUserId);
		if (noticeService.writeNotice(noticeDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "공지사항 글보기", notes = "글번호에 해당하는 공지사항의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/view/{id}")
	public ResponseEntity<NoticeDto> view(@PathVariable("id") @ApiParam(value = "얻어올 공지사항의 번호.", required = true) int id) throws Exception {
		logger.info("view - 호출");
		return new ResponseEntity<NoticeDto>(noticeService.getNotice(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 글수정", notes = "수정할 공지사항 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify/{id}")
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "수정할 공지사항정보.", required = true) NoticeDto noticeDto) throws Exception {
		logger.info("modify - 호출 {}", noticeDto);
		
		if (noticeService.modifyNotice(noticeDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "공지사항 글삭제", notes = "공지사항 글번호에 해당하는 공지사항의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") @ApiParam(value = "삭제할 공지사항의 글번호.", required = true) int id) throws Exception {
		logger.info("delete - 호출");
		// 유저의 userId를 가져온다.(현재는 디폴트 사용)
//		String userId = defaultUserId;
		if (noticeService.deleteNotice(id) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
