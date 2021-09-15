package com.vbt.kanban.comment.service;

import com.vbt.kanban.comment.repository.CommentRepository;
import com.vbt.kanban.entity.Comment;
import com.vbt.kanban.entity.Task;
import com.vbt.kanban.exception.AccountNotFoundException;
import com.vbt.kanban.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TaskService taskService;

    @Override
    public Comment create(Comment comment) throws AccountNotFoundException {
        return commentRepository.save(comment);
    }

    @Override
    public Comment update(Comment comment) throws Exception {
        return commentRepository.save(comment);
    }

    @Override
    public void delete(long commentId) throws Exception {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> getByTaskId(long task) throws Exception {
        Optional<Task> taskOp = taskService.getByTaskId(task);
        return commentRepository.findByTaskOrderByAtDesc(taskOp.get());
    }
}
