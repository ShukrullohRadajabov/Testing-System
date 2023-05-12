package com.company.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestHistory {
    private Integer id;
    private Integer userId;
    private Integer subjectId;
    private Integer quantity;
    private Integer score;
    private String createdAt;

    public TestHistory(Integer id, Integer userId,
                       Integer subjectId,
                       Integer quantity, Integer score) {
        this.id = id;
        this.userId = userId;
        this.subjectId = subjectId;
        this.quantity = quantity;
        this.score = score;
        createdAt = String.valueOf(LocalDateTime.now());
    }
}
