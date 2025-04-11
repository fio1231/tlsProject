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

    /** {@link BoardMapper} */
    @NonNull
    private BoardMapper boardMapper;

    /**
     * 게시판 리스트 조회
     * @param paramDto
     * @return
     */
    public List<BoardDto> selectBoardList(BoardDto paramDto) {
        return boardMapper.selectBoardList(paramDto);
    }

    /**
     * 개시판 건수 조회
     * @param paramDto
     * @return
     */
    public int selectBoardListCnt(BoardDto paramDto) {
        return boardMapper.selectBoardListCnt(paramDto);
    }

    /**
     * 게시판 데이타 조회
     * @param paramDto
     * @return
     */
    public BoardDto selectBoard(BoardDto paramDto) {
        return boardMapper.selectBoard(paramDto);
    }

    /**
     * 게사판 데이터 등록
     * @param paramDto
     */
    public void insertBoard(BoardDto paramDto) {
        boardMapper.insertBoard(paramDto);
    }

    /**
     * 게시판 데이터 수정
     * @param paramDto
     */
    public void updateBoard(BoardDto paramDto) {
        boardMapper.updateBoard(paramDto);
    }

    /**
     * 게시판 데이터 삭제
     * @param paramDto
     */
    public void deleteBoard(BoardDto paramDto) {
        boardMapper.deleteBoard(paramDto);
    }
}
