package piglin.swapswap.domain.post.repository;

import static piglin.swapswap.domain.favorite.entity.QFavorite.favorite;
import static piglin.swapswap.domain.post.entity.QPost.post;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.entity.QMember;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.dto.response.PostGetResponseDto;

@Repository
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public PostQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostGetListResponseDto> findPostListWithFavoriteByCursor(
            Member member, LocalDateTime cursorTime) {

        return queryFactory
                .select(Projections.constructor(PostGetListResponseDto.class,
                        post.id,
                        post.member.id,
                        post.title,
                        post.imageUrl,
                        post.modifiedUpTime,
                        post.viewCnt,
                        favorite.post.count(),
                        favoriteStatus(member)))
                .from(post)
                .where(isNotDeleted(), lessThanCursorTime(cursorTime))
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id)
                .orderBy(post.modifiedUpTime.desc(), post.id.desc())
                .limit(12)
                .fetch();
    }

    @Override
    public List<PostGetListResponseDto> searchPostListWithFavorite(String titleCond,
            Category categoryCond,
            Member member, LocalDateTime cursorTime) {

        return queryFactory.select(post)
                .select(Projections.constructor(PostGetListResponseDto.class,
                        post.id,
                        post.member.id,
                        post.title,
                        post.imageUrl,
                        post.modifiedUpTime,
                        post.viewCnt,
                        favorite.post.count(),
                        favoriteStatus(member)))
                .from(post)
                .where(titleContains(titleCond), categoryEq(categoryCond), isNotDeleted(),
                        lessThanCursorTime(cursorTime))
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id)
                .orderBy(post.modifiedUpTime.desc(), post.id.desc())
                .limit(12)
                .fetch();
    }

    @Override
    public void updatePostViewCnt(Long postId) {

        queryFactory.update(post)
                .set(post.viewCnt, post.viewCnt.add(1L))
                .where(isNotDeleted(), post.id.eq(postId))
                .execute();
        em.flush();
        em.clear();
    }

    @Override
    public PostGetResponseDto findPostWithFavorite(Long postId, Member member) {

        return queryFactory.select(
                        Projections.constructor(PostGetResponseDto.class,
                                post.member.id,
                                post.member.nickname,
                                post.title,
                                post.content,
                                post.category,
                                post.city,
                                post.imageUrl,
                                post.viewCnt,
                                post.upCnt,
                                favorite.post.count(),
                                post.modifiedUpTime,
                                favoriteStatus(member)))
                .from(post)
                .where(isNotDeleted(), post.id.eq(postId))
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .join(QMember.member)
                .on(post.member.id.eq(QMember.member.id))
                .fetchOne();
    }

    @Override
    public List<PostGetListResponseDto> findAllMyFavoritePost(Member member,
            LocalDateTime cursorTime) {

        return queryFactory.select(Projections.constructor(PostGetListResponseDto.class,
                        post.id,
                        post.member.id,
                        post.title,
                        post.imageUrl,
                        post.modifiedUpTime,
                        post.viewCnt,
                        JPAExpressions.select(favorite.count())
                                      .from(favorite)
                                      .where(favorite.post.eq(post)),
                        favoriteStatus(member)
                ))
                .from(post)
                .where(isNotDeleted(), lessThanCursorTime(cursorTime), favorite.member.eq(member))
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id)
                .orderBy(post.modifiedUpTime.desc())
                .limit(12)
                .fetch();
    }

    private BooleanExpression lessThanCursorTime(LocalDateTime cursorTime) {

        return cursorTime != null ? post.modifiedUpTime.lt(cursorTime) : null;
    }

    private BooleanExpression titleContains(String titleCond) {

        return titleCond != null ? post.title.contains(titleCond) : null;
    }

    private BooleanExpression categoryEq(Category categoryCond) {

        return categoryCond != null ? post.category.eq(categoryCond) : null;
    }

    private BooleanExpression isNotDeleted() {

        return post.isDeleted.isFalse();
    }

    private BooleanExpression favoriteStatus(Member member) {

        if (member == null) {

            return Expressions.asBoolean(false);
        }

        return new JPAQueryFactory(em).selectOne()
                .from(favorite)
                .where(favorite.member.id.eq(member.getId()), favorite.post.id.eq(post.id))
                .exists();
    }
}
