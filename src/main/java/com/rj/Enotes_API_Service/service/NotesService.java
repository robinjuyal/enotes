package com.rj.Enotes_API_Service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rj.Enotes_API_Service.dto.NotesDto;
import com.rj.Enotes_API_Service.entity.FileDetails;

public interface NotesService {

    public Boolean saveNotes(String  notes, MultipartFile file) throws Exception;

    public List<NotesDto>getAllNotes();

    public byte[] downloadFile(FileDetails fileDetails) throws Exception;

    public FileDetails getFileDetails(Integer id)throws Exception;


}
