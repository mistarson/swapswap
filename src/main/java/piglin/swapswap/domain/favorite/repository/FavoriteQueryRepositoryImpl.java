package piglin.swapswap.domain.favorite.repository;

import static piglin.swapswap.domain.favorite.entity.QFavorite.favorite;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

public class FavoriteQueryRepositoryImpl implements FavoriteQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public FavoriteQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void deleteAllFavoriteByMember(Member loginMember) {
        queryFactory
                .update(favorite)
                .set(favorite.isDeleted, true)
                .where(favorite.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void deleteAllFavoriteByPostList(List<Post> postList) {
        queryFactory
                .update(favorite)
                .set(favorite.isDeleted, true)
                .where(favorite.post.in(postList))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void reRegisterFavoriteByMember(Member loginMember) {
        queryFactory
                .update(favorite)
                .set(favorite.isDeleted, false)
                .where(favorite.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void reRegisterFavoriteByPost(List<Post> postList) {
        queryFactory
                .update(favorite)
                .set(favorite.isDeleted, false)
                .where(favorite.post.in(postList))
                .execute();

        em.flush();
        em.clear();
    }
}
