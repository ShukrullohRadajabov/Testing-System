package com.company.server.service;

import com.company.server.database.Database;
import com.company.server.dto.QuestionDTO;
import com.company.server.dto.Result;
import com.company.server.entity.Question;
import com.company.server.entity.Subject;
import com.company.server.entity.User;
import com.company.server.exceptions.ValidationException;
import com.company.server.files.WorkWithFiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuestionService {
    public static Result addQuestion(QuestionDTO questionDTO) {
        try {
            checkParams(questionDTO);

            for (Question question : Database.QUESTIONS) {
                if (question.getSubjectId().equals(questionDTO.getSubjectId()) &&
                        question.getText().equalsIgnoreCase(questionDTO.getText())) {
                    return new Result("This question exists in this subject", false);
                }
            }

            ArrayList<String> keys = new ArrayList<>(questionDTO.getWrongKeys());
            keys.add(questionDTO.getCorrectAnswer());

            Question question = new Question(Database.QUESTIONS.size() + 1, questionDTO.getSubjectId(),
                    questionDTO.getText(), keys, questionDTO.getCorrectAnswer());

            Database.QUESTIONS.add(question);

            WorkWithFiles.writeToJsonFile(Database.QUESTIONS, Database.QUESTIONS_FILE);

        } catch (ValidationException e) {
            return new Result(e.getMessage(), false);
        }
        return new Result("New question successfully added", true);
    }

    private static void checkParams(QuestionDTO questionDTO)
            throws ValidationException {

        if (questionDTO == null)
            throw new ValidationException("Data is required");

        Subject subject = SubjectService.getSubjectById(questionDTO.getSubjectId());
        if (subject == null) {
            throw new ValidationException("Subject not found");
        }

        String text = questionDTO.getText();
        if (text == null || text.isBlank()) {
            throw new ValidationException("Question text is required");
        }

        String correctAnswer = questionDTO.getCorrectAnswer();
        if (correctAnswer == null || correctAnswer.isBlank()) {
            throw new ValidationException("Correct answer is required");
        }

        List<String> wrongKeys = questionDTO.getWrongKeys();
        if (wrongKeys == null || wrongKeys.size() != 3) {
            throw new ValidationException("Wrong keys incorrect");
        }

        HashSet<String> set = new HashSet<>(wrongKeys);
        if (set.size() != 3) {
            throw new ValidationException("Wrong keys have got duplicates.");
        }

        if (wrongKeys.contains(correctAnswer)) {
            throw new ValidationException("Correct answer can't be in wrong keys");
        }
    }

    public static Map<Integer, List<Question>> getQuestionMap() {
        return Database.QUESTIONS.stream()
                .collect(Collectors.groupingBy(Question::getSubjectId));
    }

    public static List<Question> getQuestionsBySubjectId(Integer subjectId) {
        return Database.QUESTIONS.stream()
                .filter(question -> question.getSubjectId().equals(subjectId))
                .toList();
    }
}
