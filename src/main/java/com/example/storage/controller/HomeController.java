package com.example.storage.controller;


import com.example.storage.model.TUser;
import com.example.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView getHomePage(Principal principal) throws Exception {
        TUser user = userRepository.findByUsername(principal.getName());
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("notes", user.getNoteList());
        modelAndView.addObject("files", user.getFileList());
        return modelAndView;
    }
}
