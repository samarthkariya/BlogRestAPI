package com.samarth.blog.payloads;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String jwtToken;
}
