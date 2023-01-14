package com.userservice.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.userservice.Exception.NotFoundException;
import com.userservice.Exception.NotValidException;
import com.userservice.model.Singer;
import com.userservice.model.SingerRepository;
import com.userservice.model.UserDto;
import com.userservice.model.UserRepository;

@RestController
@Validated
@RequestMapping("/jpa/singers")
public class SingerController {

    @Autowired
    SingerRepository singerRepository;

    @GetMapping
    public List<Singer> getAllSingers() {
        return singerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Singer getSinger(@PathVariable Long id) {
        Singer singer = singerRepository.findById(id).get();
        if (singer == null) {
            throw new NotFoundException("Singer not found"); 
        }
        return singer;
    }

    @PostMapping
    public ResponseEntity<Object> saveSinger(@Valid @RequestBody Singer singer) {
        Singer checkSingerName = singerRepository.findByName(singer.getName());
        if(checkSingerName == null) {
            Singer newSinger = singerRepository.save(singer);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{singerId}")
                    .buildAndExpand(newSinger.getId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }else{
            throw new NotValidException("Conflict");
        }
    }

    @PutMapping("/{singerID}")
    public ResponseEntity<Object> updateSinger(@PathVariable Long singerID , @Valid @RequestBody Singer requestBody) {
        Singer singer = singerRepository.findById(singerID).get();
        if (singer == null) {
            throw new NotFoundException("Singer not found");
        }
        singer.setName(requestBody.getName());
        singer.setUsers(requestBody.getUsers());
        Singer updatedSinger = singerRepository.save(singer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("{usersId}")
        .buildAndExpand(updatedSinger.getId())
        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{singerID}")
    public void deleteUser(@PathVariable Long singerID){
        Singer singer = singerRepository.findById(singerID).get();
        if (singer == null) {
            throw new NotFoundException("Singer not found");
        }
        singerRepository.delete(singer);
    }
    
}
