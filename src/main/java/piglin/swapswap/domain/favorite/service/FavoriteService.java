package piglin.swapswap.domain.favorite.service;

import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

public interface FavoriteService {

    boolean isFavorite(Post post, Member member);

    Long getPostFavoriteCnt(Post post);

    void updateFavorite(Member member, Post post);
}
