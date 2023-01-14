package com.userservice.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
@RequestMapping("/jpa/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SingerRepository singerRepository;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        UserDto user = userRepository.findById(id).get();
        if (user == null) {
            throw new NotFoundException("User not found"); 
        }
        return user;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@Valid @RequestBody UserDto user) {
        UserDto checkUserName = userRepository.findByUserName(user.getUserName());
        UserDto checkUserEmail = userRepository.findByUserEmail(user.getUserEmail());
        if(checkUserName == null && checkUserEmail == null) {
            UserDto newUser = userRepository.save(user);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{usersId}")
                    .buildAndExpand(newUser.getId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        }else{
            throw new NotValidException("Conflict");
        }
    }

    @PostMapping("/vote-Singer/{userID}")
    public ResponseEntity<Object> voteSinger(@PathVariable Long userID, @RequestBody Singer singerRequestbody) {
        UserDto user = userRepository.findById(userID).get();
        Singer singer = singerRepository.findByName(singerRequestbody.getName());
        LocalDate now = LocalDate.now();
        LocalDate endVotingDate = LocalDate.of(2023, 1, 30);

        if (user == null) {
            throw new NotFoundException("User not found");
        }else if(singer == null ){
            throw new NotFoundException("Singer not found");
        }else if(user.getSinger() != null){
            throw new NotValidException("Can't Vote again !");
        }else if(endVotingDate.isBefore(now)){
            throw new NotValidException("Vote has been ended !");
        }

        List<UserDto> singerVotes = singer.getUsers();
        singerVotes.add(user);
        singer.setUsers(singerVotes);
        singerRepository.save(singer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{usersId}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{userID}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userID , @Valid @RequestBody UserDto requestBody) {
        UserDto user = userRepository.findById(userID).get();
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        user.setUserName(requestBody.getUserName());
        user.setUserEmail(requestBody.getUserEmail());
        UserDto updatedUser = userRepository.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("{usersId}")
        .buildAndExpand(updatedUser.getId())
        .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{userID}")
    public void deleteUser(@PathVariable Long userID){
        UserDto user = userRepository.findById(userID).get();
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        userRepository.delete(user);
    }

    @GetMapping("/voting-result/{userId}")
    public Singer getWinninSinger(@PathVariable Long userId) {
        UserDto user = userRepository.findById(userId).get();
        if (user == null) {
            throw new NotFoundException("User not found"); 
        }else if(user.getSinger() != null){
            throw new NotValidException("User should Vote to get winnin singer !"); 
        }

        List<Singer> singers = singerRepository.findAll();
        int max = 0;
        Singer winninSinger = null;
        for (Singer singer : singers) {
            if (singer.getUsers().size() > max) {
                max = singer.getUsers().size();
                winninSinger = singer;
            }
        }
        return winninSinger;
    }

}
