package piglin.swapswap.domain.post.repository;

import static piglin.swapswap.domain.favorite.entity.QFavorite.favorite;
import static piglin.swapswap.domain.post.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.deal.constant.DealStatus;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.entity.QMember;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.constant.City;
import piglin.swapswap.domain.post.dto.response.PostListDetailResponseDto;
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
    public List<PostListDetailResponseDto> findPostListWithFavoriteByCursor(
            Member member, LocalDateTime cursorTime) {

        return queryFactory
                .select(Projections.constructor(PostListDetailResponseDto.class,
                        post.id,
                        post.member.id,
                        post.city,
                        post.dealStatus,
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
    public List<PostListDetailResponseDto> searchPostListWithFavorite(String titleCond,
            Category categoryCond, City cityCond,
            Member member, LocalDateTime cursorTime) {

        return queryFactory.select(post)
                .select(Projections.constructor(PostListDetailResponseDto.class,
                        post.id,
                        post.member.id,
                        post.city,
                        post.dealStatus,
                        post.title,
                        post.imageUrl,
                        post.modifiedUpTime,
                        post.viewCnt,
                        favorite.post.count(),
                        favoriteStatus(member)))
                .from(post)
                .where(titleContains(titleCond), categoryEq(categoryCond), cityEq(cityCond), isNotDeleted(),
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
                                post.dealStatus,
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
    public List<PostListDetailResponseDto> findAllMyFavoritePost(Member member,
            LocalDateTime cursorTime) {

        return queryFactory.select(Projections.constructor(PostListDetailResponseDto.class,
                        post.id,
                        post.member.id,
                        post.city,
                        post.dealStatus,
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

    @Override
    public List<PostListDetailResponseDto> findAllMyPostList(Member member, LocalDateTime cursorTime) {

        return queryFactory
                .select(Projections.constructor(PostListDetailResponseDto.class,
                        post.id,
                        post.member.id,
                        post.city,
                        post.dealStatus,
                        post.title,
                        post.imageUrl,
                        post.modifiedUpTime,
                        post.viewCnt,
                        favorite.post.count(),
                        favoriteStatus(member)))
                .from(post)
                .where(isNotDeleted(), lessThanCursorTime(cursorTime), post.member.id.eq(member.getId()))
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id)
                .orderBy(post.modifiedUpTime.desc(), post.id.desc())
                .limit(12)
                .fetch();
    }

    @Override
    public void updatePostListStatus(List<Long> postIdList, DealStatus dealStatus) {

        queryFactory
                .update(post)
                .set(post.dealStatus, dealStatus)
                .where(post.id.in(postIdList))
                .execute();

        em.flush();
        em.clear();
    }

    private BooleanExpression lessThanCursorTime(LocalDateTime cursorTime) {

        return cursorTime != null ? post.modifiedUpTime.lt(cursorTime) : null;
    }

    private BooleanExpression titleContains(String titleCond) {

        return titleCond != null ? Expressions.stringTemplate("function('replace',{0},{1},{2})",post.title," ","").contains(titleCond) : null;
    }

    private BooleanExpression categoryEq(Category categoryCond) {

        return categoryCond != null ? post.category.eq(categoryCond) : null;
    }

    private BooleanExpression cityEq(City cityCond) {

        return cityCond != null ? post.city.eq(cityCond) : null;
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

    @Override
    public void deleteAllPostByMember(Member loginMember) {
        queryFactory
                .update(post)
                .set(post.isDeleted, true)
                .where(post.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }

    @Override
    public void reRegisterPostByMember(Member loginMember) {
        queryFactory
                .update(post)
                .set(post.isDeleted, false)
                .where(post.member.eq(loginMember))
                .execute();

        em.flush();
        em.clear();
    }
}
