package com.lpnu.excursionclient.security;

public record JwtPair(Jwt accessJwt, Jwt refreshJwt) {
}
