package piglin.swapswap.domain.favorite.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.favorite.entity.Favorite;
import piglin.swapswap.domain.favorite.repository.FavoriteRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImplV1 implements FavoriteService{

    private final FavoriteRepository favoriteRepository;

    @Override
    public boolean findFavorite(Post post, Member member) {
        Optional<Favorite> favorite = favoriteRepository.findByMemberAndPost(member, post);

        return favorite.isPresent();
    }

    @Override
    public Long getPostFavoriteCnt(Post post) {
        return favoriteRepository.countByPost(post);
    }
}
