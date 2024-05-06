package org.ispp4.cohabify.message;

import java.time.LocalDateTime;

import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.user.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "messages")
public class Message extends BaseEntity{

    @NotNull
    private String text;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm")
    private LocalDateTime timeSent;

    @DBRef
    @NotNull
    private User sender;
      
}
