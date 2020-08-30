package com.portfolio.springboot_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // https://spring.io/guides/gs/messaging-stomp-websocket/  official reference 사이트가 존재
@EnableWebSocketMessageBroker // Web Socket은 Communication Protocol로서 양방향으로 frequently with low latency하게 데이터를 보내는 역할 수행
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override // Message Broker가 참여자들의 채팅방에 메세지들을 Broadcast하는 역할 수행
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // enable a simple memory-based message broker to carry messages back to the client on destinations prefixed with /topic
        registry.enableSimpleBroker("/topic");
        // designates the /mychat prefix for messages that are bound for methods annotated with @MessageMapping
        // mychat으로 시작하는 모든 주소들로 매핑된 message들은 @MessageMapping로 연결된 Controller에서 처리됌
        registry.setApplicationDestinationPrefixes("/mychat");
    }

    @Override // Stomp는 Simple Text Oriented Message Protocol로서 채팅 Text가 어디로 가야하는지 등을 정의할 수 있음
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // enabling SockJS fallback options so that alternate transports can be used if WebSocket is not available
        // WebSocket이 정상적으로 구동이 안되면 SockJS client가 가능한 transport를 찾는다고 함
        registry
                .addEndpoint("/webSocket")
                .setAllowedOrigins("http://localhost:3000", "https://posungkim.github.io")
                .withSockJS();
                //.setWebSocketEnabled(false);
    }
}
