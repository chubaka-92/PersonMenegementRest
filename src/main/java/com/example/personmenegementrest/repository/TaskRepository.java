package com.example.personmenegementrest.repository;

import com.example.personmenegementrest.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository  extends CrudRepository<TaskEntity, Long> {
}
