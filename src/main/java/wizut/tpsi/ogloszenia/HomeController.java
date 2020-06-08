/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wizut.tpsi.ogloszenia.bean.Currentlog;
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

    @Autowired
    private Currentlog Currentlog;

    @RequestMapping("/")
    public String home(Model model, OfferFilter offerFilter) {
        offerFilter.setMaxpage(offersService.mxpage(offerFilter));
        if (offerFilter.getSorte() == null) {
            offerFilter.setSorte("default");
        }

        String sorter = offerFilter.getSorte();
        Integer page = 0;

        if (offerFilter.getPage() != null) {
            page = offerFilter.getPage();
        } else {
            offerFilter.setPage(0);
        }

        List<CarManufacturer> carManufacturers = offersService.getCarManufacturers();
        List<CarModel> carModels = null;

        if (offerFilter.getManufacturerId() != null) {
            carModels = offersService.getCarModels(offerFilter.getManufacturerId());
        }
        List<FuelType> fuelTypes = offersService.getFuelTypes();
        List<Offer> offers = offersService.getOffers(offerFilter);

        model.addAttribute("idid", Currentlog.getId());
        model.addAttribute("sorter", sorter);
        model.addAttribute("page", page);
        model.addAttribute("maxpage", offerFilter.getMaxpage());
        model.addAttribute("carManufacturers", carManufacturers);
        model.addAttribute("offers", offers);
        model.addAttribute("carModels", carModels);
        model.addAttribute("fuelTypes", fuelTypes);
        return "offersList";
    }

    @GetMapping("/offer/{id}")
    public String offerDetails(Model model, @PathVariable("id") Integer id) {
        Offer offer = offersService.getOffer(id);
        model.addAttribute("idid", Currentlog.getId());
        model.addAttribute("offer", offer);
        return "offerDetails";
    }

    @GetMapping("/newoffer")
    public String newOfferForm(Model model, Offer offer) {

        model.addAttribute("idid", Currentlog.getId());
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
        if (Currentlog.getId() == null) {
            return "redirect:/";
        }
        model.addAttribute("idid", Currentlog.getId());
        if (binding.hasErrors()) {
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

        offer.setUser(offersService.getUseruser(Currentlog.getId()));
        offer = offersService.createOffer(offer);

        return "redirect:/offer/" + offer.getId();
    }

    @GetMapping("/deleteoffer/{id}")
    public String deleteOffer(Model model, @PathVariable("id") Integer id) {
        try {
            if (!Objects.equals(offersService.getOffer(id).getUser().getId(), Currentlog.getId())) {
                return "redirect:/";
            }

        } catch (Exception e) {
        }
        Offer offer = offersService.deleteOffer(id);
        model.addAttribute("idid", Currentlog.getId());
        model.addAttribute("offer", offer);
        return "deleteOffer";
    }

    @GetMapping("/editoffer/{id}")
    public String editOffer(Model model, @PathVariable("id") Integer id) {

        model.addAttribute("idid", Currentlog.getId());
        List<CarModel> carModels = offersService.getCarModels();
        List<BodyStyle> bodyStyles = offersService.getBodyStyles();
        List<FuelType> fuelTypes = offersService.getFuelTypes();
        Offer offer = offersService.getOffer(id);
        try {

            if (!Objects.equals(Currentlog.getId(), offer.getUser().getId())) {
                return "redirect:/";
            }
        } catch (Exception e) {
        }

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
            List<CarModel> carModels = offersService.getCarModels();
            List<BodyStyle> bodyStyles = offersService.getBodyStyles();
            List<FuelType> fuelTypes = offersService.getFuelTypes();

            model.addAttribute("idid", Currentlog.getId());
            model.addAttribute("carModels", carModels);
            model.addAttribute("bodyStyles", bodyStyles);
            model.addAttribute("fuelTypes", fuelTypes);
            model.addAttribute("header", "Edycja ogłoszenia");
            model.addAttribute("action", "/editoffer/" + id);
            model.addAttribute("offer", offer);

            return "offerForm";
        }
        offer.setUser(offersService.getUseruser(Currentlog.getId()));
        offersService.saveOffer(offer);

        return "redirect:/offer/" + offer.getId();
    }
}
