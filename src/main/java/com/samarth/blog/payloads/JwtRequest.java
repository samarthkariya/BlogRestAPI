package com.samarth.blog.payloads;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    private String username;
    private String password;
}
