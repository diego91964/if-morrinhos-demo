package br.uaijug.social;

import br.uaijug.social.domain.Sessao;
import br.uaijug.social.domain.User;
import br.uaijug.social.service.SessaoService;
import br.uaijug.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

/**
 * Created by diego on 15/11/16.
 */
@RestController("/user")
public class UserRest {

    @Autowired
    private UserService userService;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private  HttpServletRequest request;


    @RequestMapping(method = RequestMethod.GET,
            value = "/cadastrarNovoUsuario/{nome}/{email}/{senha}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User cadastrarNovoUsuario (@PathVariable ("nome")  String nome,
                                      @PathVariable ("email") String email,
                                      @PathVariable ("senha") String senha){

        User user = new User(email,nome,senha);

        userService.insertUser(user);
        return user;
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/cadastrarFotoUsuario/{email}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String cadastrarFotoUsuario (@PathVariable ("token") String token, @QueryParam("urlFoto") String urlFoto){

        Sessao sessao = sessaoService.buscarSessaoPorToken(token);

        if (sessao == null)
            return "ERRO_USUARIO_NAO_AUTENTICADO";
        else {
            userService.adicionaFotoUsuario(sessao.getEmailUsuario(), urlFoto);
        }

        return urlFoto;
    }

    public Sessao login (@PathVariable ("email") String email, @QueryParam("senha") String senha){
        return userService.login(email,senha);
    }
}
