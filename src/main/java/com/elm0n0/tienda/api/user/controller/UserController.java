package com.elm0n0.tienda.api.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elm0n0.tienda.api.user.exception.UserException;
import com.elm0n0.tienda.api.user.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	private static final String GET_BY_ID = "/get";
	private static final String POST_CREATE = "/create";
	private static final String PATH_MODIFY = "/modify";
	private static final String DELETE = "/delete";

	private final UserService userService;

	@GetMapping(GET_BY_ID)
	public ResponseEntity<Object> getUserById(@RequestParam String userId) {
		try {
			return ResponseEntity.ok(userService.getUserById(userId));
		} catch (UserException e) {
			return ResponseEntity.status(e.getMensaje().codigo()).body(e.getMensaje());
		}
	}
}
