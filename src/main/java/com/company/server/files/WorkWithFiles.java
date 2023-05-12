package com.company.server.files;
//||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

import com.company.server.database.Database;
import com.company.server.entity.Question;
import com.company.server.entity.Subject;
import com.company.server.entity.TestHistory;
import com.company.server.entity.User;
import com.company.server.service.QuestionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.Collections;
import java.util.List;

public class WorkWithFiles {

    static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .serializeNulls().create();

    public static void writeToJsonFile(List list, File file) {

        file.getParentFile().mkdirs();

        try (PrintWriter printWriter = new PrintWriter(file)) {

            printWriter.write(GSON.toJson(list));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readUsers(){
        try (BufferedReader reader = new BufferedReader(
                new FileReader(Database.USERS_FILE)
        )) {

            User[] arr = GSON.fromJson(reader, User[].class);

            Collections.addAll(Database.USERS, arr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readSubjects(){
        try (BufferedReader reader = new BufferedReader(
                new FileReader(Database.SUBJECTS_FILE)
        )) {

            Subject[] arr = GSON.fromJson(reader, Subject[].class);

            Collections.addAll(Database.SUBJECTS, arr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readQuestions(){
        try (BufferedReader reader = new BufferedReader(
                new FileReader(Database.QUESTIONS_FILE)
        )) {

            Question[] arr = GSON.fromJson(reader, Question[].class);

            Collections.addAll(Database.QUESTIONS, arr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTestHistories(){
        try (BufferedReader reader = new BufferedReader(
                new FileReader(Database.TEST_HISTORIES_FILE)
        )) {

            TestHistory[] arr = GSON.fromJson(reader, TestHistory[].class);

            Collections.addAll(Database.TEST_HISTORIES, arr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getSubjectsWithWordFile() {


        try (FileOutputStream out = new FileOutputStream(Database.SUBJECTS_WORD_FILE);
             XWPFDocument document = new XWPFDocument();
             ) {

            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);

            paragraph.createRun().setText("Subject list");

            XWPFTable table = document.createTable();
            table.setWidth("100%");

            XWPFTableRow row = table.getRow(0);

            XWPFTableCell cell = row.getCell(0);
            cell.setWidth("30%");
            cell.setText("Id");

            cell = row.createCell();
            cell.setWidth("70%");
            cell.setText("Name");

            for (Subject subject : Database.SUBJECTS) {
                row = table.createRow();

                row.getCell(0).setText(String.valueOf(subject.getId()));
                row.getCell(1).setText(subject.getName());

            }

            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Database.SUBJECTS_WORD_FILE;
    }

    public static File getQuestionsWithExcelFile() {

        try (FileOutputStream out = new FileOutputStream(Database.QUESTIONS_EXCEL_FILE);
             XSSFWorkbook workbook = new XSSFWorkbook();) {

            for (Subject subject : Database.SUBJECTS) {

                XSSFSheet sheet = workbook.createSheet(subject.getName());

                List<Question> questionList = QuestionService.getQuestionsBySubjectId(subject.getId());

                XSSFRow row = sheet.createRow(0);

                row.createCell(0).setCellValue("Id");
                row.createCell(1).setCellValue("Text");
                row.createCell(2).setCellValue("Correct answer");
                row.createCell(3).setCellValue("Keys");

                int rowIndex = 1;
                for (Question question : questionList) {
                    row = sheet.createRow(++rowIndex);
                    row.createCell(0).setCellValue(question.getId());
                    row.createCell(1).setCellValue(question.getText());
                    row.createCell(2).setCellValue(question.getCorrectAnswer());
                    row.createCell(3).setCellValue(question.getKeys().toString());}
                for (int i = 0; i < 4; i++) {sheet.autoSizeColumn(i);}}workbook.write(out);} catch (IOException e) {e.printStackTrace();}
        return Database.QUESTIONS_EXCEL_FILE;
    }
}
