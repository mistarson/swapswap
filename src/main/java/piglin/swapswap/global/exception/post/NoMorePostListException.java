package piglin.swapswap.global.exception.post;

public class NoMorePostListException extends RuntimeException {

    public NoMorePostListException() {
        super("더이상 로드할 게시물이 없습니다.");
    }
}
