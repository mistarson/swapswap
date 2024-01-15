package piglin.swapswap.domain.post.repository;

import static piglin.swapswap.domain.favorite.entity.QFavorite.*;
import static piglin.swapswap.domain.post.entity.QPost.*;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import piglin.swapswap.domain.favorite.entity.QFavorite;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.constant.Category;
import piglin.swapswap.domain.post.dto.response.PostGetListResponseDto;
import piglin.swapswap.domain.post.entity.Post;
import piglin.swapswap.domain.post.entity.QPost;

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
                        memberEq(member).as("favoriteStatus")))
                .from(post)
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id, favorite.member.id)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(post.count())
                                 .from(post)
                                 .limit(pageable.getPageSize())
                                 .offset(pageable.getOffset())
                                 .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }

    @Override
    public Page<PostGetListResponseDto> searchPost(String title, Category categoryCond, Member member,
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
                        memberEq(member).as("favoriteStatus")))
                .from(post)
                .where(titleContains(title), categoryEq(categoryCond))
                .leftJoin(favorite)
                .on(favorite.post.eq(post))
                .groupBy(post.id, favorite.member.id)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long count = queryFactory.select(post.count())
                .from(post)
                .where(titleContains(title), categoryEq(categoryCond), post.isDeleted.eq(false))
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

    private BooleanExpression memberEq(Member member) {

        Long memberId;

        if(member == null) {
            memberId = 0L;
        } else {
            memberId = member.getId();
        }

        return new CaseBuilder().when(favorite.member.id.eq(memberId))
                                .then(true)
                                .otherwise(false);
    }
}
