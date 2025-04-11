package com.tls.server.board.dto;

import com.tls.server.common.dto.AbstractReqDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * ==========================================================
 * PackageName : com.tls.server.board.dto
 * FileName    : BoardDto
 * Author      : 이진우
 * Date        : 2025-03-25
 * Description :
 * ==========================================================
 * Date          |    Author    |    Note
 * ----------------------------------------------------------
 * 2025-03-25    |    이진우    |    최초생성
 */
@Getter
@Setter
@ToString
public class BoardDto extends AbstractReqDto {
    /** 글 번호 */
    private int no;

    /** 글 제목 */
    private String subject;

    /** 글 내용 */
    private String content;

    /** 삭제 여부 */
    private String delYn;

    /** 등록 자 */
    private String crtId;

    /** 등록 일시 */
    private String crtDtm;

    /** 게시글 리스트 */
    private List<BoardDto> boardList;

    /** 게시글 건 수 */
    private int totCnt;

    /** 페이지 번호 */
    private int pageNo;

    /** 한 페이지 글 갯수 */
    private int pageSize;
}
