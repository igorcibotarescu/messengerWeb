package com.ici.ChatApp.config;

import com.ici.ChatApp.chat.ChatMessage;
import com.ici.ChatApp.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String userName = (String) headerAccessor.getSessionAttributes().get("username");
        if(userName != null) {
            log.info("User disconnected: {}", userName);
            var chatMessage = ChatMessage.builder().type(MessageType.LEAVE).sender(userName).build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
