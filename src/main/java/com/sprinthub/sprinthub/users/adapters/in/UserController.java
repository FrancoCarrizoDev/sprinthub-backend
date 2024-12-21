package com.sprinthub.sprinthub.users.adapters.in;

import com.sprinthub.sprinthub.users.application.dtos.CreateUserDto;
import com.sprinthub.sprinthub.users.application.usecases.CreateNewUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateNewUserUseCase createNewUserUseCase;

    public UserController(CreateNewUserUseCase createNewUserUseCase) {
        this.createNewUserUseCase = createNewUserUseCase;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDto user) {
        try {
            createNewUserUseCase.execute(user);
            return ResponseEntity.ok("User created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error saving user");
        }

    }

}
