package es.uc3m.tiw.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "mensajes")
@JsonPropertyOrder({"emailori", "emaildest","mensaje"})
public class Message implements Serializable{

	@Id
	private String id;

	private String emailori;

    private String emaildest;

    private String mensaje;

    private long time;

	public Message() {

	}

	@PersistenceConstructor
	public Message(String emailori, String emaildest, String mensaje, long time) {
		this.emailori = emailori;
		this.emaildest = emaildest;
		this.mensaje = mensaje;
		this.time = time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmailori() {
		return emailori;
	}

	public void setEmailori(String emailori) {
		this.emailori = emailori;
	}

	public String getEmaildest() {
		return emaildest;
	}

	public void setEmaildest(String emaildest) {
		this.emaildest = emaildest;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Mensaje [emailori=" + emailori + ", emaildest=" + emaildest + ", mensaje=" + mensaje
				+ "]";
	}

}
