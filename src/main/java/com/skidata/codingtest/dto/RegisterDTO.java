package com.skidata.codingtest.dto;

import com.skidata.codingtest.entity.Role;

public record RegisterDTO(String userName, String password, Role role) {
}
