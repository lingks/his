package com.his.manager.config;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * Created by beaver on 2017/6/12.
 */
public class RichUserDetails implements UserDetails, CredentialsContainer {
    static final long serialVersionUID = 1L;
    
    private String id;
    private String phone;
    private String loginNmae;
    private String password;
    private List<GrantedAuthority> authoritie;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Integer type;
    private boolean enabled;
    private String employeeId;
    public RichUserDetails() {
    
    }
    
    public RichUserDetails(String id, String phone, String loginNmae, String password, Integer mType,
                           Collection<? extends GrantedAuthority> authorities, String employeeId) {
        this(id, phone, loginNmae, password,mType, true, true, true, true, authorities,employeeId);
    }
    
    public RichUserDetails(String id, String phone, final String loginNmae, String password, Integer type,
                           boolean enabled,
                           boolean accountNonExpired, boolean credentialsNonExpired,
                           boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String employeeId) {
        this.id = id;
        this.phone = phone;
        this.loginNmae = loginNmae;
        this.password = password;
        this.type = type;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authoritie = Collections.unmodifiableList(new ArrayList<>(authorities));
        this.employeeId = employeeId;
    }
    
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authoritie;
    }
    
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public String getUsername() {
        return loginNmae;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    @Override
    public void eraseCredentials() {
        password = null;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getLoginNmae() {
        return loginNmae;
    }
    
    public void setLoginNmae(String loginNmae) {
        this.loginNmae = loginNmae;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<GrantedAuthority> getAuthoritie() {
        return authoritie;
    }
    
    public void setAuthoritie(List<GrantedAuthority> authoritie) {
        this.authoritie = authoritie;
    }
    
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getId() {
        return id;
    }
    
    public String getPhone() {
        return phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(
                new RichUserDetails.AuthorityComparator());
        
        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                    "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }
        
        return sortedAuthorities;
    }
    
    
    public static class AuthorityComparator implements Comparator<GrantedAuthority>,
                                                       Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
        
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            // the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }
            
            if (g1.getAuthority() == null) {
                return 1;
            }
            
            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
    
    
}
