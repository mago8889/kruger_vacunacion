package com.kruger.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kruger.model.Empleados;

public class UserPrinciple implements UserDetails {
	
	    private static final long serialVersionUID = 1L;
	 
	    private Long id;
	    
	    private int cedula;
	    
	    private String name;
	 
	    private String username;
	 
	    private String email;
	 
	    @JsonIgnore
	    private String password;
	 
	    private Collection<? extends GrantedAuthority> authorities;
	 
	    public UserPrinciple(Long id, int cedula, String name, 
	              String email, String password, 
	              Collection<? extends GrantedAuthority> authorities) {
	        this.id = id;
	        this.cedula = cedula;
	        this.name = name;
	        this.email = email;
	        this.password = password;
	        this.authorities = authorities;
	    }
	 
	    public static UserPrinciple build(Empleados user) {
	    	
	        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> 
            new SimpleGrantedAuthority(role.getNombre())
	        		).collect(Collectors.toList());
	 
	        return new UserPrinciple(
	                user.getIdEmpleado(),
	                user.getCedula(),
	                user.getNombres(),
	                user.getCorreo(),
	                user.getClave(),
	                authorities
	        );
	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

}
