package org.ispp4.cohabify.chat;

import java.util.List;

import org.ispp4.cohabify.message.Message;
import org.ispp4.cohabify.model.BaseEntity;
import org.ispp4.cohabify.user.User;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chats")
public class Chat extends BaseEntity{
    
    @NotNull
    private Boolean isAccepted;

    @DBRef
    @NotNull
    private List<User> users;

    @DBRef
    @NotNull
    private List<Message> messages;

    @DBRef
    @NotNull
    private User openedBy;
}
