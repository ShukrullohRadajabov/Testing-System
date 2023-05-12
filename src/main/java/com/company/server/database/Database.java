package com.company.server.database;

import com.company.server.entity.Question;
import com.company.server.entity.Subject;
import com.company.server.entity.TestHistory;
import com.company.server.entity.User;
import com.company.server.enums.Role;
import com.company.server.files.WorkWithFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface Database {
    List<User> USERS = new ArrayList<>();
    List<Subject> SUBJECTS = new ArrayList<>();
    List<Question> QUESTIONS = new ArrayList<>();
    List<TestHistory> TEST_HISTORIES = new ArrayList<>();

    String DB_BASE_FOLDER = "src/main/resources/db";

    File USERS_FILE = new File(DB_BASE_FOLDER, "users.json");
    File SUBJECTS_FILE = new File(DB_BASE_FOLDER, "subjects.json");
    File QUESTIONS_FILE = new File(DB_BASE_FOLDER, "questions.json");
    File TEST_HISTORIES_FILE = new File(DB_BASE_FOLDER, "test_histories.json");

    String DOCUMENTS_BASE_FOLDER = "src/main/resources/documents";

    File USERS_PDF_FILE = new File(DOCUMENTS_BASE_FOLDER, "users.pdf");
    File SUBJECTS_WORD_FILE = new File(DOCUMENTS_BASE_FOLDER, "subjects.docx");
    File QUESTIONS_EXCEL_FILE = new File(DOCUMENTS_BASE_FOLDER, "questions.xlsx");

    String HISTORY_BASE_FOLDER = "src/main/resources/histories";

    static void loadData() {
        WorkWithFiles.readUsers();
        WorkWithFiles.readSubjects();
        WorkWithFiles.readQuestions();
        WorkWithFiles.readTestHistories();

        if(Database.USERS.isEmpty()){
            User admin = new User(1, "Adminjon", "+998901234567",
                    "4567", Role.ADMIN);
            Database.USERS.add(admin);
            WorkWithFiles.writeToJsonFile(Database.USERS, Database.USERS_FILE);
        }
    }
}
