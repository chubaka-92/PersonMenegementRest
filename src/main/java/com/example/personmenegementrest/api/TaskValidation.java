package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Task;
import com.example.personmenegementrest.entity.PersonEntity;

import java.util.List;

public interface TaskValidation {

    void addTaskValidator(Task task);

    void updateTaskValidator(Task task);

    boolean availableCountTasksToPersonValidator(List<Task> tasks, PersonEntity personEntity);
}
