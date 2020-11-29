package com.example.storage.repository;

import com.example.storage.model.TNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<TNote, Long> {
}
