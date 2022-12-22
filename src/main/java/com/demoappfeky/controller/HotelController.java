package com.demoappfeky.controller;

import com.demoappfeky.model.Hotel;
import com.demoappfeky.repository.hotelRepo.HotelRepository;
import com.demoappfeky.services.hotelServices.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HotelController {

    private final HotelRepository hotelRepository;

    private final HotelService hotelService;

    public HotelController(HotelRepository categoryRepository, HotelService categoryService) {
        this.hotelRepository = categoryRepository;
        this.hotelService = categoryService;
    }

    @RequestMapping(value = "/hotelsList",method = RequestMethod.GET)
    public String listHotels(Model model){
        List<Hotel> hotelsList = hotelRepository.findAll();
        model.addAttribute("hotelsList",hotelsList);
        return "hotel/hotelsList";
    }

    @RequestMapping(value = "/newHotel" ,method = RequestMethod.GET)
    public String showHotelNewForm(Model model){
        model.addAttribute("hotel", new Hotel());
        return "hotel/addHotel";
    }

    @RequestMapping(value = "/newHotel" ,method = RequestMethod.POST)
    public String saveHotel(Hotel hotel, RedirectAttributes redirectAttributes){
        hotelRepository.save(hotel);
        redirectAttributes.addFlashAttribute("message", "Hotel has been saved successfully.");
        return "redirect:/hotelsList";
    }

    @RequestMapping(value = "/removeHotel/{id}", method = RequestMethod.GET)
    public String removeHotel(@PathVariable(value = "id") Long id, Model model,
                              RedirectAttributes redirectAttributes){
        hotelService.removeOne(id);
        redirectAttributes.addFlashAttribute("message", "Hotel has been Deleted successfully.");
//        List<Hotel> hoteList = hotelRepository.findAll();
//        model.addAttribute("hotelList", hoteList);
        return "redirect:/hotelsList";
    }

    @GetMapping("/updateHotel/{id}")
    public String updateHotel(@PathVariable int id, Model model,
                              RedirectAttributes redirectAttributes){
        Hotel hotel = hotelService.findHotelById(id);
        redirectAttributes.addFlashAttribute("message", "Hotel has been Updated successfully.");
        if (hotel != null){
            model.addAttribute("hotel", hotel);
            return "hotel/addHotel";
        }else {
            return "404";
        }
    }
}
