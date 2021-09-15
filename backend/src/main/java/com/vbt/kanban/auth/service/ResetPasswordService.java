package com.vbt.kanban.auth.service;

import java.util.Optional;

import com.vbt.kanban.entity.Auth;
import com.vbt.kanban.entity.ResetPasswordToken;

public interface ResetPasswordService {

	void createResetPasswordToken(Auth auth);

	Optional<ResetPasswordToken> findByToken(String token);

	void update(ResetPasswordToken resetPasswordToken);
}
