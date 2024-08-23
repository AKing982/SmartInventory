package org.example.smartinventory.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Notes
{
    private int noteId;
    private String noteTitle;
    private String noteContent;
    private String author;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private NoteType noteType;

    public Notes(int noteId, String noteTitle, String noteContent, String author, LocalDate createdAt, LocalDate updatedAt, NoteType noteType) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.noteType = noteType;
    }

    public Notes(){

    }
}


