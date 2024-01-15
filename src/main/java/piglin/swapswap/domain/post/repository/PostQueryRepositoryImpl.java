package piglin.swapswap.domain.post.repository;

import static piglin.swapswap.domain.post.entity.QPost.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
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
    public Page<Post> searchPost(String title, Category categoryCond,
            Pageable pageable) {

        List<Post> content = queryFactory.select(post)
                                         .from(post)
                                         .where(titleContains(title), categoryEq(categoryCond), post.isDeleted.eq(false))
                                         .offset(pageable.getOffset())
                                         .limit(pageable.getPageSize())
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
}
