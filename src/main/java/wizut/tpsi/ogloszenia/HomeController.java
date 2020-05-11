/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wizut.tpsi.ogloszenia.jpa.Offer;
import wizut.tpsi.ogloszenia.services.OffersService;
import wizut.tpsi.ogloszenia.web.OfferFilter;

/**
 *
 * @author naeri
 */
@Controller
public class HomeController {

    @Autowired
    private OffersService offersService;

    @RequestMapping("/")
    public String home(Model model, OfferFilter offerFilter) {
        List<CarManufacturer> carManufacturers = offersService.getCarManufacturers();
        List<CarModel> carModels = null;
        if (offerFilter.getManufacturerId() != null) {
            carModels = offersService.getCarModels(offerFilter.getManufacturerId());
        }
        List<FuelType> fuelTypes = offersService.getFuelTypes();
        List<Offer> offers = offersService.getOffers(offerFilter);

        model.addAttribute("carManufacturers", carManufacturers);
        model.addAttribute("offers", offers);
        model.addAttribute("carModels", carModels);
        model.addAttribute("fuelTypes", fuelTypes);
        return "offersList";
    }

    @GetMapping("/offer/{id}")
    public String offerDetails(Model model, @PathVariable("id") Integer id) {
        Offer offer = offersService.getOffer(id);
        model.addAttribute("offer", offer);
        return "offerDetails";
    }

    @GetMapping("/newoffer")
    public String newOfferForm(Model model, Offer offer) {
        List<CarModel> carModels = offersService.getCarModels();
        List<BodyStyle> bodyStyles = offersService.getBodyStyles();
        List<FuelType> fuelTypes = offersService.getFuelTypes();

        model.addAttribute("carModels", carModels);
        model.addAttribute("bodyStyles", bodyStyles);
        model.addAttribute("fuelTypes", fuelTypes);
        model.addAttribute("header", "Nowe ogłoszenie");
        model.addAttribute("action", "/newoffer");
        return "offerForm";

    }

    @PostMapping("/newoffer")
    public String saveNewOffer(Model model, @Valid Offer offer, BindingResult binding) {
        if (binding.hasErrors()) {
            List<CarModel> carModels = offersService.getCarModels();
            List<BodyStyle> bodyStyles = offersService.getBodyStyles();
            List<FuelType> fuelTypes = offersService.getFuelTypes();

            model.addAttribute("carModels", carModels);
            model.addAttribute("bodyStyles", bodyStyles);
            model.addAttribute("fuelTypes", fuelTypes);

            return "offerForm";
        }
        model.addAttribute("header", "Nowe ogłoszenie");
        model.addAttribute("action", "/newoffer");
        offer = offersService.createOffer(offer);

        return "redirect:/offer/" + offer.getId();
    }

    @GetMapping("/deleteoffer/{id}")
    public String deleteOffer(Model model, @PathVariable("id") Integer id) {
        Offer offer = offersService.deleteOffer(id);

        model.addAttribute("offer", offer);
        return "deleteOffer";
    }

    @GetMapping("/editoffer/{id}")
    public String editOffer(Model model, @PathVariable("id") Integer id) {
        List<CarModel> carModels = offersService.getCarModels();
        List<BodyStyle> bodyStyles = offersService.getBodyStyles();
        List<FuelType> fuelTypes = offersService.getFuelTypes();
        Offer offer = offersService.getOffer(id);

        model.addAttribute("carModels", carModels);
        model.addAttribute("bodyStyles", bodyStyles);
        model.addAttribute("fuelTypes", fuelTypes);
        model.addAttribute("header", "Edycja ogłoszenia");
        model.addAttribute("action", "/editoffer/" + id);
        model.addAttribute("offer", offer);
        return "offerForm";
    }

    @PostMapping("/editoffer/{id}")
    public String saveEditedOffer(Model model, @PathVariable("id") Integer id, @Valid Offer offer, BindingResult binding) {
        if (binding.hasErrors()) {
            model.addAttribute("header", "Edycja ogłoszenia");
            model.addAttribute("action", "/editoffer/" + id);

            List<CarModel> carModels = offersService.getCarModels();
            List<BodyStyle> bodyStyles = offersService.getBodyStyles();
            List<FuelType> fuelTypes = offersService.getFuelTypes();

            model.addAttribute("carModels", carModels);
            model.addAttribute("bodyStyles", bodyStyles);
            model.addAttribute("fuelTypes", fuelTypes);
            model.addAttribute("header", "Edycja ogłoszenia");
            model.addAttribute("action", "/editoffer/" + id);
            model.addAttribute("offer", offer);

            return "offerForm";
        }

        offersService.saveOffer(offer);

        return "redirect:/offer/" + offer.getId();
    }
}
