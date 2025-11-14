package com.unifacisa.rede.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String photo;
    private String message;
    private List<PostDTO> posts;
}
