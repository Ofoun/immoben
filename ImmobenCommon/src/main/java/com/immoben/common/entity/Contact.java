package com.immoben.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact extends IdBasedEntity {
	
	@Column(nullable = false)
	private String fullname;

	@Column(nullable = false)
	private String email;

	@Column()
	private String phone;

	@Column()
	private String subject;

	@Column(nullable = false)
	private String content;
	

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Contact [fullname=" + fullname + ", email=" + email + ", phone=" + phone + ", subject=" + subject
				+ ", content=" + content + "]";
	}


	
	
	

}
