package com.vbt.kanban.account.dto;

import com.vbt.kanban.auth.dto.AuthDto;
import lombok.Data;

@Data
public class AccountDto {
    private long id;
    private String name;
    private String phone ;
    private String address;
    private String birthday;
    private String website;
    private AuthDto auth;
}
