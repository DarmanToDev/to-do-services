package com.test.todoservices.controller;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.todoservices.dto.TagRequest;
import com.test.todoservices.exception.ResourceNotFoundException;
import com.test.todoservices.model.Tag;
import com.test.todoservices.repository.TagRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
        Tag newTag = new Tag(); 
        BeanUtils.copyProperties(tagReq, newTag);
        
	    this.tagRepository.save(newTag);
            
        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tag/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editTag(@PathVariable Integer id, @Valid @RequestBody TagRequest tagReq) throws Exception {
       
        Tag existTag = this.tagRepository.findById(id).orElse(null);
        if(existTag == null){
            throw new ResourceNotFoundException();
        } else {
            BeanUtils.copyProperties(tagReq, existTag, getNullPropertyNames(tagReq));
            this.tagRepository.save(existTag);

            return ResponseEntity.status(HttpStatus.OK).body(existTag);
        }
    }

	@CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tag/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> tagDetail(@PathVariable Integer id) throws Exception {
         
        Tag tag = this.tagRepository.findById(id).orElse(null);
        if(tag == null){
            throw new ResourceNotFoundException();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(tag);
        }
    }
  
    @CrossOrigin(origins = "*")
    @RequestMapping(value="/app/tag/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteTag(@PathVariable Integer id) throws Exception {

        Tag existTag = this.tagRepository.findById(id).orElse(null);
        if(existTag == null){
            throw new ResourceNotFoundException();
        } else {
            existTag.setIsActive(false);
            this.tagRepository.save(existTag);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
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

    private TagRequest convertToDto(Tag tag) {
        TagRequest tagResponse = MAPPER.convertValue(tag, TagRequest.class);
        return tagResponse;
    }

    public static String[] getNullPropertyNames(Object source) {
	    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
	    return Stream.of(wrappedSource.getPropertyDescriptors())
	        .map(FeatureDescriptor::getName)
	        .filter(propertyName -> {
	            try {
	               return wrappedSource.getPropertyValue(propertyName) == null;
	            } catch (Exception e) {
	               return false;
	            }                
	        })
	        .toArray(String[]::new);
	}

}
