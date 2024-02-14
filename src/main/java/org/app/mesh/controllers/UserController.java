package org.app.mesh.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.app.mesh.domains.User;
import org.app.mesh.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getbyphone/{phone}")
    @Operation(summary = "Поиск по телефону")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
        return ResponseEntity.ok().body(userService.getUserByPhone(phone));
    }

    @GetMapping("/getbyname")
    @Operation(summary = "Поиск по имени")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<User>> getUserByName(@RequestParam int page, @RequestParam int size, @RequestParam  String name) {
        return ResponseEntity.ok().body(userService.getUsersByName(page,  size,  name));
    }

    @GetMapping("/getbymail/{email}")
    @Operation(summary = "Поиск по почтовому адресу ")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<User> getUserByMail(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.getUserByMail(email));
    }

    @GetMapping("/getbybirthdate")
    @Operation(summary = "Поиск по дате рождения")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Page<User>> getUserByBirthdate(@RequestParam int page, @RequestParam int size, @RequestParam  @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate birthdate) {
        return ResponseEntity.ok().body(userService.getUsersByBirthdate(page,  size, birthdate));
    }

    @PostMapping("/moneytransfer")
    @Operation(summary = "Перевод средств между клиентами")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Boolean> moneyTransfer(@RequestParam long sourceId, @RequestParam long recipientId, @RequestParam  float amount) {
        return ResponseEntity.ok(userService.moneyTransfer(sourceId, recipientId, amount));
    }
}
