package com.vbt.kanban.task.service;

import java.util.List;
import java.util.Optional;

import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;

public interface TaskService {
	Task create(Task task) throws AccountNotFoundException;
	Task update(Task task) throws Exception;
	Task delete(Long taskId) throws Exception;
	List<Task> getByProjectId(Long projectId) throws Exception;
	long count();
    Optional<Task> getByTaskId(Long taskId);
}
