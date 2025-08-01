package com.subroto.JournalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
@Data //--> it is equivalent to setter,getter,noargsConstructor,AllArgsConstructor --> Project Lombok
@NoArgsConstructor
public class JournalEntity {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String desc;

    private LocalDateTime date;




}
