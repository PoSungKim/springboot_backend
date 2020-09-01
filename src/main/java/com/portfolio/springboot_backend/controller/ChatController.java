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
    @MessageMapping("/join") // @Payload는 Default 값으로 설정된다고 함
    @SendTo("/topic/public")   // 참여할 시 환영 메세지
    public ChatMessage joinMessage(@Payload ChatMessage chatMessage) throws Exception {
        System.out.println("/mychat.join" + chatMessage);
        chatMessage.setContent( (chatMessage.getSender().equals("손님") ? "손님 " : chatMessage.getSender() + "님 ") + "안녕하세요! 오픈 채팅방에 오신 것을 환영합니다 ^^");
        chatMessage.setSender("오픈챗 봇");
        return chatMessage;
    }

    @MessageMapping("/send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) throws Exception {
        System.out.println("/mychat.send " + chatMessage);
        return chatMessage;
    }

    @MessageMapping("/leave") // @Payload는 Default 값으로 설정된다고 함
    @SendTo("/topic/public")   // 방을 나갈 시 메세지
    public ChatMessage leaveMessage(@Payload ChatMessage chatMessage) throws Exception {
        System.out.println("/mychat.leave" + chatMessage);
        chatMessage.setContent( (chatMessage.getSender().equals("손님") ? "손님" : chatMessage.getSender() + "님") + "께서 방을 나가셨습니다 ^^");
        chatMessage.setSender("오픈챗 봇");
        return chatMessage;
    }

}
