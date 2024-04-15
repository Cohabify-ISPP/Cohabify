package org.ispp4.cohabify.chat;

import java.security.Principal;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.ChatMessage;
import org.ispp4.cohabify.message.Message;
import org.ispp4.cohabify.message.MessageService;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class ChatWebSocketController {

    private ChatService chatService;
    private MessageService messageService;
    private UserService userService;

    @MessageMapping("/{chatId}")
	@SendTo("/chat/{chatId}")
	public Message handleChatMessage(@DestinationVariable ObjectId chatId, @Payload ChatMessage msg, @AuthenticationPrincipal Principal principal) throws Exception {
        Chat chat = chatService.getChatById(chatId);
        User user = userService.getUserByUsername(principal.getName());
        if(chat.getUsers().stream().map(u -> u.getId()).toList().contains(user.getId())) {
            Message message = new Message();
            message.setSender(user);
            message.setTimeSent(LocalDateTime.now());
            message.setText(msg.getMsg());
            message = messageService.saveMessage(message, chat);
            return message;
        } else {
            log.info(String.format("The principal '%s' has tried to send a message to a chat on which they are not participating.", principal.getName()));
            return null;
        }
	}

}
