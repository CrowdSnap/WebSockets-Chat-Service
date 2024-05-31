package com.crowdsnap.websocketschatservice.chatRoom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
