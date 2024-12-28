package com.rj.Enotes_API_Service.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rj.Enotes_API_Service.dto.NotesDto;
import com.rj.Enotes_API_Service.dto.NotesDto.CategoryDto;
import com.rj.Enotes_API_Service.entity.FileDetails;
import com.rj.Enotes_API_Service.entity.Notes;
import com.rj.Enotes_API_Service.exception.ResourceNotFoundException;
import com.rj.Enotes_API_Service.repository.CategoryRepository;
import com.rj.Enotes_API_Service.repository.FileRepository;
import com.rj.Enotes_API_Service.repository.NotesRepository;
import com.rj.Enotes_API_Service.service.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepo;

    @Value("{file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {

        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        // Validation notes
        checkCategoryExist(notesDto.getCategory());

        Notes notesMap = mapper.map(notesDto, Notes.class);

        FileDetails fileDetails = saveFileDetails(file);
        if (!ObjectUtils.isEmpty(fileDetails)) {
            notesMap.setFileDetails(fileDetails);
        } else {
            notesMap.setFileDetails(null);
        }

        Notes saveNotes = notesRepository.save(notesMap);

        if (!ObjectUtils.isEmpty(saveNotes)) {
            return true;
        }
        return false;

    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();

            String rndString = UUID.randomUUID().toString();
            String extension = FilenameUtils.getExtension(originalFileName);
            String uploadFileName = rndString + "." + extension;

            File saveFile = new File(uploadPath);
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            // path :enotesapiservice/notes/java.pdf

            String storePath = uploadPath.concat(uploadFileName);

            /// upload file
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));

            if (upload != 0) {
                FileDetails fileDetails = new FileDetails();
                fileDetails.setOriginalFileName(originalFileName);
                fileDetails.setDisplayFileName(getDisplayName(originalFileName));
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setPath(storePath);
                FileDetails saveFileDetails = fileRepository.save(fileDetails);
                return saveFileDetails;
            }

        }
        return null;
    }

    private String getDisplayName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String fileName = FilenameUtils.removeExtension(originalFileName);
        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        fileName = fileName + "." + extension;
        return fileName;
    }

    private void checkCategoryExist(CategoryDto category) throws Exception {
        categoryRepo.findById(category.getId()).orElseThrow(() -> new ResourceNotFoundException("category id invalid"));
    }

    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepository.findAll().stream()
                .map(note -> mapper.map(note, NotesDto.class)).toList();
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {
                
        InputStream io=new FileInputStream(fileDetails.getPath());
        return StreamUtils.copyToByteArray(io);
        

    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
        FileDetails fileDetails = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File is not available"));

        return fileDetails;
    }

}
