package br.uaijug.social;

import br.uaijug.social.domain.Post;
import br.uaijug.social.domain.User;
import br.uaijug.social.service.UserService;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SocialNetApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void deveInserirUsuarioCompleto() {
		User user = new User("email3@gmail.com","nome","senha");
		user.setUrlFoto("testeFotoUrl");
		userService.insertUser(user);
	}

	@Test
	public void deveInserirVariosUsuariosEPost (){

		String avatarPadrao = "http://lh6.ggpht.com/_qUxAU04uRNA/TFsP1isE_XI/AAAAAAAACVY/GRIpzNdIjGI/Facebook-batman-1.jpg";

		List<User> usuarios = new ArrayList<>();

		for (int i = 0; i < 5; i++){
			User user = new User(RandomStringUtils.random(10,true,false) + "@gmail.com", RandomStringUtils.random(5,true,false),"123456");
			user.setUrlFoto(avatarPadrao);

			userService.insertUser(user);
			for (int j = 0; j< 3 ; j++){
				Post post = new Post(new LoremIpsum().getParagraphs(2), new Date());
				userService.criaPost(user.getEmail(),post);
			}

			usuarios.add(user);
		}

		userService.amigo(usuarios.get(0).getEmail(),usuarios.get(2).getEmail());
		userService.amigo(usuarios.get(1).getEmail(),usuarios.get(3).getEmail());
	}

	@Ignore @Test
	public void deveInserirUsuario() {
		User user = new User("email1@gmail.com","nome","senha");
		userService.insertUser(user);
	}

	@Ignore @Test
	public void deveAdicionarEmail (){
		User user = new User("sajdasiodj@gmail.com","nome","senha");
		userService.insertUser(user);

		System.out.println("Inseriu");

		userService.adicionaFotoUsuario(user.getEmail(),"ulrFotoUsuario");
	}

}
