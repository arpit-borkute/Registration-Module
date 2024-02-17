package in.bankbenchers.registration.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USER_REG")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer userId;

	@Column(name = "email_id", nullable = false, unique = true)
	private String email;

	@Column(name = "Mobile_no", nullable = false, unique = true)
	@Length(min = 10)
	private String mobileNumber;

	@Column(name = "User_name", nullable = false, unique = true)
	private String userName;

	@Column(name = "account_locked")
	private boolean accountLocked;

	@Column(name = "locker_at")
	private Date lockedAt;

}
