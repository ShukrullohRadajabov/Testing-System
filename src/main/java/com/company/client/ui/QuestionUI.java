package com.company.client.ui;

import com.company.client.util.ScannerUtil;
import com.company.server.database.Database;
import com.company.server.dto.QuestionDTO;
import com.company.server.dto.Result;
import com.company.server.entity.Question;
import com.company.server.files.WorkWithFiles;
import com.company.server.service.QuestionService;
import com.company.server.service.SubjectService;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class QuestionUI {
    public static void addQuestion() {
        if(Database.SUBJECTS.isEmpty()){
            System.out.println("No subjects");
            return;
        }

        Database.SUBJECTS.forEach(System.out::println);

        System.out.println("Enter subject id:");
        Integer subjectId = ScannerUtil.SCANNER_NUM.nextInt();

        System.out.println("Enter question text:");
        String text = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter correct answer:");
        String correctAnswer = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter 1-wrong variant:");
        String wrong1 = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter 2-wrong variant:");
        String wrong2 = ScannerUtil.SCANNER_STR.nextLine();

        System.out.println("Enter 3-wrong variant:");
        String wrong3 = ScannerUtil.SCANNER_STR.nextLine();

        List<String> wrongKeys = new ArrayList<>();
        Collections.addAll(wrongKeys, wrong1, wrong2, wrong3);

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setSubjectId(subjectId);
        questionDTO.setText(text);
        questionDTO.setCorrectAnswer(correctAnswer);
        questionDTO.setWrongKeys(wrongKeys);

        Result result = QuestionService.addQuestion(questionDTO);
        if(result.isSuccess()){
            System.out.println(ScannerUtil.ANSI_GREEN+result.getMessage()+ScannerUtil.ANSI_RESET);
        }else{
            System.out.println(ScannerUtil.ANSI_RED+result.getMessage()+ScannerUtil.ANSI_RESET);
        }

    }

    public static void showQuestions() {
        if(Database.QUESTIONS.isEmpty()){
            System.out.println("No questions");
        }else{
            File file = WorkWithFiles.getQuestionsWithExcelFile();
            if(file != null){
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Map<Integer, List<Question>> questionMap = QuestionService.getQuestionMap();
                for (Integer subjectId : questionMap.keySet()) {
                    System.out.println();
                    System.out.println("subject = " + SubjectService.getSubjectById(subjectId));

                    questionMap.get(subjectId).forEach(System.out::println);
                }
            }
        }
    }
}
