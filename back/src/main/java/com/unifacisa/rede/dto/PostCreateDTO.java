package com.unifacisa.rede.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDTO {

    private Long userId;
    private String message;
    private LocalDateTime creationDate;

}
