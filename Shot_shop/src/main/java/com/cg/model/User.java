package com.cg.model;

import com.cg.model.dto.UserDTO;
import com.cg.model.dto.UserUpdateDTO;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String address;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    public UserDTO toUserDTO () {
        return new UserDTO()
                .setId(id)
                .setUsername(username)
                .setFullName(fullName)
                .setAddress(address)
                .setPhone(phone)
                .setRole(role.toRoleDTO());
    }

    public UserUpdateDTO toUserUpdateDTO(){
        return new UserUpdateDTO()
                .setId(id)
                .setUsername(username)
                .setFullName(fullName)
                .setAddress(address)
                .setPhone(phone)
                .setRole(role.toRoleDTO());
    }
}
