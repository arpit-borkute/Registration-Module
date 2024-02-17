package in.bankbenchers.registration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.bankbenchers.registration.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);
}
