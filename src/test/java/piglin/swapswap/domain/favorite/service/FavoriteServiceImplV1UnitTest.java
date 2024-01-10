package piglin.swapswap.domain.favorite.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import piglin.swapswap.domain.favorite.entity.Favorite;
import piglin.swapswap.domain.favorite.repository.FavoriteRepository;
import piglin.swapswap.domain.member.entity.Member;
import piglin.swapswap.domain.post.entity.Post;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplV1UnitTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteServiceImplV1 favoriteService;

    private Member member;
    private Post post;

    @Nested
    class IsFavoriteTestList {
        @Test
        @DisplayName("찜 되어 있는지 조회 - 성공 / 찜 안 함")
        void isFavorite_Success_No_Favorite() {
            // Given
            when(favoriteRepository.findByMemberAndPost(member, post)).thenReturn(Optional.empty());
            // When
            boolean result = favoriteService.isFavorite(post, member);
            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("찜 되어 있는지 조회 - 성공 / 찜 함")
        void isFavorite_Success_Favorite() {
            // Given
            Favorite favorite = Mockito.mock(Favorite.class);
            when(favoriteRepository.findByMemberAndPost(member, post)).thenReturn(Optional.of(favorite));
            // When
            boolean result = favoriteService.isFavorite(post, member);
            // Then
            assertThat(result).isTrue();
        }
    }

    @Nested
    class UpdateFavoriteTestList {
        @Test
        @DisplayName("찜 업데이트 - 성공 / 찜 함")
        void updateFavorite_Success_Favorite() {
            // Given
            Favorite favorite = Mockito.mock(Favorite.class);
            when(favoriteRepository.findByMemberAndPost(member, post)).thenReturn(Optional.of(favorite));
            // When
            favoriteService.updateFavorite(member, post);
            // Then
            verify(favoriteRepository).delete(any(Favorite.class));
        }

        @Test
        @DisplayName("찜 업데이트 - 성공 / 찜 안 함")
        void updateFavorite_Success_No_Favorite() {
            // Given
            Favorite favorite = Mockito.mock(Favorite.class);
            when(favoriteRepository.findByMemberAndPost(member, post)).thenReturn(Optional.empty());
            // When
            favoriteService.updateFavorite(member, post);
            // Then
            verify(favoriteRepository).save(any(Favorite.class));
        }
    }
}