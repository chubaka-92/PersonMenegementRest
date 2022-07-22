package com.example.personmenegementrest.api;

import com.example.personmenegementrest.dto.Task;
import com.example.personmenegementrest.entity.TaskEntity;

public interface TaskMapper {
    Task taskEntityToTask(TaskEntity taskEntity);
    TaskEntity taskToTaskEntity(Task task);
}
