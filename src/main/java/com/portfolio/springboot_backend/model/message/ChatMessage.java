package com.portfolio.springboot_backend.model.message;

import lombok.Data;

@Data
public class ChatMessage {
    private String sender;
    private String content;
    private String date;
}
