package com.crowdsnap.websocketschatservice.chat;

import com.crowdsnap.websocketschatservice.chatRoom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatRoomId = chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getReceiverId(),
                true
        ).orElseThrow();
        chatMessage.setChatId(chatRoomId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> getChatMessages(String senderId, String receiverId) {
        var chatId = chatRoomService.getChatRoomId(senderId, receiverId, false);
        return chatId.map(chatMessageRepository::findByChatId).orElse(new ArrayList<>());
    }

}
