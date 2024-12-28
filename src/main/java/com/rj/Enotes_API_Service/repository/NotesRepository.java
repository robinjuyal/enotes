package com.rj.Enotes_API_Service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rj.Enotes_API_Service.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{

    List<Notes> findByCreatedBy(Integer userId);

    Page<Notes> findByCreatedBy(Integer userId, Pageable pagable);
    

}
