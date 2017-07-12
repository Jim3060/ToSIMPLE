package model;

import java.util.Date;

public class Report {
	private long id;
	private long userId;
	private String questionnaireId;
	private Date createTime;
	private int status;
	private String content;
	
	public Report(){;}
	public Report(long id, long userId, String questionnaireId, Date createTime, int status, String content) {
		this.id = id;
		this.userId = userId;
		this.questionnaireId = questionnaireId;
		this.createTime = createTime;
		this.status = status;
		this.content = content;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getQuestionnaireId() {
		return questionnaireId;
	}
	public void setQuestionnaireId(String questionnaireId) {
		this.questionnaireId = questionnaireId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
