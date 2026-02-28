package HotWhellShop_Spring_react.controller;

import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import HotWhellShop_Spring_react.domain.ResultPaginationDTO;
import HotWhellShop_Spring_react.domain.User;
import HotWhellShop_Spring_react.domain.DTO.ResGetUserByidDTO;
import HotWhellShop_Spring_react.domain.DTO.ResUpdateUserDTO;
import HotWhellShop_Spring_react.domain.DTO.UserDTO;
import HotWhellShop_Spring_react.service.UserService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public ResponseEntity<UserDTO> CreateUser(@Valid @RequestBody User reqUser) {
        String passwordEncode = this.passwordEncoder.encode(reqUser.getPassword());
        reqUser.setPassword(passwordEncode);
        User hiu = this.userService.handleCreateUser(reqUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.handleConverCreateUserDTO(hiu));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ResGetUserByidDTO> GetUserById(@PathVariable long id) {
        User hiu = this.userService.handleGetUserById(id);
        return ResponseEntity.ok().body(this.userService.handleConverGetUserByIdDTO(hiu));

    }

    @GetMapping("/user")
    public ResponseEntity<ResultPaginationDTO> GetAllUser(@Filter Specification<User> spec, Pageable pageable) {
        ResultPaginationDTO data = this.userService.handleGetAllUser(spec, pageable);
        return ResponseEntity.ok().body(data);
    }

    @PutMapping("/user")
    public ResponseEntity<ResUpdateUserDTO> UpdateUser(@RequestBody User user) {
        User hiu = this.userService.handleUpdateUser(user);
        return ResponseEntity.ok().body(this.userService.handleConverUpdateUserDTO(hiu));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> DeleteUserById(@PathVariable long id) {
        this.userService.handleDeleteUserByid(id);
        return ResponseEntity.ok("delete success");
    }
}
