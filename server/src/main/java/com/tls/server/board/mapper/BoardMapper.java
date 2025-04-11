package com.tls.server.board.mapper;

import com.tls.server.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ==========================================================
 * PackageName : com.tls.server.board.mapper
 * FileName    : BoardMapper
 * Author      : 이진우
 * Date        : 2025-03-25
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-25    |    이진우    |    최초생성
 */
@Mapper
public interface BoardMapper {

    /**
     * 게시글 리스트 조회
     * @param paramDto
     * @return
     */
    List<BoardDto> selectBoardList(BoardDto paramDto);

    /**
     * 게시글 리스트 갯수 조회
     * @param paramDto
     * @return
     */
    int selectBoardListCnt(BoardDto paramDto);

    /**
     * 게시글 데이터 조회
     * @param paramDto
     * @return
     */
    BoardDto selectBoard(BoardDto paramDto);

    /**
     * 게시글 등록
     * @param paramDto
     * @return
     */
    int insertBoard(BoardDto paramDto);

    /**
     * 게시글 수정
     * @param paramDto
     * @return
     */
    int updateBoard(BoardDto paramDto);

    /**
     * 게시글 삭제
     * @param paramDto
     * @return
     */
    int deleteBoard(BoardDto paramDto);


}