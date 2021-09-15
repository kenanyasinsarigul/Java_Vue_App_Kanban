package com.vbt.kanban.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@NotNull
	private String name;

	@NotBlank
	@NotNull
	private String phone;

	@NotBlank
	@NotNull
	private String address;

	@NotBlank
	@NotNull
	private String birthday;

	@NotBlank
	@NotNull
	private String website;

    @OneToOne
    private Auth auth;
}

