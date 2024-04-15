package org.ispp4.cohabify.message;

import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.chat.Chat;
import org.ispp4.cohabify.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MessageService {
    
    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final ChatService chatService;

    @Transactional
    public Message saveMessage(Message message, Chat chat){
        Message savedMessage = messageRepository.save(message);
        List<Message> messages = chat.getMessages();
        messages.add(savedMessage);
        chat.setMessages(messages);
        chatService.saveChat(chat);
        return savedMessage;
    }

    @Transactional
    public Message getMessageById(ObjectId id){
        return messageRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteMessage(ObjectId id){
        messageRepository.deleteById(id);
    }

}
