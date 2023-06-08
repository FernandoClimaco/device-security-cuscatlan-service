package com.device.security.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType; 


@Entity
@Table(name = "user")
public class User implements Serializable {
	
	   private static final long serialVersionUID = 1L;
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "iduser", nullable = false)
	    private Integer iduser;
	    @Basic(optional = false)
	    @Column(name = "name", nullable = false, length = 100)
	    private String name;
	    @Basic(optional = false)
	    @Column(name = "user", nullable = false, length = 45)
	    private String username;
	    @Basic(optional = false)
	    @Column(name = "password", nullable = false, length = 600)
	    private String password;
	    @Basic(optional = false)
	    @Column(name = "identification", nullable = false, length = 45)
	    private String identification;
	    @Basic(optional = false)
	    @Column(name = "address", nullable = false, length = 45)
	    private String address;
	    @Column(name = "country", nullable = false, length = 45)
	    private String country;
	    @Column(name = "phone", nullable = false, length = 45)
	    private String phone;
	    @Column(name = "email", nullable = false, length = 45)
	    private String email; 
	    @Basic(optional = false)
	    @Column(name = "created", nullable = false)
	    @Temporal(TemporalType.DATE)
	    private Date created;
	    
	    
	    
	    public User() {
	    }

	    public User(Integer iduser) {
	        this.iduser = iduser;
	    }

	    

	    public User(Integer iduser, String name, String username, String password, String identification,
				String address, String country, String phone, String email, Date created) {
			super();
			this.iduser = iduser;
			this.name = name;
			this.username = username;
			this.password = password;
			this.identification = identification;
			this.address = address;
			this.country = country;
			this.phone = phone;
			this.email = email;
			this.created = created;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Integer getIduser() {
	        return iduser;
	    }

	    public void setIduser(Integer iduser) {
	        this.iduser = iduser;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getUser() {
	        return username;
	    }

	    public void setUser(String user) {
	        this.username = user;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getIdentification() {
	        return identification;
	    }

	    public void setIdentification(String identification) {
	        this.identification = identification;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public Date getCreated() {
	        return created;
	    }

	    public void setCreated(Date created) {
	        this.created = created;
	    }

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	    
	    
	   
}
