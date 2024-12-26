package com.scribe.backend.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.*;
import lombok.Data;

@Document(collection="multimedia")
@Data
public class Multimedia {

    @Id
    private int _id;
    private String url;
}
