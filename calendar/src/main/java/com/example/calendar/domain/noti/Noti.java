package com.example.calendar.domain.noti;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Noti {
    @Id
    @Column(name = "noti_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String responseYn;
    @Enumerated(EnumType.STRING)
    private NotiType notiType;
    private String useYn;
}
