package com.scribe.backend.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "chapters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapters {

    @Id
    private String id; 

    @DBRef 
    private String story_id;

    private List<Multimedia> multimedia;

    private String title;

    private String content;

    private LocalDateTime publishedOn;
}
