package com.example.rgpdplatform.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public abstract class AbstractDocument {

    @Id
    private String id;

    @CreatedDate
    private Date creationDate;

    void prePersist() {
        creationDate = new Date();
    }

}
