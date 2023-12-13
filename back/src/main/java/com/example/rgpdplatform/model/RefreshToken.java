package com.example.rgpdplatform.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.Id;

@Document
@Data
public class RefreshToken {
    @Id
    private String id;
    @DocumentReference
    private User owner;
}
