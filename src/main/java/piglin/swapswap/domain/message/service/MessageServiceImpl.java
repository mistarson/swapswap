package piglin.swapswap.domain.message.service;

import java.util.List;
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
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public void saveMessage(MessageDto messageDto) {

        ChatRoom chatRoom = findChatRoom(messageDto);

        Message message = createMessageAndUpdateLastMessage(chatRoom, messageDto);

        messageRepository.save(message);
    }

    @Override
    public List<MessageDto> getMessageByChatRoomId(String roomId) {

        List<Message> messageList = messageRepository.findAllByChatRoom_Id(roomId);

        return MessageMapper.messageToMessageDto(messageList);
    }

    private ChatRoom findChatRoom(MessageDto messageDto) {

        return chatRoomRepository.findById(messageDto.getChatRoomId()).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION)
        );
    }

    private Message createMessageAndUpdateLastMessage(ChatRoom chatRoom, MessageDto messageDto) {

        Message message = MessageMapper.createMessage(messageDto, chatRoom);
        chatRoom.setLastMessage(message);

        return message;
    }
}
