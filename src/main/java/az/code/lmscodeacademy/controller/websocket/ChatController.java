package az.code.lmscodeacademy.controller.websocket;

import az.code.lmscodeacademy.dto.websocket.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message receiveMessage(@Payload Message message) {
        return message;

    }

}
