package ua.banan.data.model;

public class Comment 
{
	public String comment;
	public String mail;
	public String name;
	public String phone;
	
	public Comment() {}
	
	public Comment(String comment, String mail, String name) {
		super();
		this.comment = comment;
		this.mail = mail;
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
