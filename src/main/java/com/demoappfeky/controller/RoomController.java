package com.demoappfeky.controller;

import com.demoappfeky.model.Branch;
import com.demoappfeky.model.Room;
import com.demoappfeky.repository.hotelRepo.BranchRepository;
import com.demoappfeky.repository.hotelRepo.RoomRepository;
import com.demoappfeky.services.hotelServices.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RoomController {

    public final RoomRepository roomRepository;
    public final RoomService roomService;
    public final BranchRepository branchRepository;
    public RoomController(RoomRepository roomRepository, RoomService roomService, BranchRepository branchRepository) {
        this.roomRepository = roomRepository;
        this.roomService = roomService;
        this.branchRepository = branchRepository;
    }

    @RequestMapping(value = "/roomsList",method = RequestMethod.GET)
    public String listRooms(Model model){
        List<Room> roomsList = roomRepository.findAll();
        model.addAttribute("roomsList",roomsList);
        return "room/roomsList";
    }

    @RequestMapping(value = "/newRoom" ,method = RequestMethod.GET)
    public String showRoomNewForm(Model model){
        List<Branch> branchList = branchRepository.findAll();
        model.addAttribute("room", new Room());
        model.addAttribute("branchList", branchList);
        return "room/addRoom";
    }

    @RequestMapping(value = "/newRoom" ,method = RequestMethod.POST)
    public String saveRoom(Room room, RedirectAttributes redirectAttributes){
        roomRepository.save(room);
        redirectAttributes.addFlashAttribute("message", "Room has been Saved successfully.");
        return "redirect:/roomsList";
    }

    @RequestMapping(value = "/removeRoom/{id}", method = RequestMethod.GET)
    public String removeRoom(@PathVariable("id") Integer id,
                             RedirectAttributes redirectAttributes){
        roomService.removeOne(id);
        redirectAttributes.addFlashAttribute("message", "Room has been Deleted successfully.");
        return "redirect:/roomsList";
    }
    @GetMapping("/updateRoom/{id}")
    public String updateHotel(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes){
        Room room = roomService.findRoomById(id);
        List<Branch> branchList = branchRepository.findAll();
        model.addAttribute("branchList", branchList);
        if (room != null){
            model.addAttribute("room", room);
            return "room/addRoom";
        }else {
            return "404";
        }
    }
}
