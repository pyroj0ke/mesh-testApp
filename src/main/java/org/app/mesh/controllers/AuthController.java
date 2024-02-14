package org.app.mesh.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.app.mesh.dto.AuthenticationRequest;
import org.app.mesh.dto.AuthenticationResponse;
import org.app.mesh.services.JwtUtil;
import org.app.mesh.services.MeshUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final MeshUserDetail meshUserDetail;

    @PostMapping("/authenticate")
    @Operation(summary = "Аутентификация и формирование JWT в теле отклика")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect email or password");
        }

        final UserDetails userDetails = meshUserDetail.loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping({ "/hello" })
    @Operation(summary = "изначально защищенная тестовая точка для проверки корректности работы JWT")
    @SecurityRequirement(name = "JWT")
    public String firstPage() {
        return "Hello World";
    }
}
