package com.ssafy.enjoytrip.general.controller;

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

import com.ssafy.enjoytrip.general.model.QnADto;
import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.service.QnAService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/qna")
@Api("QNA게시판 컨트롤러  API V1")
@CrossOrigin(origins = "http://localhost:8080")
public class QnAController {
	
	@Autowired
	private QnAService qnaService;
	
	private static final Logger logger = LoggerFactory.getLogger(QnAController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@ApiOperation(value = "QNA게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<QnADto>> list() throws Exception {
		logger.info("list - 호출");
		return new ResponseEntity<List<QnADto>>(qnaService.listArticle(), HttpStatus.OK);
	}
	@ApiOperation(value = "QNA게시판 검색", notes = "모든 게시글의 검색결과.", response = List.class)
	@GetMapping("/search")
	public ResponseEntity<List<QnADto>> search(@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, Object> map) throws Exception {
		logger.info("search - 호출");
		return new ResponseEntity<List<QnADto>>(qnaService.searchArticle(map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QNA게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody @ApiParam(value = "게시글 정보.", required = true) QnADto qnaDto) throws Exception {
		logger.info("write - 호출");
		
		if (qnaService.writeArticle(qnaDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "QNA게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = QnADto.class)
	@GetMapping("/view/{id}")
	public ResponseEntity<QnADto> view(@PathVariable("id") @ApiParam(value = "얻어올 글의 글번호.", required = true) int id) throws Exception {
		logger.info("view - 호출");
		return new ResponseEntity<QnADto>(qnaService.getArticle(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "QNA게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify/{id}")
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) QnADto qnaDto) throws Exception {
		logger.info("modify - 호출 {}", qnaDto);
		
		if (qnaService.modifyArticle(qnaDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "QNA게시판 답변 작성", notes = "답변을 작성할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/writeanswer/{id}")
	public ResponseEntity<String> writeAnswer(@PathVariable("id") @ApiParam(value = "삭제할 글의 글번호.", required = true) int id, @RequestBody @ApiParam(value = "답변할 글정보.", required = true) QnADto qnaDto) throws Exception {
		logger.info("writeAnswer - 호출 {}", qnaDto);
		
		qnaDto.setId(id);
		if (qnaService.writeAnswer(qnaDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "QNA게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") @ApiParam(value = "삭제할 글의 글번호.", required = true) int id, @ApiParam(value = "삭제 신청자의 정보", required = true) UserDto userDto) throws Exception {
		logger.info("delete - 호출");
		
		if (qnaService.deleteArticle(id, userDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
