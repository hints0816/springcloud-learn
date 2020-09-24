package com.hints.authserver.model;

import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.springframework.security.core.GrantedAuthority;
@Data
@Table("testrole")
public class Role implements GrantedAuthority {

    @Name
    private String id;
    @Column
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
    @Override
    public String toString(){
        return name;
    }
}
