package piglin.swapswap.domain.post.repository;

import static piglin.swapswap.domain.favorite.entity.QFavorite.favorite;
import static piglin.swapswap.domain.post.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;

@Repository
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public PostQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostGetListResponseDto> findAllPostListWithFavoriteAndPaging(
            Member member, LocalDateTime cursorTime) {

        return queryFactory
                .select(Projections.fields(PostGetListResponseDto.class,
                        post.id.as("postId"),
                        post.member.id.as("memberId"),
                        post.title,
                        post.imageUrl,
                        post.modifiedUpTime,
                        post.viewCnt,
                        favorite.post.count().as("favoriteCnt"),
                        favoriteStatus(member).as("favoriteStatus")))
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
    public List<PostGetListResponseDto> searchPost(String title, Category categoryCond,
            Member member, LocalDateTime cursorTime) {

        return queryFactory.select(post)
                .select(Projections.fields(PostGetListResponseDto.class,
                        post.id.as("postId"),
                        post.member.id.as("memberId"),
                        post.title,
                        post.imageUrl,
                        post.modifiedUpTime,
                        post.viewCnt,
                        favorite.post.count().as("favoriteCnt"),
                        favoriteStatus(member).as("favoriteStatus")))
                .from(post)
                .where(titleContains(title), categoryEq(categoryCond), isNotDeleted(), lessThanCursorTime(cursorTime))
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id)
                .orderBy(post.modifiedUpTime.desc(), post.id.desc())
                .limit(12)
                .fetch();

    }

    private BooleanExpression lessThanCursorTime(LocalDateTime cursorTime) {

        return cursorTime != null ? post.modifiedUpTime.lt(cursorTime) : null;
    }

    private BooleanExpression titleContains(String title) {

        return title != null ? post.title.contains(title) : null;
    }

    private BooleanExpression categoryEq(Category category) {

        return category != null ? post.category.eq(category) : null;
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
