package es.uc3m.tiw.controller;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;

import es.uc3m.tiw.entity.*;
import es.uc3m.tiw.repository.UserDAO;

@Controller
@CrossOrigin
public class UserController {

	@Autowired
	UserDAO daous;


	// ----------------- GET ALL USERS ----------------------
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<User>> getUsers(@RequestParam(value = "name", required = false) String name) {
		List<User> userList;
		userList = daous.findAll();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}


	// ----------------- GET USER ----------------------
	@RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> getUserByIduser(@PathVariable String username) {
		User us = daous.findByUsername(username);
		if (us == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(us, HttpStatus.OK);
		}
	}


	// ----------------- SAVE USER ----------------------
	@PostMapping("/users")
	public ResponseEntity<User> saveUser(@RequestBody User puser) {
		ResponseEntity<User> response;
		User us = daous.findByUsername(puser.getUsername());

		if (us != null) {
			response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
			return response;
		}

		User newUser = daous.save(puser);
		if (newUser == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			response = new ResponseEntity<>(newUser, HttpStatus.CREATED);
		}
		return response;
	}

	// ----------------- UPDATE USER ----------------------
	@RequestMapping(value="/users/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<User> updateUser(@PathVariable @Validated Long id, @RequestBody User pUser) {
		ResponseEntity<User> response;
		User us = daous.findById(id).orElse(null);
		if (us == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (!pUser.getUsername().equals("")){
				User usr = daous.findByUsername(pUser.getUsername());
				if (usr != null) {
					response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
					return response;
				}
				us.setUsername(pUser.getUsername());
			}
			if (!pUser.getName().equals("")){
				us.setName(pUser.getName());
			}
			if (!pUser.getPassword().equals("")){
				us.setPassword(pUser.getPassword());
			}
			if (!pUser.getAddress().equals("")){
				us.setAddress(pUser.getAddress());
			}
			if (!pUser.getPhone().equals("")){
				us.setPhone(pUser.getPhone());
			}
			response = new ResponseEntity<>(daous.save(us), HttpStatus.OK);
		}
		return response;
	}

	// ----------------- DELETE USER ----------------------
	@RequestMapping(value="/users/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<User> deleteUser(@PathVariable @Validated Long id) {
		ResponseEntity<User> response;
		User us = daous.findById(id).orElse(null);
		if (us == null) {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			daous.delete(us);
			response = new ResponseEntity<>(HttpStatus.OK);
		}
		return response;

	}

}