/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wizut.tpsi.ogloszenia.bean.Currentlog;
import wizut.tpsi.ogloszenia.services.OffersService;

/**
 *
 * @author naeri
 */
@Controller
public class UsersController {

    @Autowired
    private OffersService offersService;

    @Autowired
    private Currentlog Currentlog;

    @GetMapping("/login")
    public String log(Model model, Useruser useruser) {

        return "login";
    }

    @PostMapping("/login")
    public String login(Useruser userUser, Model model) {
        userUser = offersService.getUseruser(offersService.getuser(userUser).getId());
        if (userUser == null) {
            return "login";
        }
        Currentlog.setId(userUser.getId());
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public String logout() {

        Currentlog.setId(null);
        return "redirect:/";
    }
}
