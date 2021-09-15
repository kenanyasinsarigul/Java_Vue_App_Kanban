package com.vbt.kanban.service;

import java.util.List;

import com.vbt.kanban.entity.Account;
import com.vbt.kanban.entity.Project;
import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.exception.NotFoundException;
import org.springframework.security.core.parameters.P;

public interface ProjectService {

	Project add(Project project) throws AccountNotFoundException, NotFoundException;

	Project update(Project project) throws Exception;

	Project delete(Long projectId) throws Exception;

	List<Project> getAll();

	Project getProject(Long id) throws Exception;

	List<Project> getProjectOwner(Long ownerId) throws Exception;

	List<Project> myProject(Long user_id) throws Exception;

	void addMember(Project project, Account account);

	void removeMember(long projectId, long accountId);

	boolean isProjectMember(long id, String email);
	boolean isProjectExists(long id);
	
	long count();



}
