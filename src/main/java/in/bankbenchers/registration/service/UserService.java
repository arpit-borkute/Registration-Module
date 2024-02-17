package in.bankbenchers.registration.service;

public interface UserService {

	String registerUser(String emailId, String mobileNumber, String userName);

	String userLogin(String email);

	String validateOTP(String email, String enteredOTP);
}
