package org.ispp4.cohabify.chat;

import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.message.Message;
import org.ispp4.cohabify.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatService {
    
    @Autowired
    private final ChatRepository chatRepository;

    @Transactional
    public Chat saveChat(Chat chat){
        return chatRepository.save(chat);
    }

    @Transactional
    public Chat getChatById(ObjectId id){
        return chatRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteChat(ObjectId id){
        chatRepository.deleteById(id);
    }
    
    @Transactional
    public List<Chat> getChatsByUser(User user){
        return chatRepository.findByUsers(user);
    }

    @Transactional
    public Chat acceptChat(Chat chat){
        chat.setIsAccepted(true);
        return chatRepository.save(chat);
    }

    @Transactional
    public List<Message> getMessagesFromChat(Chat chat){
        return chat.getMessages();
    } 

}
