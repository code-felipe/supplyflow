package com.custodia.supply.invitation.entity;

import java.io.Serializable;
import java.util.Date;

import com.custodia.supply.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "invitation_codes")
public class InvitationCode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	@Column(name = "is_used")
	private Boolean isUsed;// its sets once the new user is succesfully registrate
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)// it was false
	@JoinColumn(name = "created_by_user_id", nullable = false)
	private User createdBy; //create by the admin. is set once the code is generate
	
	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "used_by_user_id", unique = true)
	private User isUsedBy; //the code was used by
	
	@Column(name = "create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;//code create by that date.

	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createByUser) {
		this.createdBy = createByUser;
	}

	public User getIsUsedBy() {
		return isUsedBy;
	}

	public void setIsUsedBy(User isUsedBy) {
		this.isUsedBy = isUsedBy;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
		
	
}
