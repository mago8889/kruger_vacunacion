package com.kruger.security;

import java.util.List;

import com.kruger.model.Rol;



public class JwtResponse {
	private String token;
    private String type = "Bearer";
    private long idUser;
    private List<Rol> roles;
    
	public JwtResponse(String accessToken, List<Rol> roles, Long idUser) {
        this.token = accessToken;
        this.idUser = idUser;
        this.roles = roles;
    }

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

}
