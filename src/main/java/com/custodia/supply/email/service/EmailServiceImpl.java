package com.custodia.supply.email.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;

import com.custodia.supply.email.controller.EmailController;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.service.IUserService;
import com.custodia.supply.validation.exception.EmailSendException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	private IUserService userService;

	@Autowired
	private JavaMailSender mailSender;

	private final SpringTemplateEngine templateEngine;
	private final LocalDateTime currentDateTime = LocalDateTime.now();

	@Value("${spring.mail.username}")
	private String fromAddress;

	public EmailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	@Override
    public Boolean sendEmailWithHtml(String to, String subject, String templateName, Long id) {
			User u = userService.findOne(id);
			Request r = userService.fetchRequestByIdWithUserWithRequestItemWithSupplyItem(id);
        try {
            // 1) Render Thymeleaf
            Context ctx = new Context();
            ctx.setVariable("title", "Weekly Supply Item Request");   // <h1 th:text="${title}">
            ctx.setVariable("year", Year.now().getValue());
            ctx.setVariable("shipTo", u.getAssignedSite().getAddress());
            ctx.setVariable("items", r.getRequests());
            ctx.setVariable("requests", u.getRequests());
            ctx.setVariable("date", this.dateFormater(currentDateTime));
            
           
//            ctx.setVariable("date", new Date());
            // i can use de object with request.user.firstName en la vista:
            // ctx.setVariable("request", requestService.findOne(requestId));

            String html = templateEngine.process(templateName, ctx);

            // 2) MimeMessage en MULTIPART (necesario para addInline)
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                new MimeMessageHelper(mime, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            // 3) Logo inline por classpath (mejor que ruta absoluta en C:\…)
            // Coloca la imagen en: src/main/resources/static/mail/logo.png
            ClassPathResource logo = new ClassPathResource("static/mail/logo.png");
            helper.addInline("logo", logo, "image/png"); // <-- el CID es "logo" (coincide con cid:logo)

            mailSender.send(mime);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
	
	private String dateFormater(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
        
	}

}
