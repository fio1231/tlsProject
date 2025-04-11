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

    List<BoardDto> selectBoardList(BoardDto paramDto);

    int selectBoardListCnt(BoardDto paramDto);

    BoardDto selectBoard(BoardDto paramDto);

    int insertBoard(BoardDto paramDto);

    int updateBoard(BoardDto paramDto);

    int deleteBoard(BoardDto paramDto);


}