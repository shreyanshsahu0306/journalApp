package com.example.journalApp.Config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "email_Config")
@Data
@NoArgsConstructor
public class EmailConfig {

    private String email;

    private String pswd;

}