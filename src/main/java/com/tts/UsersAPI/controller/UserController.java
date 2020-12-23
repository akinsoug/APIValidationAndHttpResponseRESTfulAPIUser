package com.tts.UsersAPI.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.UsersAPI.model.User;
import com.tts.UsersAPI.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	UserRepository repository; 
	
	

//	@GetMapping("/users")
//	public List<User> getUsersByState(@RequestParam(value = "state", required = false) String state) {
//		if (state != null) {
//			return (List<User>) repository.findByState(state);
//		}
//		return (List<User>) repository.findAll();
//	}

	/**
	 * 
	 * @param state
	 * @return
	 */
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value = "state", required = false) @Valid String state) {
		if (state != null) {
			return  new ResponseEntity<>((List<User>) repository.findByState(state), HttpStatus.OK);
		}
		return  new ResponseEntity<>((List<User>) repository.findAll(), HttpStatus.OK); // 
	} //public ResponseEntity<List<User>> getUsers(@RequestParam(value = "state", required = false) @Valid String state, BindingResult bindingResult) {

	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		repository.save(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
 
	@PutMapping("/users/{id}")
	public ResponseEntity<Void>  updateUser(@PathVariable(value = "id") Long id, @RequestBody @Valid User user, BindingResult bindingResult) {
		Optional<User> user2 = repository.findById(id);
		System.out.println("\n\n user: \t" + user + "\t user2: \t" + user2);
		
		if (!user2.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else if (bindingResult.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		repository.save(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * 
	 * @param id
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable(value = "id") Long id) {
		Optional<User> user2 = repository.findById(id);
		if (!user2.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
//		if (!user.isPresent()) {
//			
//		}
		
		return new ResponseEntity<>(user2, HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long id) {

		Optional<User> user2 = repository.findById(id);
		if (!user2.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		repository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}