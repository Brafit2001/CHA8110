package es.uc3m.tiw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RestResource;

import es.uc3m.tiw.entity.User;


public interface UserDAO extends CrudRepository<User, Long>{

	public List<User> findAll();

	public List<User> findByName(String name);
	public User findByUsername(String username);
	
}
