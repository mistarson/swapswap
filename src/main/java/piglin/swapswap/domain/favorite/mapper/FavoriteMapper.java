package piglin.swapswap.domain.favorite.mapper;

import piglin.swapswap.domain.favorite.entity.Favorite;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

public class FavoriteMapper {

    public static Favorite createFavorite(Member member, Post post) {

        return Favorite.builder()
                       .member(member)
                       .post(post)
                       .isDeleted(false)
                       .build();
    }
}
