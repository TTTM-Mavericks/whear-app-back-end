package com.tttm.Whear.App.utils.request;

import com.tttm.Whear.App.enums.ERole;
import com.tttm.Whear.App.enums.Language;
import com.tttm.Whear.App.enums.StatusGeneral;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String username;

    private String password;

    private Date dateOfBirth;

    private String phone;

    private String email;

    private Boolean gender;

    private String imgUrl;

    private Language language;
}
