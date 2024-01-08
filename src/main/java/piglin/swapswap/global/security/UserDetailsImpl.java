package piglin.swapswap.global.security;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import piglin.swapswap.domain.member.constant.MemberRoleEnum;
import piglin.swapswap.domain.member.entity.Member;

@Getter
public class UserDetailsImpl implements UserDetails {

	private final Member member;

	public UserDetailsImpl( Member member) {
		this.member = member;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	public Member getUser() {
		return this.member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		MemberRoleEnum role = member.getRole();
		String authority = role.getAuthority();

		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(simpleGrantedAuthority);

		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
