package com.company.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private Integer subjectId;
    private String text;
    private String correctAnswer;
    private List<String> wrongKeys;
}
