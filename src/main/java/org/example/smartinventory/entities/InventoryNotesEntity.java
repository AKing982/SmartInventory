package org.example.smartinventory.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="inventoryNotes")
@Data
@NoArgsConstructor(access= AccessLevel.PUBLIC)
@Builder
public class InventoryNotesEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notesId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private UserEntity user;

    private String title;

    private String content;

    private String author;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String noteType;

    public InventoryNotesEntity(int notesId, UserEntity user, String title, String content, String author, LocalDate createdAt, LocalDate updatedAt, String noteType) {
        this.notesId = notesId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.noteType = noteType;
    }
}
