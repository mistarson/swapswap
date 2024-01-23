package piglin.swapswap.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import piglin.swapswap.domain.common.BaseTime;
import piglin.swapswap.domain.member.constant.MemberRole;
import piglin.swapswap.domain.wallet.entity.Wallet;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberRole role;

    @Column(nullable = false)
    private Boolean isDeleted;

    @JoinColumn(name = "wallet_id")
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    private Wallet wallet;

    public void updateMember(String nickname) {
        this.nickname = nickname;
    }

    public void deleteMember() {
        isDeleted = true;
    }

    public void reRegisterMember() {
        isDeleted = false;
    }
}
