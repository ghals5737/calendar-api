package com.example.calendar.domain.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendId implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mainUserId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subUserId;
}
