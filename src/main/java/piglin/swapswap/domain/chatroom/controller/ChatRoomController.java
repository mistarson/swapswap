package piglin.swapswap.domain.chatroom.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import piglin.swapswap.domain.chatroom.dto.ChatRoomResponseDto;
import piglin.swapswap.domain.chatroom.service.ChatRoomServiceImpl;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.message.dto.MessageDto;
import piglin.swapswap.global.annotation.AuthMember;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomServiceImpl chatRoomService;

    @GetMapping("/chats")
    public String getChatRoomList(@AuthMember Member member, Model model) {
        List<ChatRoomResponseDto> chatRoomList = chatRoomService.getChatRoomList(member);
        model.addAttribute("chatRoomList", chatRoomList);

        return "chat/chatroomList";
    }

    @GetMapping("/chats/room/{roomId}")
    public String getChatRoom(@PathVariable String roomId, Model model) {
        ChatRoomResponseDto chatRoomDto = chatRoomService.findById(roomId);
        List<MessageDto> messageList = chatRoomService.getMessageByChatRoomId(roomId);
        model.addAttribute("messageList", messageList);
        model.addAttribute("chatRoomDto", chatRoomDto);

        return "chat/chatroom";
    }
}
