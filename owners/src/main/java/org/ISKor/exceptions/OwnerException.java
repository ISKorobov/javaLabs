package org.ISKor.exceptions;

public class OwnerException extends RuntimeException {
    private OwnerException(String message) {
        super(message);
    }

    public static OwnerException kittyAlreadyExistsException(int kittyId) {
        return new OwnerException("Kitty with id " + kittyId + " already exists and is already taken");
    }

    public static OwnerException noSuchOwner(int ownerId) {
        return new OwnerException("Owner with id " + ownerId + " doesn't exist");
    }
}