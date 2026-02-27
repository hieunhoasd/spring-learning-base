package HotWhellShop_Spring_react.controller;

import org.springframework.web.bind.annotation.RestController;

import HotWhellShop_Spring_react.domain.User;
import HotWhellShop_Spring_react.service.UserService;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class UserController {
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/user")
    public ResponseEntity<User> CreateUser(@Valid @RequestBody User reqUser) {
        String passwordEncode = this.passwordEncoder.encode(reqUser.getPassword());
        reqUser.setPassword(passwordEncode);
        User hiu = this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(hiu);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> GetUserById(@PathVariable long id) {
        User hiu = this.userService.handleGetUserById(id);
        return ResponseEntity.ok(hiu);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> GetAllUser() {
        return ResponseEntity.ok(this.userService.handleGetAllUser());
    }

    @PutMapping("/user")
    public ResponseEntity<User> UpdateUser(@RequestBody User user) {
        User hiu = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok(hiu);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> DeleteUserById(@PathVariable long id) {
        this.userService.handleDeleteUserByid(id);
        return ResponseEntity.ok("delete success");
    }
}
