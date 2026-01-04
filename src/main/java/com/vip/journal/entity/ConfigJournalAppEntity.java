package com.vip.journal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "app_config")
public class ConfigJournalAppEntity {

    @Id
    private ObjectId id;
    private String key;
    private String value;
}
