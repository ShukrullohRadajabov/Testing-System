package com.company.server.service;

import com.company.server.database.Database;
import com.company.server.dto.Result;
import com.company.server.entity.Subject;
import com.company.server.exceptions.ValidationException;
import com.company.server.files.WorkWithFiles;

public class SubjectService {
    public static Result addSubject(String name) {
        try {
            checkParams(name);

            Subject subject = SubjectService.getSubjectByName(name);
            if(subject != null){
                return new Result("This subject exists", false);
            }

            subject = new Subject(Database.SUBJECTS.size()+1, name.toUpperCase());
            Database.SUBJECTS.add(subject);

            WorkWithFiles.writeToJsonFile(Database.SUBJECTS, Database.SUBJECTS_FILE);

        } catch (ValidationException e) {
            return new Result(e.getMessage(), false);
        }
        return new Result(name+" successfullly added.", true);
    }

    public static Subject getSubjectByName(String name) {
        return Database.SUBJECTS.stream()
                .filter(subject -> subject.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    private static void checkParams(String name) throws ValidationException {
        if(name == null || name.isBlank()){
            throw new ValidationException("Subject name is required");
        }
    }

    public static Subject getSubjectById(Integer subjectId) {
        return Database.SUBJECTS.stream()
                .filter(subject -> subject.getId().equals(subjectId))
                .findFirst().orElse(null);
    }
}
