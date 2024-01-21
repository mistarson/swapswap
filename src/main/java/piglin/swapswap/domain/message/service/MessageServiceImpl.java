package piglin.swapswap.domain.message.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.mapper.ChatRoomMapper;
import piglin.swapswap.domain.chatroom.repository.ChatRoomRepository;
import piglin.swapswap.domain.chatroom_member.repository.ChatRoomMemberRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.request.MessageRequestDto;
import piglin.swapswap.domain.message.dto.response.MessageResponseDto;
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
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    @Transactional
    public void saveMessage(MessageRequestDto requestDto) {

        ChatRoom chatRoom = getChatRoomByMessageDto(requestDto);

        Message message = createMessageAndUpdateLastMessage(chatRoom, requestDto);

        messageRepository.save(message);
    }

    @Override
    public List<MessageResponseDto> getMessageByChatRoomId(Long roomId, Member member) {

        validateMember(member, roomId);

        List<Message> messageList = messageRepository.findAllByChatRoom_Id(roomId);

        return MessageMapper.messageToMessageDto(messageList);
    }

    private ChatRoom getChatRoomById(Long roomId) {

        return chatRoomRepository.findById(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION));
    }

    private ChatRoom getChatRoomByMessageDto(MessageRequestDto requestDto) {

        return chatRoomRepository.findById(requestDto.getChatRoomId()).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION)
        );
    }

    private Message createMessageAndUpdateLastMessage(ChatRoom chatRoom, MessageRequestDto requestDto) {

        Message message = MessageMapper.createMessage(requestDto, chatRoom);
        ChatRoomMapper.updateChatRoom(chatRoom, requestDto);

        return message;
    }

    private void validateMember(Member member, Long roomId) {

        ChatRoom chatRoom = getChatRoomById(roomId);

        chatRoomMemberRepository.findByChatRoomAndMember(chatRoom, member).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION));
    }

}
