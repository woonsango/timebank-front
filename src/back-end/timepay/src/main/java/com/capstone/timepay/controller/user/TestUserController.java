package com.capstone.timepay.controller.user;

import com.capstone.timepay.domain.annotation.Response;
import com.capstone.timepay.service.user.dto.RegisterDTO;
import com.capstone.timepay.service.user.service.TestUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TestUserController
{
    private final TestUserService testUserService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Response<?> findAll() {
        return new Response<>("true", "조회 성공", testUserService.findAll());
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public Response<?> findUser(@PathVariable("id") Long id) {
        return new Response<>("true", "조회 성공", testUserService.findUser(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth")
    public Response<?> register(@RequestBody RegisterDTO registerDTO) {
        return new Response<>("true", "가입 성공", testUserService.register(registerDTO));
    }
}
