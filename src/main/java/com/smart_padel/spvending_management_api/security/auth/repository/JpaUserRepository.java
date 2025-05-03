package com.smart_padel.spvending_management_api.security.auth.repository;
import com.smart_padel.spvending_management_api.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface JpaUserRepository extends JpaRepository<User, Integer> { Optional<User> findByUsername(String username);}
