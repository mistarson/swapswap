package piglin.swapswap.domain.favorite.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import piglin.swapswap.domain.favorite.entity.Favorite;
import piglin.swapswap.domain.favorite.mapper.FavoriteMapper;
import piglin.swapswap.domain.favorite.repository.FavoriteRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImplV1 implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public boolean isFavorite(Post post, Member member) {

        Optional<Favorite> favorite = favoriteRepository.findByMemberAndPost(member, post);

        return favorite.isPresent();
    }

    @Override
    public Long getPostFavoriteCnt(Post post) {

        return favoriteRepository.countByPost(post);
    }

    @Override
    @Transactional
    public void updateFavorite(Member member, Post post) {

        Optional<Favorite> favorite = favoriteRepository.findByMemberAndPost(member, post);

        if (favorite.isPresent()) {
            favoriteRepository.delete(favorite.get());
        } else {
            favoriteRepository.save(FavoriteMapper.createFavorite(member, post));
        }
    }

    @Override
    public void deleteFavoritesByPostId(Long postId) {

        List<Favorite> favoriteList = favoriteRepository.findAllByPostId(postId);

        for(Favorite favorite : favoriteList) {
            favorite.deleteFavorite();
        }
    }
}
