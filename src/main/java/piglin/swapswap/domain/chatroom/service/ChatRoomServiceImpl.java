package piglin.swapswap.domain.chatroom.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.entity.ChatRoom;
import piglin.swapswap.domain.chatroom.mapper.ChatRoomMapper;
import piglin.swapswap.domain.chatroom.repository.ChatRoomRepository;
import piglin.swapswap.domain.chatroom_member.entity.ChatRoomMember;
import piglin.swapswap.domain.chatroom_member.mapper.ChatRoomMemberMapper;
import piglin.swapswap.domain.chatroom_member.repository.ChatRoomMemberRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.global.exception.common.BusinessException;
import piglin.swapswap.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;

    @Override
    public List<ChatRoomResponseDto> getChatRoomList(Member member) {

        List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findAllByMember(member);
        List<ChatRoomResponseDto> chatRoomList = new ArrayList<>();

        return ChatRoomMapper.chatRoomMemberListToChatRoomDtoList(chatRoomMemberList, chatRoomList);
    }

    @Override
    public ChatRoomResponseDto findById(String roomId, Member member) {
        ChatRoom chatRoom = validateMember(member, roomId);

        return ChatRoomResponseDto.builder()
                                .id(chatRoom.getId())
                                .build();
    }

    @Override
    public String createChatroom(Member member, Long secondMemberId) {

        if (member.getId().equals(secondMemberId)) {
            throw new BusinessException(ErrorCode.CHAT_ONLY_DIFFERENT_USER_EXCEPTION);
        }

        Member secondMember = findMember(secondMemberId);

        List<ChatRoomMember> chatRoomMemberList1 = chatRoomMemberRepository.findAllByMember(member);
        List<ChatRoomMember> chatRoomMemberList2 = chatRoomMemberRepository.findAllByMember(secondMember);

        for (ChatRoomMember chatRoomMember1 : chatRoomMemberList1) {
            for (ChatRoomMember chatRoomMember2 : chatRoomMemberList2) {
                if (chatRoomMember1.getChatRoom().getId().equals(chatRoomMember2.getChatRoom().getId())) {
                    return chatRoomMember1.getChatRoom().getId();
                }
            }
        }

        return createChatRoomAndAddMember(member, secondMember);
    }

    @Override
    public void leaveChatRoom(Member member, String roomId) {

        List<ChatRoomMember> chatRoomMemberList = chatRoomMemberRepository.findAllByMember(member);

        for (ChatRoomMember chatRoomMember : chatRoomMemberList) {
            if (chatRoomMember.getChatRoom().getId().equals(roomId)) {

                chatRoomMemberRepository.delete(chatRoomMember);

                return;
            }
        }

        throw new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION);
    }

    private Member findMember(Long memberId) {

        return memberRepository.findById(memberId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
    }

    private ChatRoom findChatRoom(String roomId) {

        return chatRoomRepository.findById(roomId).orElseThrow(() ->
                new BusinessException(ErrorCode.NOT_FOUND_CHATROOM_EXCEPTION));
    }

    private ChatRoom validateMember(Member member, String roomId) {

        ChatRoom chatRoom = findChatRoom(roomId);

        for (ChatRoomMember chatRoomMember : chatRoomMemberRepository.findAllByChatRoom(chatRoom)) {
            if (chatRoomMember.getMember().getId().equals(member.getId())) {

                return chatRoom;
            }
        }

        throw new BusinessException(ErrorCode.NOT_CHAT_ROOM_MEMBER_EXCEPTION);
    }

    private String createChatRoomAndAddMember(Member member, Member secondMember) {

        ChatRoom chatRoom = chatRoomRepository.save(ChatRoomMapper.createChatRoom());
        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, member));
        chatRoomMemberRepository.save(ChatRoomMemberMapper.createChatRoomMember(chatRoom, secondMember));

        return chatRoom.getId();
    }

}