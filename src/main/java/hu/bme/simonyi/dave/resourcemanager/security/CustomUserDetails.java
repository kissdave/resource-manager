package hu.bme.simonyi.dave.resourcemanager.security;

import hu.bme.simonyi.dave.resourcemanager.model.User;
import hu.bme.simonyi.dave.resourcemanager.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dkiss on 2016. 11. 07..
 */
public class CustomUserDetails extends User implements UserDetails {

    List<String> rolesString = new ArrayList<>();

    public CustomUserDetails(User user) {
        super(user);
        convertRolesToSringArray();

    }

    private void convertRolesToSringArray() {
        if(this.getRoles() != null) {
            for ( UserRole e : this.getRoles()) {
                rolesString.add(e.getRoleName());
            }
        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString(rolesString));
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
        if(this.getEnabled() > 0){
            return true;
        } else {
            return false;
        }
    }
}
