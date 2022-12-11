package es.uc3m.tiw.controller;

import es.uc3m.tiw.entity.Message;
import es.uc3m.tiw.repository.ChatDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@CrossOrigin
public class ChatController {

	@Autowired
	ChatDAO daocha;


	// ----------------- SEND MESSAGE ----------------------
	@RequestMapping(value = "/mensaje", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ResponseEntity<List<Message>> sendMensaje(@RequestBody Message mensaje) {

		try {
			mensaje.setTime(System.currentTimeMillis());
			Message newMessage = daocha.save(mensaje);
			if (newMessage == null){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else{
				String emailori = mensaje.getEmailori();
				String emaildest = mensaje.getEmaildest();

				List<Message> convsSent = daocha.findByEmailoriAndEmaildest(emailori, emaildest);
				List<Message> convsRcv = daocha.findByEmailoriAndEmaildest(emaildest, emailori);
				convsSent.addAll(convsRcv);
				convsSent.sort(Comparator.comparing(Message::getTime));

				return new ResponseEntity<>(convsSent, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// ----------------- REQUEST OPENED CHATS FOM A USER ----------------------
	@RequestMapping(value = "/chats/{emailori}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<List<String>> getChats(@PathVariable(value = "emailori") String emailori){
        try{
            List<Message> all_messages = daocha.findByEmailori(emailori);
			List<Message> all_messages_des = daocha.findByEmaildest(emailori);
			all_messages.addAll(all_messages_des);
            List<String> all_users = new ArrayList<>();
            for (int i = 0; i< all_messages.size(); i++) {
                String emailori2 = all_messages.get(i).getEmailori();
                String emaildest2 = all_messages.get(i).getEmaildest();
                if(emailori2.equals(emailori)) {
                    all_users.add(emaildest2);
                }else if(emaildest2.equals(emailori)) {
                    all_users.add(emailori2);
                }
            }
            List<String> diffUsers= new ArrayList<>();
            for (int i=0; i<all_users.size();i++) {
                if(diffUsers.contains(all_users.get(i))) {

                }else {
                    diffUsers.add(all_users.get(i));
                }
            }

		    return new ResponseEntity<>(diffUsers, HttpStatus.OK);

        } catch (Exception e) {

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // ----------------- REQUEST CHAT BETWEEN TWO USERS ----------------------
	@RequestMapping(value = "/conversaciones/{emailori}/{emaildest}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody ResponseEntity<List<Message>> getConversaciones(@PathVariable(value = "emailori") String emailori,
																		 @PathVariable(value = "emaildest") String emaildest) {

		try {

			List<Message> convsSent = daocha.findByEmailoriAndEmaildest(emailori, emaildest);
			List<Message> convs_rcv = daocha.findByEmailoriAndEmaildest(emaildest, emailori);
			convsSent.addAll(convs_rcv);
			convsSent.sort(Comparator.comparing(Message::getTime));

		    return new ResponseEntity<>(convsSent, HttpStatus.OK);


		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}