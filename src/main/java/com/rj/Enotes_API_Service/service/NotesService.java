package com.rj.Enotes_API_Service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rj.Enotes_API_Service.dto.NotesDto;

public interface NotesService {

    public Boolean saveNotes(String  notes, MultipartFile file) throws Exception;

    public List<NotesDto>getAllNotes();


}
