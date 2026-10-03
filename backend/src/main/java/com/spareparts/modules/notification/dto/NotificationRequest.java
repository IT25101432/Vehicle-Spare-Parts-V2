package com.spareparts.modules.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NotificationRequest {

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    private String recipient;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message body is required")
    private String message;

    public NotificationRequest() {}

    public NotificationRequest(String recipient, String subject, String message) {
        this.recipient = recipient; this.subject = subject; this.message = message;
    }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public static NotificationRequestBuilder builder() { return new NotificationRequestBuilder(); }

    public static class NotificationRequestBuilder {
        private String recipient, subject, message;
        public NotificationRequestBuilder recipient(String v) { this.recipient = v; return this; }
        public NotificationRequestBuilder subject(String v) { this.subject = v; return this; }
        public NotificationRequestBuilder message(String v) { this.message = v; return this; }
        public NotificationRequest build() { return new NotificationRequest(recipient, subject, message); }
    }
}