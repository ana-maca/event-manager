package com.example.eventmanager.controller;

import com.example.eventmanager.model.User;
import com.example.eventmanager.service.UserService;
import com.example.eventmanager.util.BrowserRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Object getAllUsers(Model model, HttpServletRequest request) {
        if (BrowserRequestUtils.prefersHtml(request)) {
            model.addAttribute("users", userService.getAllUsers());
            return "index";
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public Object getUserById(@PathVariable Long id, Model model, HttpServletRequest request) {
        if (BrowserRequestUtils.prefersHtml(request)) {
            model.addAttribute("user", userService.getUserById(id));
            return "index";
        }
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}