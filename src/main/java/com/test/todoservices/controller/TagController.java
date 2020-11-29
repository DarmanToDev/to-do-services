package com.test.todoservices.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.todoservices.dto.TagRequest;
import com.test.todoservices.exception.ResourceNotFoundException;
import com.test.todoservices.model.Tag;
import com.test.todoservices.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
    private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private TagRepository tagRepository;

	@CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tag", method = RequestMethod.POST)
    public ResponseEntity<?> addTag(@Valid @RequestBody TagRequest tagReq) throws Exception {
          
        Tag tag = MAPPER.convertValue(tagReq, Tag.class); 
	    this.tagRepository.save(tag);
            
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(tag));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tag/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editTag(@PathVariable Integer id) throws Exception {
       
        Tag existTag = this.tagRepository.findById(id).orElse(null);
        if(existTag == null){
            throw new ResourceNotFoundException();
        } else {
            // existTag = MAPPER.convertValue(tagReq, Tag.class);
            this.tagRepository.save(existTag);

            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(existTag));
        }
    }

	@CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tag/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> tagDetail(@PathVariable Integer id) throws Exception {
         
        Tag tag = this.tagRepository.findById(id).orElse(null);
        if(tag == null){
            throw new ResourceNotFoundException();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(convertToDto(tag));
        }
    }
  
    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tag/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTag(@PathVariable Integer id) throws Exception {

            Tag existTag = this.tagRepository.findById(id).orElse(null);
            if(existTag == null){
                throw new ResourceNotFoundException();
            } else {
                // existTag.setIsActive(false);
                this.tagRepository.save(existTag);

                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
    }

    private TagRequest convertToDto(Tag tag) {
        // ModelMapper modelMapper = new ModelMapper();
        // TagRequest tagResponse = modelMapper.map(tag, TagRequest.class);
        TagRequest tagResponse = MAPPER.convertValue(tag, TagRequest.class);
        return tagResponse;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tags", method = RequestMethod.GET)
    public ResponseEntity<?> listTag() throws Exception {
      
        List<Tag> listTag = this.tagRepository.findAll();
        if(listTag != null){
            
            return ResponseEntity.status(HttpStatus.OK).body(listTag.stream().map(this::convertToDto).collect(Collectors.toList()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new String("{}"));
    }

}
