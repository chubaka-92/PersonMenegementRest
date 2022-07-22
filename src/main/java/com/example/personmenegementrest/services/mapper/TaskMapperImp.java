package com.example.personmenegementrest.services.mapper;

import com.example.personmenegementrest.api.TaskMapper;
import com.example.personmenegementrest.dto.Task;
import com.example.personmenegementrest.entity.TaskEntity;
import com.example.personmenegementrest.types.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TaskMapperImp implements TaskMapper {

    public Task taskEntityToTask(TaskEntity taskEntity) {
        log.info("Was calling taskEntityToTask. Input id: " + taskEntity.toString());
        return Task.builder()
                .id(taskEntity.getId().toString())
                .uid(taskEntity.getUid())
                .description(taskEntity.getDescription())
                .priority(taskEntity.getPriority().toString())
                .build();
    }

    public TaskEntity taskToTaskEntity(Task task) {
        log.info("Was calling taskToTaskEntity. Input id: " + task.toString());
        return TaskEntity.builder()
                .id(getId(task))
                .uid(getUid(task))
                .description(task.getDescription())
                .priority(Priority.getDefine(task.getPriority()))
                .build();
    }

    private Long getId(Task task) {
        log.debug("Was calling getId. Input id: " + task.toString());
        if (task.getId() == null) {
            return null;
        }
        return Long.valueOf(task.getId());
    }

    private String getUid(Task task) {
        log.debug("Was calling getUid. Input id: " + task.toString());
        if (task.getUid() == null) {
            return UUID.randomUUID().toString();
        }
        return task.getUid();
    }

}
