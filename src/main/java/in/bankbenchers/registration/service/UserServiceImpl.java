package in.bankbenchers.registration.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.bankbenchers.registration.entity.OTPDetails;
import in.bankbenchers.registration.entity.User;
import in.bankbenchers.registration.repository.UserRepository;
import in.bankbenchers.registration.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailUtils emailUtils;

	private Map<String, OTPDetails> otpMap = new HashMap<>();

	private Random random = new Random();

	@Override
	public String registerUser(String email, String mobileNumber, String userName) {
		if (!isValidEmail(email)) {
			return "Invalid email format";
		}

		if (!isValidMobileNumber(mobileNumber)) {
			return "Invalid mobile number";
		}

		if (userRepo.findByEmail(email) != null) {
			return "User with this email already exists";
		}

		User newUser = new User();
		newUser.setEmail(email);
		newUser.setMobileNumber(mobileNumber);
		newUser.setUserName(userName);
		userRepo.save(newUser);

		return "User successfully registered";
	}

	public boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = java.util.regex.Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	public boolean isValidMobileNumber(String mobileNumber) {
		return mobileNumber != null && mobileNumber.matches("[6-9]\\d{9}");
	}

	@Override
	public String userLogin(String email) {
		User user = userRepo.findByEmail(email);

		if (user == null) {
			return "Email not found";
		}

		String otp = generateOTP(6);
		sendOTP(email, otp);

		otpMap.put(email, new OTPDetails(otp, System.currentTimeMillis()));

		return "OTP sent to your email";
	}

	private String generateOTP(int length) {

		String numbers = "0123456789";
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(numbers.length());
			sb.append(numbers.charAt(index));
		}
		return sb.toString();
	}

	private void sendOTP(String email, String otp) {
		System.out.println("Sending OTP " + " to email: " + email);

		emailUtils.sendOTP(email, otp);

	}

	@Override
	public String validateOTP(String email, String enteredOTP) {
		if (!otpMap.containsKey(email)) {
			return "Invalid email";
		}

		OTPDetails otpDetails = otpMap.get(email);
		String expectedOTP = otpDetails.getOtp();

		if (!isOTPValid(otpDetails)) {
			return "OTP is Expired";
		}
		if (enteredOTP.equals(expectedOTP)) {
			otpMap.remove(email);
			return "OTP validated successfully";
		} else {
			otpDetails.incrementAttempts();
			if (otpDetails.getIncorrectAttempts() > 3) {
				lockAccount(email);
				return "Account locked for 5 minutes due to multiple incorrect attempts";
			} else {
				return "Invalid OTP";
			}
		}
	}

	private boolean isOTPValid(OTPDetails otpDetails) {
		long currentTime = System.currentTimeMillis();
		long otpTime = otpDetails.getTimestamp();
		return (currentTime - otpTime) < 2 * 60 * 1000;// 1,20,000==2 min
	}

	private void lockAccount(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			user.setAccountLocked(true);
			user.setLockedAt(new Date());
			userRepo.save(user);
			System.out.println("Account locked for email: " + email);
		}

	}

}
