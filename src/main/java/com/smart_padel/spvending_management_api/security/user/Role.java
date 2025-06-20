package com.smart_padel.spvending_management_api.security.user;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Getter
public enum Role {
    USER(Set.of(Permission.USER_READ)),
    ADMIN(Set.of(Permission.ADMIN_UPDATE, Permission.ADMIN_CREATE, Permission.ADMIN_DELETE, Permission.ADMIN_READ));
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities(){
       var authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toList());
       authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
       return authorities;
    }

}
