package com.rj.Enotes_API_Service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rj.Enotes_API_Service.dto.NotesDto;
import com.rj.Enotes_API_Service.service.NotesService;
import com.rj.Enotes_API_Service.util.CommonUtil;

import org.springframework.util.CollectionUtils;


@RestController
@RequestMapping("api/v1/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;


    @PostMapping("/")
    public ResponseEntity<?>saveNotes(@RequestParam String notes , @RequestParam(required = false) MultipartFile file) throws Exception{

        Boolean saveNotes=notesService.saveNotes(notes, file);
        if (saveNotes) {
            return CommonUtil.createBuildResponseMessage("notes saved success", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
    @GetMapping("/")
    public ResponseEntity<?>getAllNotes(){

        List<NotesDto>notes =notesService.getAllNotes();
        if (CollectionUtils.isEmpty(notes)) {
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

}
