package com.example.personmenegementrest.services;

import com.example.personmenegementrest.api.TaskMapper;
import com.example.personmenegementrest.api.TaskService;
import com.example.personmenegementrest.api.TaskValidation;
import com.example.personmenegementrest.dao.PersonDAO;
import com.example.personmenegementrest.dao.TaskDAO;
import com.example.personmenegementrest.dto.Task;
import com.example.personmenegementrest.entity.PersonEntity;
import com.example.personmenegementrest.entity.TaskEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImp implements TaskService {

    private final TaskDAO taskDAO;
    private static final String MESSAGE = "message";
    private static final String TOO_MANY_TASKS = "tooManyTasks";
    private static final String TASK_NOT_FOUND = "taskNotFound";
    private static final String TASKS_NOT_FOUND = "tasksNotFound";
    private static final String PERSON_NOT_FOUND = "personNotFound";
    private final ResourceBundle errorMsg = ResourceBundle.getBundle(MESSAGE);
    private final TaskMapper taskMapper;
    private final TaskValidation taskValidation;
    private final PersonDAO personDao;

    public ResponseEntity getTaskById(Long id) {
        log.info("Was calling getTaskById. Input id: " + id);
        TaskEntity taskEntity = taskDAO.findTaskById(id);

        if (taskEntity == null) {
            log.error(MessageFormat.format(errorMsg.getString(TASK_NOT_FOUND), id));
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(TASK_NOT_FOUND), id));
        }
        return ResponseEntity.ok(taskMapper.taskEntityToTask(taskEntity));
    }

    public ResponseEntity getTasks() {
        log.info("Was calling getTasks.");
        List<TaskEntity> persons = taskDAO.findTasks();

        if (persons == null) {
            log.error(errorMsg.getString(TASKS_NOT_FOUND));
            return ResponseEntity.badRequest().body(errorMsg.getString(TASKS_NOT_FOUND));
        }
        return ResponseEntity.ok(persons.stream()
                .map(taskMapper::taskEntityToTask)
                .collect(Collectors.toList()));
    }

    public ResponseEntity deleteTask(Long id) {
        log.info("Was calling deleteTask. Input id: " + id);
        if (taskDAO.findTaskById(id) == null) {
            log.error(MessageFormat.format(errorMsg.getString(TASK_NOT_FOUND), id));
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(TASK_NOT_FOUND), id));
        }
        taskDAO.deleteTaskById(id);
        return ResponseEntity.ok(id);
    }

    public ResponseEntity addNewTask(Task task, Long personId) {
        log.info("Was calling addNewTask. Input task: " + task.toString());
        PersonEntity personEntity = personDao.findPersonById(personId);

        if (personEntity == null) {
            log.error(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), personId));
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), personId));
        }

        taskValidation.addTaskValidator(task);
        if (!task.isValid()) {
            log.error(task.toString());
            return ResponseEntity.badRequest().body(task);
        }

        TaskEntity taskEntity = taskMapper.taskToTaskEntity(task);
        taskEntity.setPerson(personEntity);

        return ResponseEntity.ok(taskMapper.taskEntityToTask(taskDAO.addTask(taskEntity)));
    }

    @Override
    public ResponseEntity addNewTasks(List<Task> tasks, Long personId) {
        log.info("Was calling addNewTasks. Input tasks: " + tasks.toString());
        PersonEntity personEntity = personDao.findPersonById(personId);

        if (personEntity == null) {
            log.error(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), personId));
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(PERSON_NOT_FOUND), personId));
        }

        if (taskValidation.availableCountTasksToPersonValidator(tasks, personEntity)) {
            log.error(MessageFormat.format(errorMsg.getString(TOO_MANY_TASKS), personEntity.getCountAvailableTasks()));
            return ResponseEntity.badRequest().body(MessageFormat.format(errorMsg.getString(TOO_MANY_TASKS), personEntity.getCountAvailableTasks()));
        }

        List<Task> response = new ArrayList<>();
        for (Task task : tasks) {
            taskValidation.addTaskValidator(task);
            if (task.isValid()) {
                TaskEntity taskEntityTemp = taskMapper.taskToTaskEntity(task);
                taskEntityTemp.setPerson(personEntity);
                response.add(taskMapper
                        .taskEntityToTask(taskDAO
                                .addTask(taskEntityTemp)));
            } else {
                log.error(task.toString());
                response.add(task);
            }
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity updateTask(Task task) {
        log.info("Was calling updateTask. Input task: " + task.toString());
        taskValidation.updateTaskValidator(task);
        if (!task.isValid()) {
            log.error(task.toString());
            return ResponseEntity.badRequest().body(task);
        }
        return ResponseEntity.ok(taskMapper.taskEntityToTask(taskDAO.updateTask(taskMapper.taskToTaskEntity(task))));
    }
}
