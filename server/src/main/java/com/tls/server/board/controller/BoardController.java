package com.tls.server.board.controller;

import com.tls.server.board.dto.BoardDto;
import com.tls.server.board.service.BoardService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ==========================================================
 * PackageName : com.tls.server.board.controller
 * FileName    : BoardController
 * Author      : 이진우
 * Date        : 2025-03-25
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-25    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@RestController
public class BoardController {

    /** {@link BoardService} */
    @NonNull
    private BoardService boardService;

    /**
     * 게시판 글 조회
     * @param boardDto
     * @return
     */
    @PostMapping("/get-board")
    public ResponseEntity<BoardDto> getBoardList(@RequestBody BoardDto boardDto) {
        BoardDto resDto = new BoardDto();
        resDto.setBoardList(boardService.selectBoardList(boardDto));
        resDto.setTotCnt(boardService.selectBoardListCnt(boardDto));
        return ResponseEntity.ok(resDto);
    }

    /**
     * 게시판 글 상세 조회
     * @param boardDto
     * @return
     */
    @PostMapping("/get-board-dtl")
    public ResponseEntity<BoardDto> getBoardDtl(@RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(boardService.selectBoard(boardDto));
    }

    /**
     * 게시판 등록
     * @param boardDto
     * @return
     */
    @PostMapping("/save-board")
    public ResponseEntity<?> saveBoard(@RequestBody BoardDto boardDto) {
        boardService.insertBoard(boardDto);
        return ResponseEntity.ok().build();
    }
    
    /**
     * 게시판 수정
     * @param boardDto
     * @return
     */
    @PostMapping("/update-board")
    public ResponseEntity<?> updateBoard(@RequestBody BoardDto boardDto) {
        boardService.updateBoard(boardDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 게시판 삭제
     * @param boardDto
     * @return
     */
    @PostMapping("/delete-board")
    public ResponseEntity<?> deleteBoard(@RequestBody BoardDto boardDto) {
        boardService.deleteBoard(boardDto);
        return ResponseEntity.ok().build();
    }
}
