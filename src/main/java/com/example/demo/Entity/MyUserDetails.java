package com.example.demo.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
   Users users;
   
   public MyUserDetails(Users users) {
	   this.users=users;
   }
   public Users getUsers() {
	return users;
}
public void setUsers(Users users) {
	this.users = users;
}
@Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
       Set<Role> roles = users.getRoles();
       List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
       for (Role role : roles) {
           authorities.add(new SimpleGrantedAuthority(role.getRole()));
       }
        
       return authorities;
   }

   @Override
   public String getPassword() {
       return users.getPassword();
   }

   @Override
   public String getUsername() {
       return users.getUsername();
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
       return users.isEnable();
   }
}
