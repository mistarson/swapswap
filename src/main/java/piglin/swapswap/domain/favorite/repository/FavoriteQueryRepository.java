package piglin.swapswap.domain.favorite.repository;

import java.util.List;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

public interface FavoriteQueryRepository {

    void deleteAllFavoriteByMember(Member loginMember);

    void deleteAllFavoriteByPostList(List<Post> postList);

    void reRegisterFavoriteByMember(Member loginMember);

    void reRegisterFavoriteByPost(List<Post> postList);
}
