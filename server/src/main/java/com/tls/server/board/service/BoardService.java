package com.tls.server.board.service;

import com.tls.server.board.dto.BoardDto;
import com.tls.server.board.mapper.BoardMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ==========================================================
 * PackageName : com.tls.server.board.service
 * FileName    : BoardService
 * Author      : 이진우
 * Date        : 2025-03-25
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-25    |    이진우    |    최초생성
 */
@RequiredArgsConstructor
@Service
public class BoardService {

    @NonNull
    private BoardMapper boardMapper;

    public List<BoardDto> selectBoardList(BoardDto paramDto) {
        return boardMapper.selectBoardList(paramDto);
    }

    public int selectBoardListCnt(BoardDto paramDto) {
        return boardMapper.selectBoardListCnt(paramDto);
    }

    public BoardDto selectBoard(BoardDto paramDto) {
        return boardMapper.selectBoard(paramDto);
    }

    public void insertBoard(BoardDto paramDto) {
        boardMapper.insertBoard(paramDto);
    }

    public void updateBoard(BoardDto paramDto) {
        boardMapper.updateBoard(paramDto);
    }

    public void deleteBoard(BoardDto paramDto) {
        boardMapper.deleteBoard(paramDto);
    }
}
