package az.code.lmscodeacademy.dto.websocket;

import az.code.lmscodeacademy.entity.enums.MessageStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private MessageStatus status;
}
