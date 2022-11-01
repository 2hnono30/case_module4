package com.cg.model.dto;

import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserUpdateDTO {
    private Long id;

    @NotBlank(message = "The email is required")
    @Email(message = "The email address is invalid")
    @Size(max = 50, message = "The length of email must be between 5 and 50 characters")
    private String username;

    private String fullName;

    private String address;

    private String phone;

    @Valid
    private RoleDTO role;

    public User toUser() {
        return new User()
                .setId(id)
                .setUsername(username)
                .setFullName(fullName)
                .setAddress(address)
                .setPhone(phone)
                .setRole(role.toRole());
    }
}
