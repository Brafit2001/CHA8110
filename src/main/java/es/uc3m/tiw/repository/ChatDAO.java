package es.uc3m.tiw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.rest.core.annotation.RestResource;

import es.uc3m.tiw.entity.Message;
import org.springframework.data.repository.query.Param;


public interface ChatDAO extends CrudRepository<Message, Long>{

	List<Message> findAll();

	@Query("SELECT u FROM Mensajes u WHERE (u.emailori=:email1 AND emaildest=:email2) OR (u.emailori=:email2 AND u.emaildest=:email1)")
	List<Message> findByEmailoriAndEmaildest(@Param("email1")String emailori, @Param("email2") String emaildest);

	@Query("SELECT u FROM Mensajes u WHERE u.emailori=:email OR u.emaildest=:email")
	List<Message> findByEmailori(@Param("email")String email);

}
