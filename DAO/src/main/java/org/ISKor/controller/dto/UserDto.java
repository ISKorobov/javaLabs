package org.ISKor.controller.dto;

public record UserDto(int id, String username, String password, String role, int ownerId) {
}