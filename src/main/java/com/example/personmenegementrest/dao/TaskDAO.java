package com.example.personmenegementrest.dao;

import com.example.personmenegementrest.entity.TaskEntity;
import com.example.personmenegementrest.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TaskDAO {
    private final TaskRepository taskRepository;

    public TaskEntity findTaskById(Long id) {
        log.info("Was calling findTaskById. Input id: " + id);
        return taskRepository.findById(id).orElse(null);
    }

    public List<TaskEntity> findTasks() {
        log.info("Was calling findTasks.");
        return (List<TaskEntity>) taskRepository.findAll();
    }

    @Transactional
    public TaskEntity addTask(TaskEntity taskEntity) {
        log.info("Was calling addTask. Input id: " + taskEntity.toString());
        return taskRepository.save(taskEntity);
    }

    @Transactional
    public TaskEntity updateTask(TaskEntity taskEntity) {
        log.info("Was calling updateTask. Input id: " + taskEntity.toString());
        return taskRepository.save(taskEntity);
    }

    @Transactional
    public void deleteTaskById(long id) {
        log.info("Was calling deleteTaskById. Input id: " + id);
        taskRepository.deleteById(id);
    }
}
