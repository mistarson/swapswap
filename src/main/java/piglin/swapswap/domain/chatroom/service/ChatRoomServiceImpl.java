package piglin.swapswap.domain.chatroom.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.repository.ChatRoomRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.MessageDto;
import piglin.swapswap.domain.message.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<ChatRoomResponseDto> getChatRoomList(Member member) {

        return chatRoomRepository.findAllByChatRoomMembersContaining(member).stream().map(chatRoom -> {

            String lastChatMessage = "";
            LocalDateTime modifiedAt = null;

            if (chatRoom.getLastChatMessage() != null) {
                lastChatMessage = chatRoom.getLastChatMessage().getText();
                modifiedAt = chatRoom.getLastChatMessage().getModifiedTime();
            }

            return ChatRoomResponseDto.builder()
                    .id(chatRoom.getId())
                    .lastChatMessage(lastChatMessage)
                    .modifiedAt(modifiedAt)
                    .build();

        }).toList();
    }

    @Override
    public List<MessageDto> getMessageByChatRoomId(String roomId) {
        return messageRepository.findAllByChatRoom_Id(roomId).stream().map(message -> MessageDto.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoom().getId())
                .type(message.getType())
                .senderNickname(message.getSenderNickname())
                .text(message.getText())
                .build()).toList();
    }

    @Override
    public ChatRoomResponseDto findById(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 채팅방입니다."));

        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .build();
    }
}
