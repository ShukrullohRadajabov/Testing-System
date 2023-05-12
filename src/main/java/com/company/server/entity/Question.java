package com.company.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private Integer id;
    private Integer subjectId;
    private String text;
    private List<String> keys;
    private String correctAnswer;
}
