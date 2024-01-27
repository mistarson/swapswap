package piglin.swapswap.domain.billpost.dto;

import lombok.Builder;

@Builder
public record BillPostResponseDto(Long postId,
                                  String postTile,
                                  String imageUrl) {

}
