package com.his.manager.domain;

import com.his.common.model.PersistentObject;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;

@Entity
public class SysUser extends PersistentObject implements UserDetails, CredentialsContainer, Serializable {


    @Column
    private String id;
    @Id
    @Column(updatable = false, nullable = false)
    @Size(min = 0, max = 50)
    private String username;

    @Size(min = 0, max = 500)
    private String password;

    @Email
    @Size(min = 0, max = 50)
    private String email;
    /**
     * 是否启用0停用 1启用
     */
    private boolean activated = true;

    @Size(min = 0, max = 100)
    @Column(name = "activationkey")
    private String activationKey;

    @Size(min = 0, max = 100)
    @Column(name = "resetpasswordkey")
    private String resetPasswordKey;
    @Column
    private Integer type = 1;
   /* @ManyToMany
    @JoinTable(
            name = "sys_user_authority2",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_id"))
    private Set<SysAuthority> authorities;*/

   /* @ManyToMany
    @JoinTable(
            name = "sys_user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<SysGroup> groups;

    public Set<SysGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<SysGroup> groups) {
        this.groups = groups;
    }*/

    @Transient
    private List<GrantedAuthority> authoritie;
    @Transient
    private boolean accountNonExpired;
    @Transient
    private boolean accountNonLocked;
    @Transient
    private boolean credentialsNonExpired;
    @Transient
    private boolean enabled;
    @Size(min = 0, max = 64)
    @Column(name = "employee_id")
    private String employeeId;

    public SysUser() {
    }

    public SysUser(String id, String username, String password, boolean activated, Collection<? extends GrantedAuthority> authorities) {
        this(id,"",username, password, activated,  authorities);
    }

    public SysUser(String id , String email, String username, String password, boolean activated, Collection<? extends GrantedAuthority> authorities) {
        this.setId(id);
        this.email = email;
        this.username = username;
        this.password = password;
        this.activated = activated;
        this.authoritie = Collections.unmodifiableList(new ArrayList(authorities));this.authoritie = authoritie;
    }


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<GrantedAuthority> getAuthoritie() {
        return authoritie;
    }

    public void setAuthoritie(List<GrantedAuthority> authoritie) {
        this.authoritie = authoritie;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetPasswordKey() {
        return resetPasswordKey;
    }

    public void setResetPasswordKey(String resetPasswordKey) {
        this.resetPasswordKey = resetPasswordKey;
    }

   /* public Set<SysAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<SysAuthority> authorities) {
        this.authorities = authorities;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SysUser user = (SysUser) o;

        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
    public static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new SysUser.AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while(var2.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    public static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 420L;

        public AuthorityComparator() {
        }

        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            return g2.getAuthority() == null?-1:(g1.getAuthority() == null?1:g1.getAuthority().compareTo(g2.getAuthority()));
        }
    }
    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + this.getId() +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", activated=" + activated +
                ", activationKey='" + activationKey + '\'' +
                ", resetPasswordKey='" + resetPasswordKey +
                '}';
    }
}
