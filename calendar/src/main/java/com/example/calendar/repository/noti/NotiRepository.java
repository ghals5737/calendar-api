package com.example.calendar.repository.noti;

import com.example.calendar.domain.noti.Noti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotiRepository extends JpaRepository<Noti, Long> {
    Optional<Noti> findByReceiveUserId(Long receiveUserId);
}
