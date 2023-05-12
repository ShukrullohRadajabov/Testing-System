package com.company.client.ui;

import com.company.client.util.ScannerUtil;
import com.company.server.database.Database;
import com.company.server.dto.Result;
import com.company.server.entity.Subject;
import com.company.server.files.WorkWithFiles;
import com.company.server.service.SubjectService;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SubjectUI {
    public static void addSubject() {
        System.out.println("Subject name");
        String name = ScannerUtil.SCANNER_STR.nextLine();

        Result result = SubjectService.addSubject(name);
        if(result.isSuccess()){
            System.out.println(ScannerUtil.ANSI_GREEN+result.getMessage()+ScannerUtil.ANSI_RESET);
        }else{
            System.out.println(ScannerUtil.ANSI_RED+result.getMessage()+ScannerUtil.ANSI_RESET);
        }
    }

    public static void showSubjects() {
        if(Database.SUBJECTS.isEmpty()){
            System.out.println("No subjects");
        }else{
            File file = WorkWithFiles.getSubjectsWithWordFile();
            if(file != null){
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Database.SUBJECTS.forEach(System.out::println);
            }
        }
    }
}
