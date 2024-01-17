package piglin.swapswap.domain.message.service;

import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.repository.ChatRoomRepository;
import piglin.swapswap.domain.message.dto.MessageDto;
import piglin.swapswap.domain.message.entity.Message;
import piglin.swapswap.domain.message.mapper.MessageMapper;
import piglin.swapswap.domain.message.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public void saveMessage(MessageDto messageDto) {

        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId()).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 채팅방 입니다.")
        );

        Message message = MessageMapper.createMessage(messageDto, chatRoom);
        chatRoom.setLastChatMessage(message);
        messageRepository.save(message);
    }
}
