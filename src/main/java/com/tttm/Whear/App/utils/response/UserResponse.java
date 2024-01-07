package com.tttm.Whear.App.utils.response;

import com.tttm.Whear.App.enums.ERole;
import com.tttm.Whear.App.enums.Language;
import com.tttm.Whear.App.enums.StatusGeneral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;

    private String password;

    private Date dateOfBirth;

    private String phone;

    private String email;

    private Boolean gender;

    private ERole role;

    private String imgUrl;

    private StatusGeneral status;

    private Language language;

}
