package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Task;
import com.example.personmenegementrest.entity.PersonEntity;

import java.util.List;

public interface TaskChecker{

    void checkTaskRequiredFields(Task task);

    boolean checkAvailableCountTasksToPerson(List<Task> tasks, PersonEntity personEntity);
}
