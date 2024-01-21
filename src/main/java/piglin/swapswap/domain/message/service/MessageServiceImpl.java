package piglin.swapswap.domain.message.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.mapper.ChatRoomMapper;
import piglin.swapswap.domain.chatroom.service.ChatRoomServiceImpl;
import piglin.swapswap.domain.chatroom_member.service.ChatRoomMemberServiceImpl;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;
import piglin.swapswap.domain.message.entity.Message;
import piglin.swapswap.domain.message.mapper.MessageMapper;
import piglin.swapswap.domain.message.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomServiceImpl chatRoomService;
    private final ChatRoomMemberServiceImpl chatRoomMemberService;

    @Override
    @Transactional
    public void saveMessage(MessageRequestDto requestDto) {

        ChatRoom chatRoom = chatRoomService.findChatRoomByMessageDto(requestDto);

        Message message = createMessageAndUpdateLastMessage(chatRoom, requestDto);

        messageRepository.save(message);
    }

    @Override
    public List<MessageResponseDto> getMessageByChatRoomId(Long roomId, Member member) {

        validateMember(member, roomId);

        List<Message> messageList = messageRepository.findAllByChatRoom_Id(roomId);

        return MessageMapper.messageToMessageDto(messageList);
    }

    @Override
    public void messageIsDeletedToTrue(ChatRoom chatRoom) {

        messageRepository.messageIsDeletedToTrue(chatRoom);
    }

    private Message createMessageAndUpdateLastMessage(ChatRoom chatRoom, MessageRequestDto requestDto) {

        Message message = MessageMapper.createMessage(requestDto, chatRoom);
        ChatRoomMapper.updateChatRoom(chatRoom, requestDto);

        return message;
    }

    private void validateMember(Member member, Long roomId) {

        ChatRoom chatRoom = chatRoomService.findChatRoom(roomId);

        chatRoomMemberService.findByChatRoomAndMember(chatRoom, member);
    }

}
