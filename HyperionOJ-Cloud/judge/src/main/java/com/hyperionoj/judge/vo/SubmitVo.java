package com.hyperionoj.judge.vo;

import lombok.Data;

/**
 * @author Hyperion
 * @date 2021/12/7
 */
@Data
public class SubmitVo {

    private Long problemId;

    private String codeLang;

    private String codeBody;

}