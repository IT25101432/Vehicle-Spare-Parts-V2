package com.spareparts.modules.notification.service;

import com.spareparts.modules.notification.dto.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processNotification(NotificationRequest request) {
        log.info("Processing notification request for recipient: {}", request.getRecipient());
        emailService.sendSimpleEmail(
                request.getRecipient(),
                request.getSubject(),
                request.getMessage()
        );
    }
}