package com.demoappfeky.controller;

import com.demoappfeky.model.Branch;
import com.demoappfeky.model.Hotel;
import com.demoappfeky.repository.hotelRepo.HotelRepository;
import com.demoappfeky.services.hotelServices.BranchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class BranchController {

    private final BranchService branchService;
    private final HotelRepository hotelRepository;

    public BranchController(BranchService branchService, HotelRepository hotelRepository) {
        this.branchService = branchService;
        this.hotelRepository = hotelRepository;
    }

    @RequestMapping(value = "/addBranch", method = RequestMethod.GET)
    public String addBranch(Model model){
        List<Hotel> listHotels = hotelRepository.findAll();
        Branch branch = new Branch();
        model.addAttribute("branch", branch);
        model.addAttribute("listHotels", listHotels);

        return "branch/addBranch";
    }

    @RequestMapping(value = "/addBranch", method = RequestMethod.POST)
    public String addBranchPost(@ModelAttribute(name = "branch") Branch branch,
                                 RedirectAttributes redirectAttributes) throws IOException {

        branchService.save(branch);
        redirectAttributes.addFlashAttribute("message", "The Branch has been saved successfully.");

        return "redirect:/branchesList";
    }

    @RequestMapping(value = "/updateBranch", method = RequestMethod.GET)
    public String updateBranch(Model model,@RequestParam("id") Long id) throws Exception {
        Optional<Branch> branch = branchService.findOne(id);
        if(branch.isPresent()) {
            List<Hotel> listHotels = hotelRepository.findAll();
            model.addAttribute("listHotels", listHotels);
            System.out.println(branch.get().getId());
            model.addAttribute("branch", branch.get());
            return "branch/updateBranch";
        } else {
            throw new Exception("Branch not found");
        }
    }

    @RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
    public String updateBranchPost(@ModelAttribute("branch") Branch branch, RedirectAttributes redirectAttributes) throws IOException{
        branchService.save(branch);
        redirectAttributes.addFlashAttribute("message", "The Branch has been updated successfully.");
        return "redirect:/branchesList";
    }

    @RequestMapping("/branchesList")
    public String branches(Model model){
        List<Branch> branchList = branchService.findAll();
        model.addAttribute("branchList", branchList);
        return "branch/branchesList";
    }

    @RequestMapping(value = "/removeBranch/{id}", method = RequestMethod.GET)
    public String removeBranch(@PathVariable(value = "id") Long id,
                               RedirectAttributes redirectAttributes){
        branchService.removeOne(id);
        redirectAttributes.addFlashAttribute("message", "Branch has been Deleted successfully.");
        return "redirect:/branchesList";
    }
}
