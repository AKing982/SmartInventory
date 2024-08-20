package org.example.smartinventory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidCategorySegmentException extends IllegalArgumentException
{
    public InvalidCategorySegmentException(String s) {
        super("Invalid category segment: " + s);
    }
}
