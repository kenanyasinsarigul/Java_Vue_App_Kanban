package com.vbt.kanban.task.service;

import java.util.List;
import java.util.Optional;

import com.vbt.kanban.account.service.AccountService;
import com.vbt.kanban.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vbt.kanban.entity.Project;
import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;
import com.vbt.kanban.task.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private AccountService accountService;

	@Override
	public Task create(Task task) throws AccountNotFoundException {
		return this.taskRepository.save(task);
	}

	@Override
	public Task update(Task task) throws Exception {
		Optional<Task> taskOp = this.taskRepository.findById(task.getId());
		if (taskOp.isEmpty()) {
			throw new NotFoundException("Böyle bir task bulunamadı.");
		}
		return this.taskRepository.save(task);
	}

	@Override
	public Task delete(Long taskId) throws Exception {
		if (this.taskRepository.findById(taskId).isEmpty()) {
			throw new NotFoundException("Böyle bir task bulunamadı.");
		}
		 else {
				Task deletedTask = this.taskRepository.findById(taskId).get();
				this.taskRepository.delete(deletedTask);
				return deletedTask;
			}
	}

	@Override
	public List<Task> getByProjectId(Long projectId) throws Exception {
		List<Task> tasks = this.taskRepository.getByProjectId(projectId);
		return tasks;
	}

	@Override
	public long count() {
		return this.taskRepository.count();
	}

	@Override
	public Optional<Task> getByTaskId(Long taskId) {
		return this.taskRepository.findById(taskId);
	}

}
