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
    private int no;
    private String subject;
    private String content;
    private String delYn;
    private String crtId;
    private String crtDtm;

    private List<BoardDto> boardList;
    private int totCnt;

    private int pageNo;
    private int pageSize;
}
