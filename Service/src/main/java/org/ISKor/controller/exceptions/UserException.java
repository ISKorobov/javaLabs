package org.ISKor.controller.exceptions;

public class UserException extends RuntimeException{
    private UserException(String message) {super(message);}

    public static UserException usernameAlreadyExist(String username){
        return new UserException("User with this username:" + username + " already exist");
    }
    public static UserException noRole(String role){
        return new UserException("This role: " + role + "does not exist");
    }
    public static UserException ownerRegistered(int id){
        return new UserException("Owner with id" + id + " already registered");
    }
    public static UserException noOwner(int id){
        return new UserException("Owner with id: " + id + " does not exist");
    }
}
