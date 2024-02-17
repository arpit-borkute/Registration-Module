package in.bankbenchers.registration.entity;

public class OTPDetails {

	private String otp;
	private long timestamp;
	private int incorrectAttempts;

	public OTPDetails(String otp, long timestamp) {
		this.otp = otp;
		this.timestamp = timestamp;
		this.incorrectAttempts = 0;
	}

	public String getOtp() {
		return otp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getIncorrectAttempts() {
		return incorrectAttempts;
	}

	public void incrementAttempts() {
		this.incorrectAttempts++;
	}

}
