package uz.salikhdev.google_lms.service.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.salikhdev.google_lms.domain.dto.request.HomeWorkNotificationRequest;
import uz.salikhdev.google_lms.domain.dto.request.TeacherNotificationRequest;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    //@Value("${spring.mail.enable}")
    private final Boolean isEnable = true;
    @Qualifier("sender")
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, Long> redisTemplate;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    public void sendOtpCode(String toEmail, Long code) {
        redisTemplate.opsForValue().set(toEmail, code, 3, TimeUnit.MINUTES);

        if (!isEnable) {
            log.info("Email sending is disabled. To: {}, Code: {}", toEmail, code);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Verificaiton Code");
            String text = "Your verification code is: " + code + "\n" +
                    "This code is valid for 3 minutes.\n" +
                    "If you did not request this code, please ignore this email.";
            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendNotificationForHomework(String toEmail, HomeWorkNotificationRequest request) {
        if (!isEnable) {
            log.info("Email sending is disabled. To: {}, notification: {}", toEmail, request);
            return;
        }

        String formattedDeadline = request.deadline().format(formatter);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Homework Notification");
            String text =
                    "Dear " + request.firstName() + " " + request.lastName() + ",\n\n" +

                            "A new homework has been assigned to you.\n\n" +

                            "📘 Group: " + request.groupName() + "\n" +
                            "📝 Homework: " + request.homeWorkTitle() + "\n" +
                            "👨‍🏫 Teacher: " + request.teacherName() + "\n" +
                            "⏰ Deadline: " + formattedDeadline + "\n\n" +

                            "Please make sure to submit your homework before the deadline.\n\n" +

                            "If you have any questions, contact your teacher.\n\n" +

                            "Best regards,\n" +
                            "Learning Management System";

            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendNotificationForTeacher(String toEmail, TeacherNotificationRequest request) {
        if (!isEnable) {
            log.info("Email sending is disabled. To: {}, notification: {}", toEmail, request);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("New Group Assignment");

            String text =
                    "Dear " + request.firstName() + " " + request.lastName() + ",\n\n" +

                            "You have been assigned to a new group.\n\n" +

                            "👥 Group Name: " + request.groupName() + "\n" +
                            "📄 Group NUMBER: " + request.groupNumber() + "\n\n" +

                            "Please check your dashboard for more details.\n\n" +

                            "Best regards,\n" +
                            "Learning Management System";

            message.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", toEmail, e.getMessage());
        }
    }

}
