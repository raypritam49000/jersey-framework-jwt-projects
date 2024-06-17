package org.santanubrains.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.santanubrains.utiliy.PasswordUtils;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "T_USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL_ID", unique = true)
	private String emailId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "COMPANY")
	private String company;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
	private List<String> roles = new ArrayList<>();

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", updatable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "CREATED_TIME", updatable = false)
	private Date createdTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "LAST_UPDATED_TIME")
	private Date lastUpdatedTime;

	public User() {
		super();
	}

	public User(Long id,String firstName, String lastName, String emailId, String company,
			List<String> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.company = company;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = PasswordUtils.digestPassword(password);
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}
