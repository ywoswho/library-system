package net.ywoswho.librarysystem.exception;

public class RentalOwnershipException extends RuntimeException {
    public RentalOwnershipException() {
        super("User is not the owner of the rental");
    }
}
