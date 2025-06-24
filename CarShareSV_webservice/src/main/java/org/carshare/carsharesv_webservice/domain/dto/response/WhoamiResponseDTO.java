package org.carshare.carsharesv_webservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhoamiResponseDTO {
    private UUID userId;

    private String firstName;

    private String lastName;

    private String username;

    private LocalDate birthdate;

    private String phoneNumber;

    private String email;

    private Boolean active;

    private List<String> roles;
}
