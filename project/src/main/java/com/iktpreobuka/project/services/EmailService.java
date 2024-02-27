package com.iktpreobuka.project.services;

import com.iktpreobuka.project.models.EmailObject;

public interface EmailService {
	void sendSimpleMessage (EmailObject object);
	void sendTemplateMessage (EmailObject object) throws
	Exception;
	void sendMessageWithAttachment (EmailObject object,
	String pathToAttachment) throws Exception;
	void sendVoucherEmail(String to, String subject, String content);
}
