package com.agrestina.controller;

import com.agrestina.domain.user.User;
import com.agrestina.dto.user.GetUserDTO;
import com.agrestina.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/active")
    public ResponseEntity<Page<GetUserDTO>> ListActiveUsers(Pageable pagination){
        var user = this.userService.listActive(pagination);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/inactive")
    public ResponseEntity<Page<GetUserDTO>> ListInactiveUsers(Pageable pagination){
        var user = this.userService.listInactive(pagination);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<GetUserDTO>> ListAllUsers(Pageable pagination){
        var user = this.userService.listAll(pagination);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("delete/{login}")
    @Transactional
    public ResponseEntity<GetUserDTO> delete(@PathVariable String login) {
        var user = this.userService.delete(login);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("activate/{login}")
    @Transactional
    public ResponseEntity<GetUserDTO> activate(@PathVariable String login) {
        var user = this.userService.activate(login);
        return ResponseEntity.ok(user);
    }
}