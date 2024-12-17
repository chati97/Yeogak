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
import com.ssafy.enjoytrip.general.model.CommentDto;
import com.ssafy.enjoytrip.general.model.NoticeDto;
import com.ssafy.enjoytrip.general.model.UserDto;
import com.ssafy.enjoytrip.general.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API V1")
@CrossOrigin(origins = "http://localhost:8080")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	// 임시로 사용할 디폴트 userId
//	private static final String defaultUserId = "testman";
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<List<BoardDto>> list(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception {
		logger.info("list - 호출");
		return new ResponseEntity<List<BoardDto>>(boardService.listArticle(boardParameterDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 검색", notes = "게시판 검색.", response = List.class)
	@GetMapping("/search")
	public ResponseEntity<List<BoardDto>>search(@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, Object> map) throws Exception {
		logger.info("search - 호출");
		return new ResponseEntity<List<BoardDto>>(boardService.searchArticle(map), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public ResponseEntity<String> write(@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("write - 호출");
//		boardDto.setUserId(defaultUserId);
		if (boardService.writeArticle(boardDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/view/{id}")
	public ResponseEntity<BoardDto> view(@PathVariable("id") @ApiParam(value = "얻어올 글의 글번호.", required = true) int id) throws Exception {
		logger.info("view - 호출");
		boardService.updateHit(id);
		return new ResponseEntity<BoardDto>(boardService.getArticle(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/modify/{id}")
	public ResponseEntity<String> modify(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("modify - 호출 {}", boardDto);
		
		if (boardService.modifyArticle(boardDto) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") @ApiParam(value = "삭제할 글의 글번호.", required = true) int id) throws Exception {
		logger.info("delete - 호출");
		// 유저의 userId를 가져온다.(현재는 디폴트 사용)
//		String userId = defaultUserId;
		
		if (boardService.deleteArticle(id) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/comment/write")
	public ResponseEntity<String> writeComment(@RequestBody @ApiParam(value = "게시글 정보.", required = true) CommentDto comment) throws Exception {
		logger.info("write - 호출");
//		boardDto.setUserId(defaultUserId);
		if (boardService.writeComment(comment) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/comment/delete/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable("id") @ApiParam(value = "삭제할 글의 글번호.", required = true) int id) throws Exception {
		logger.info("delete - 호출");
		// 유저의 userId를 가져온다.(현재는 디폴트 사용)
//		String userId = defaultUserId;
		
		if (boardService.deleteComment(id) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "댓글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/up")
	public ResponseEntity<String> upLikes(@RequestBody @ApiParam(value = "게시글 정보.", required = true) Map<String, Object> map) throws Exception {
		logger.info("up - 호출");
		System.out.println(boardService.getLikes(map));
		if(boardService.getLikes(map) == 0) {
			boardService.upLikes(map);
			boardService.updateLikes((int) map.get("id"));
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
}
