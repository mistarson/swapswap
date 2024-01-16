package piglin.swapswap.domain.post.repository;

import static piglin.swapswap.domain.favorite.entity.QFavorite.favorite;
import static piglin.swapswap.domain.post.entity.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<PostGetListResponseDto> findAllPostListWithFavoriteAndPaging(Pageable pageable,
            Member member) {

        List<PostGetListResponseDto> content = queryFactory
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
                .where(isNotDeleted())
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id)
                .orderBy(post.modifiedUpTime.desc(), post.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(post.count())
                                 .from(post)
                                 .where(isNotDeleted())
                                 .limit(pageable.getPageSize())
                                 .offset(pageable.getOffset())
                                 .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Page<PostGetListResponseDto> searchPost(String title, Category categoryCond,
            Member member,
            Pageable pageable) {

        List<PostGetListResponseDto> content = queryFactory.select(post)
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
                .where(titleContains(title), categoryEq(categoryCond), isNotDeleted())
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id)
                .orderBy(post.modifiedUpTime.desc(), post.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(post.count())
                                 .from(post)
                                 .where(titleContains(title), categoryEq(categoryCond), isNotDeleted())
                                 .offset(pageable.getOffset())
                                 .limit(pageable.getPageSize())
                                 .fetchOne();

        return new PageImpl<>(content, pageable, count);
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
