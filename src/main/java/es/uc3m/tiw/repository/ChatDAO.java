package es.uc3m.tiw.repository;

import es.uc3m.tiw.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ChatDAO extends CrudRepository<Message, Long>{

	List<Message> findAll();

	@Query("SELECT u FROM Mensajes u WHERE (u.emailori=:email1 AND emaildest=:email2) OR (u.emailori=:email2 AND u.emaildest=:email1)")
	List<Message> findByEmailoriAndEmaildest(@Param("email1")String emailori, @Param("email2") String emaildest);

	List<Message> findByEmailori(@Param("email")String email);


	List<Message> findByEmaildest(@Param("email")String email);
}
