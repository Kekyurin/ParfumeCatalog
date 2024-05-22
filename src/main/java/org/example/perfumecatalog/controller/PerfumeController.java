package org.example.perfumecatalog.controller;

import org.example.perfumecatalog.model.Perfume;
import org.example.perfumecatalog.service.PerfumeService;
import org.example.perfumecatalog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/perfumes")
public class PerfumeController {

    private static final String EXISTING_PERFUMES = "existingPerfumes";

    private static final String USER_IS_ADMIN = "userIsAdmin";

    private static final String ORIGINAL_PERFUME = "originalPerfume";

    private final PerfumeService perfumeService;

    private final UserService userService;

    public PerfumeController(PerfumeService perfumeService, UserService userService) {
        this.perfumeService = perfumeService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public String getAddPerfumePage(@ModelAttribute("perfume") Perfume perfume) {
        return "perfume/add-perfume";
    }

    @GetMapping
    public String getPerfumeListPage(Model model) {
        model.addAttribute(EXISTING_PERFUMES, perfumeService.getAllPerfumes());
        model.addAttribute(USER_IS_ADMIN, userService.isLoggedInUserAdmin());
        return "perfume/perfumes-list";
    }

    @PostMapping
    public String savePerfume(@ModelAttribute("perfume") Perfume perfume,
                              @RequestParam("perfume-image") MultipartFile perfumeImage,
                              Model model) {
        perfumeService.savePerfume(perfume, perfumeImage);
        model.addAttribute(EXISTING_PERFUMES, perfumeService.getAllPerfumes());
        model.addAttribute(USER_IS_ADMIN, userService.isLoggedInUserAdmin());
        return "redirect:/perfumes";
    }

    @GetMapping("/delete/{id}")
    public String getPerfumeListPage(@PathVariable("id") Long perfumeId,
                                     Model model) {
        perfumeService.deletePerfume(perfumeId);
        model.addAttribute(EXISTING_PERFUMES, perfumeService.getAllPerfumes());
        model.addAttribute(USER_IS_ADMIN, userService.isLoggedInUserAdmin());
        return "perfume/perfumes-list";
    }

    @GetMapping("/edit/{id}")
    public String getEditPerfumePage(@PathVariable("id") Long perfumeId,
                                     @ModelAttribute("newPerfumeData") Perfume perfume,
                                     MultipartFile newImage,
                                     Model model) {
        model.addAttribute(ORIGINAL_PERFUME, perfumeService.getById(perfumeId));
        return "perfume/edit-perfume";
    }

    @PostMapping("/edit/{id}")
    public String editPerfume(@PathVariable("id") Long perfumeId,
                              @ModelAttribute("newPerfumeData") Perfume perfume,
                              MultipartFile newImage,
                              Model model) {
        perfumeService.updatePerfume(perfumeId, perfume, newImage);
        model.addAttribute(EXISTING_PERFUMES, perfumeService.getAllPerfumes());
        model.addAttribute(USER_IS_ADMIN, userService.isLoggedInUserAdmin());
        return "redirect:/perfumes";
    }

}
