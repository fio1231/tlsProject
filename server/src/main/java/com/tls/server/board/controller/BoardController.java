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

    @NonNull
    private BoardService boardService;

    @PostMapping("/get-board")
    public ResponseEntity<BoardDto> getBoardList(@RequestBody BoardDto boardDto) {
        BoardDto resDto = new BoardDto();
        resDto.setBoardList(boardService.selectBoardList(boardDto));
        resDto.setTotCnt(boardService.selectBoardListCnt(boardDto));
        return ResponseEntity.ok(resDto);
    }

    @PostMapping("/get-board-dtl")
    public ResponseEntity<BoardDto> getBoardDtl(@RequestBody BoardDto boardDto) {
        return ResponseEntity.ok(boardService.selectBoard(boardDto));
    }

    @PostMapping("/save-board")
    public ResponseEntity<?> saveBoard(@RequestBody BoardDto boardDto) {
        boardService.insertBoard(boardDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update-board")
    public ResponseEntity<?> updateBoard(@RequestBody BoardDto boardDto) {
        boardService.updateBoard(boardDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-board")
    public ResponseEntity<?> deleteBoard(@RequestBody BoardDto boardDto) {
        boardService.deleteBoard(boardDto);
        return ResponseEntity.ok().build();
    }
}
