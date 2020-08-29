package com.portfolio.springboot_backend.model.message;

import lombok.Data;
import java.util.Date;

@Data
public class ChatMessage {
    private String sender;
    private String content;
    private Date date;
}
