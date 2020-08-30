package com.portfolio.springboot_backend.controller;

import com.portfolio.springboot_backend.model.message.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = {"http://localhost:3000", "https://posungkim.github.io"}, maxAge = 3600)
@Controller
public class ChatController {

    // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/messaging/handler/annotation/MessageMapping.html
    @MessageMapping("/mychat.register") // @Payload는 Default 값으로 설정된다고 함
    @SendTo("/topic/public")   // /topic/public 구독자/참여자들에게 chatMessage를 BroadCast함
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println("/mychat.register");
        return chatMessage;
    }

    @MessageMapping("/send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) throws Exception {
        System.out.println("/mychat.send " + chatMessage);
        return chatMessage;
    }

}
