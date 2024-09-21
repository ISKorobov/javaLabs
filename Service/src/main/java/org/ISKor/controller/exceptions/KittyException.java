package org.ISKor.controller.exceptions;

public class KittyException extends RuntimeException {
    private KittyException(String message) {
        super(message);
    }

    public static KittyException noSuchKitty(int id) {
        return new KittyException("Kitty with ID " + id + " doesn't exist.");
    }

    public static KittyException noSuchOwner(int ownerId) {
        return new KittyException("Owner with ID " + ownerId + " doesn't exist.");
    }

    public static KittyException noSuchColor(String colourName) {
        return new KittyException("There is no color " + colourName + " in database.");
    }

    public static KittyException noSuchBreed(String breedName) {
        return new KittyException("There is no breed " + breedName + " in database.");
    }
}
