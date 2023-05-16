package com.example.security.repository.model;

import com.example.security.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//@ApiModel(value = "Model of authentication credential")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User {
//    @ApiModelProperty(value = "The unique id of user")

    private Long id;
//    @ApiModelProperty(value = "The unique username")

    private String email;
//    @ApiModelProperty(value = "User password")

//    private String password;
//    @ApiModelProperty(value = "The role of user")

    private Role role;
}
