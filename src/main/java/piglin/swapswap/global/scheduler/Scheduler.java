package piglin.swapswap.global.scheduler;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import piglin.swapswap.domain.favorite.repository.FavoriteRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.member.repository.MemberRepository;
import piglin.swapswap.domain.post.entity.Post;
import piglin.swapswap.domain.post.event.DeleteImageUrlListEvent;
import piglin.swapswap.domain.post.repository.PostRepository;
import piglin.swapswap.global.s3.S3ImageService;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final FavoriteRepository favoriteRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredMember() {

        LocalDateTime fourTeenDaysAgo = LocalDateTime.now().minusDays(14);

        List<Member> memberListToDelete = memberRepository.findByIsDeletedIsTrueAndModifiedTimeBefore(fourTeenDaysAgo);

        memberRepository.deleteAll(memberListToDelete);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deletePostComplete() {

        List<String> postImageUrlListToDelete = new ArrayList<>();

        LocalDateTime fourTeenDaysAgo = LocalDateTime.now().minusDays(14);
        List<Post> postListToDelete = postRepository.findByIsDeletedIsTrueAndModifiedTimeBefore(fourTeenDaysAgo);

        for(Post post : postListToDelete) {

            for (int i = 0; i < post.getImageUrl().size(); i++) {
                postImageUrlListToDelete.add((String) post.getImageUrl().get(i));
            }
        }

        applicationEventPublisher.publishEvent(new DeleteImageUrlListEvent(postImageUrlListToDelete));

        postRepository.deleteAll(postListToDelete);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteFavoriteComplete() {

        LocalDateTime fourTeenDaysAgo = LocalDateTime.now().minusDays(14);

        favoriteRepository.deleteAllByIsDeletedIsTrueAndModifiedTimeBefore(fourTeenDaysAgo);
    }
}
