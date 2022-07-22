package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Task;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {

    ResponseEntity getTaskById(Long id);

    ResponseEntity getTasks();

    ResponseEntity deleteTask(Long id);

    ResponseEntity addNewTask(Task task,Long personId);

    ResponseEntity updateTask(Task task);

    ResponseEntity addNewTasks(List<Task> tasks, Long personId);
}
