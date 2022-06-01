package com.example.demo.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class Role implements Serializable {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int RoleId;
     
     @Column
     private String role;
     
	public int getId() {
		return RoleId;
	}

	public void setId(int id) {
		this.RoleId = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
     
     
}
