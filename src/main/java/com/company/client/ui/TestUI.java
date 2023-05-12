package com.company.client.ui;

import com.company.client.util.ScannerUtil;
import com.company.server.database.Database;
import com.company.server.entity.Question;
import com.company.server.entity.Subject;
import com.company.server.entity.TestHistory;
import com.company.server.entity.User;
import com.company.server.files.WorkWithFiles;
import com.company.server.service.QuestionService;
import com.company.server.service.SubjectService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUI {
    public static void solveTest(User user) {
        if (Database.SUBJECTS.isEmpty()) {
            System.out.println("No subjects");
            return;
        }

        Database.SUBJECTS.forEach(subject -> {
            List<Question> questionList = QuestionService.getQuestionsBySubjectId(subject.getId());
            System.out.println(subject + " -> " + questionList.size());
        });

        System.out.println("Enter subject id:");
        Integer subjectId = ScannerUtil.SCANNER_NUM.nextInt();

        Subject subject = SubjectService.getSubjectById(subjectId);
        if (subject == null) {
            System.out.println(ScannerUtil.ANSI_RED + "\nSubject not found" + ScannerUtil.ANSI_RESET);
            return;
        }

        List<Question> questionList = new ArrayList<>(QuestionService.getQuestionsBySubjectId(subject.getId()));

        if (questionList.isEmpty()) {
            System.out.println(ScannerUtil.ANSI_RED + "\nNo question by this subject" + ScannerUtil.ANSI_RESET);
            return;
        }

        Collections.shuffle(questionList);

        Integer score = 0;

        int number = 0;
        for (Question question : questionList) {
            number++;
            System.out.printf("%n%d) %s%n", number, question.getText());

            Collections.shuffle(question.getKeys());
            for (int i = 0; i < question.getKeys().size(); i++) {
                System.out.println((char) (i + 65) + ") " + question.getKeys().get(i));
            }

            System.out.println("Choose correct variant");
            String line = ScannerUtil.SCANNER_STR.nextLine().toUpperCase();

            if (line.length() == 1 && "ABCD".contains(line)) {
                String answer = question.getKeys().get(line.charAt(0) - 'A');
                if (answer.equals(question.getCorrectAnswer())) {
                    score++;
                    System.out.println(ScannerUtil.ANSI_GREEN + "✔" + ScannerUtil.ANSI_RESET);
                } else {
                    System.out.println(ScannerUtil.ANSI_RED + "❌" + ScannerUtil.ANSI_RESET);
                }
            } else {
                System.out.println(ScannerUtil.ANSI_RED + "❌" + ScannerUtil.ANSI_RESET);
            }
        }

        System.out.printf("%s%n%s -> count = %d, score = %d %s%n%n",
                ScannerUtil.ANSI_YELLOW, subject.getName(), questionList.size(), score,
                ScannerUtil.ANSI_RESET);

        TestHistory testHistory = new TestHistory(Database.TEST_HISTORIES.size() + 1, user.getId(), subjectId,
                questionList.size(), score);
        Database.TEST_HISTORIES.add(testHistory);

        WorkWithFiles.writeToJsonFile(Database.TEST_HISTORIES, Database.TEST_HISTORIES_FILE);
    }

    public static void showTestHistory(User user) {

    }
}
