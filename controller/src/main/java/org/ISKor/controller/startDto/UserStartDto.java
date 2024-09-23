package org.ISKor.controller.startDto;

import jakarta.validation.constraints.NotBlank;

public record UserStartDto (@NotBlank(message = "Username can`t be empty") String username, @NotBlank(message = "Password can`t be empty") String password, @NotBlank(message = "Role can`t be empty") String roleName, int ownerId){
}
