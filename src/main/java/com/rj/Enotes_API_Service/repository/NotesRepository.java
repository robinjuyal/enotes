package com.rj.Enotes_API_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rj.Enotes_API_Service.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{
    

}
