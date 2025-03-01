package com.fundooproject.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
@Data
public class RabbitMqDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String to;
	private String from;
	private String subject;
	private String body;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
