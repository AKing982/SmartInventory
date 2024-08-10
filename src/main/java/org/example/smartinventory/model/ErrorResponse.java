package org.example.smartinventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse
{
    private int status;
    private String message;


}
