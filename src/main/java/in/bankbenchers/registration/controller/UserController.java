package in.bankbenchers.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import in.bankbenchers.registration.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")

public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public String registerUser(@Valid @RequestParam String email, @RequestParam String mobileNumber,
			@RequestParam String userName) {
		return userService.registerUser(email, mobileNumber, userName);

	}

	@PostMapping("/login")
	public String userLogin(@RequestParam String email) {
		return userService.userLogin(email);
	}

	@PostMapping("/validateOtp")
	public String validateOTP(@RequestParam String email, @RequestParam String otp) {
		return userService.validateOTP(email, otp);
	}

}
