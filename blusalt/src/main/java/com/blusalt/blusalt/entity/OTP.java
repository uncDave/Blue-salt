package com.blusalt.blusalt.entity;

import com.blusalt.blusalt.utils.AttributeEncryptor;
import jakarta.persistence.*;
import lombok.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp")
public class OTP extends BaseEntity {
    private Instant expiry;
    @Convert(converter = AttributeEncryptor.class)
    private String confirmationToken;
    private String email;
    private String purpose;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private boolean expired;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id")
    private BaseUser baseUser;

    public OTP(BaseUser baseUser, String purpose) {
        this.baseUser = baseUser;
        this.email = baseUser.getEmail();
        createdDate = new Date();
        this.purpose = purpose;
        this.expired = false;
        this.expiry = Instant.now().plus(Duration.ofSeconds(300));
        confirmationToken = generateNumericOTP();
    }
    private String generateNumericOTP() {
        Random random = new Random();
        int otpLength = 6;
        StringBuilder otpBuilder = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            int digit = random.nextInt(10);
            otpBuilder.append(digit);
        }

        return otpBuilder.toString();
    }
}
