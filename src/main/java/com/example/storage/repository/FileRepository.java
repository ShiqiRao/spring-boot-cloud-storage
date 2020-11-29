package com.example.storage.repository;

import com.example.storage.model.TFile;
import com.example.storage.model.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<TFile, Long> {

    Optional<TFile> findOneByFilenameAndUser(String filename, TUser user);

}
