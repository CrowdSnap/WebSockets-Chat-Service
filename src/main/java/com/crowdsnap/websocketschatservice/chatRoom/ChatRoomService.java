package com.crowdsnap.websocketschatservice.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(String senderId, String receiverId, boolean createNewRoomIfNotExists) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .map(ChatRoom::getId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, receiverId);
                        ChatRoom chatRoom = ChatRoom.builder()
                                .senderId(senderId)
                                .receiverId(receiverId)
                                .build();
                        chatRoomRepository.save(chatRoom);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    private String createChatId(String senderId, String receiverId) {
        var chatId = String.format("%s_%s", senderId, receiverId);

        ChatRoom senderReceiverChatRoom = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        ChatRoom receiverSenderChatRoom = ChatRoom.builder()
                .chatId(chatId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();

        chatRoomRepository.save(senderReceiverChatRoom);
        chatRoomRepository.save(receiverSenderChatRoom);

        return chatId;
    }
}
