package com.hints.authserver.model;

import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.ManyMany;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
@Data
@Table("test0816")
public class User implements UserDetails, Serializable {
    @Name
    private String USID;
    @Column
    private String NAME;
    @Column
    private String PAWD;
    @ManyMany(target = Role.class,
            relation = "testuserrole",
            from = "user_id:USID",
            to = "role_id:id")
    private List<Role> UTYPE;

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return UTYPE;
    }

    @Override
    public String getPassword() {
        return PAWD;
    }

    @Override
    public String getUsername() {
        return USID;
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
