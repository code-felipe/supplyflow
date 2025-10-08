package com.custodia.supply.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.custodia.supply.email.service.IEmailService;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.user.service.IUserService;

@Controller
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private IUserService userService;

	private final JavaMailSender mailSender;

	@Autowired
	private IEmailService emailServiceHtml;

	public EmailController(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@GetMapping("/send-email")
	@ResponseBody
	public String sendEmail(@RequestParam Long requestId) {
		User user = userService.findOne(requestId);
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(user.getAssignedCustomer().getEmail()); // = spring.mail.username
			message.setTo(user.getAssignedCustomer().getEmail());
			message.setSubject("testing sending email");
			message.setText("Confirmation that the email it went throught");
			mailSender.send(message);
			// ResponseEntity help to check status?
			System.out.println("request id: " + requestId);
			return "Sent";
		} catch (Exception e) {
			return "Error";
		}
	}

	@PostMapping("/send-html-email")
    public String sendEmailWithHTML(@RequestParam Long requestId,
                                    RedirectAttributes flash) {

        boolean ok = emailServiceHtml.sendEmailWithHtml(
                "felipephez@gmail.com",
                "Java email with html",
                "email/email", // templates/email/email.html
                requestId
        );

        flash.addFlashAttribute(ok ? "success" : "error",
                ok ? "Email sent successfully" : "Fail to send email");

        return "redirect:/user/view/" + requestId;
    }

}
