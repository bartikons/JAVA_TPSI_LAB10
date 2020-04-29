/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wizut.tpsi.ogloszenia.services.OffersService;

/**
 *
 * @author naeri
 */
@Controller
public class HomeController {
    @Autowired
    private OffersService offersService;
    
    @RequestMapping("/")
    public String home(Model model) {
        
        model.addAttribute("car1", offersService.getCarManufacturer(2));
        model.addAttribute("car2", offersService.getCarManufacturer(3));
        return "home";
    }

}
