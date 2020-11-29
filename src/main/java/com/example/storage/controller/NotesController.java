package com.example.storage.controller;

import com.example.storage.model.TNote;
import com.example.storage.model.TUser;
import com.example.storage.repository.NoteRepository;
import com.example.storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class NotesController {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;


    @PostMapping("/notes")
    public String createOrUpdateNote(Principal principal, TNote note) {
        TUser user = userRepository.findByUsername(principal.getName());
        note.setUser(user);
        noteRepository.save(note);
        return "redirect:/result?success";
    }

    @GetMapping("/notes/delete")
    public String deleteNote(@RequestParam("id") long noteId) {
        Optional<TNote> noteOptional = noteRepository.findById(noteId);
        if (noteOptional.isPresent()) {
            noteRepository.delete(noteOptional.get());
            return "redirect:/result?success";
        }
        return "redirect:/result?error";
    }
}
