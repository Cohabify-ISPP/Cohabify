package org.ispp4.cohabify.chat;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.CreateChatRequest;
import org.ispp4.cohabify.user.Plan;
import org.springframework.http.HttpStatus;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/api/chat/")
public class ChatController {

    private ChatService chatService;
    private UserService userService;

    @Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<Chat>> getAllChats(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Chat> chats = chatService.getActiveChatsByUser(user);
        for(int i = 0; i < chats.size(); i++) {
            Chat chat = chats.get(i);
            for(int j = 0; j < chat.getUsers().size(); j++) {
                if(chat.getUsers().get(j).getId().equals(user.getId())) {
                    chat.getUsers().remove(j);
                    break;
                }
            }
        }
        return ResponseEntity.status(200).body(chats);
    }

    @Transactional(readOnly = true)
    @PostMapping("")
    public ResponseEntity<?> createChat(@RequestBody CreateChatRequest request, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Chat> chats = chatService.getActiveChatsByUser(user);

        for(Chat chat:chats) {
            chat.getUsers().remove(user);
            Iterator<User> iter = request.getUsers().iterator();
            for(; iter.hasNext();) {
                User requestUser = iter.next();
                if(chat.getUsers().contains(requestUser)) {
                    chat.getUsers().remove(requestUser);
                } else {
                    break;
                }

                if(chat.getUsers().isEmpty() && !iter.hasNext()) {
                    return ResponseEntity.status(409).build();
                }
            }
        }

        if (user.getPlan().equals(Plan.BASIC) && chatService.getActiveChatsOpenedByUser(user).size() >= 3){
            return ResponseEntity.status(403).build();
        }
                
        request.getUsers().add(user);
        Chat chat = new Chat();
        chat.setIsAccepted(true);
        chat.setIsActive(true);
        chat.setMessages(List.of());
        chat.setUsers(request.getUsers());
        chat.setOpenedBy(user);
        chatService.saveChat(chat);
        return ResponseEntity.status(201).build();
    }

 @Transactional
    @DeleteMapping("{chatId}")
    public ResponseEntity<?> deleteChat(@PathVariable("chatId") ObjectId chatId, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Chat chat = chatService.getChatById(chatId);

        if(chat.getOpenedBy().equals(user)) {
            chat.setIsActive(false);   
            chatService.saveChat(chat);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); 
        }
    }

}