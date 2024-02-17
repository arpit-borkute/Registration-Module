package in.bankbenchers.registration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;

	public void sendOTP(String toEmail, String otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("Your OTP for Login");
		message.setText("Your OTP for Login is: " + otp);

		try {
			mailSender.send(message);
			System.out.println("OTP sent successfully to " + toEmail);
		} catch (Exception e) {
			System.out.println("Failed to send OTP to " + toEmail + ". Error: " + e.getMessage());
		}
	}

}
