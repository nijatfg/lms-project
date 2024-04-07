package az.code.lmscodeacademy.repository.chat;

import az.code.lmscodeacademy.entity.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatId(String chatId);
}
