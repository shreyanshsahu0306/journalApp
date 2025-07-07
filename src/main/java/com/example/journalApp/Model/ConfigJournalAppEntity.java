package com.example.journalApp.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journalApp")
@Data
@NoArgsConstructor
public class ConfigJournalAppEntity {

    private String key;

    private String value;

}
