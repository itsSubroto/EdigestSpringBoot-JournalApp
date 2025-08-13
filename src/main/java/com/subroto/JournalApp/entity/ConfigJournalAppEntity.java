package com.subroto.JournalApp.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal_app")
@Data //--> it is equivalent to setter,getter,noargsConstructor,AllArgsConstructor --> Project Lombok
public class ConfigJournalAppEntity {


    private String key;

    private String value;


}
