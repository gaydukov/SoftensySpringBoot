package com.softensy.softensyspringboot.exception;

public class DoctorAbsentException extends RuntimeException {
    public DoctorAbsentException(String message) {
        super(message);
    }

}
