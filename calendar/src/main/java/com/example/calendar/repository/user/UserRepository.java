package com.example.calendar.repository.user;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
