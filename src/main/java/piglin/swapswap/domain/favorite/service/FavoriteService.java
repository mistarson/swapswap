package piglin.swapswap.domain.favorite.service;

import java.util.List;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

public interface FavoriteService {

    boolean isFavorite(Post post, Member member);

    void updateFavorite(Member member, Post post);

    void deleteFavoritesByPostId(Long postId);

    void deleteAllFavoriteByMember(Member loginMember);

    void deleteAllFavoriteByPostList(List<Post> post);

    void reRegisterFavoriteByMember(Member loginMember);

    void reRegisterFavoriteByPost(List<Post> postList);
}
