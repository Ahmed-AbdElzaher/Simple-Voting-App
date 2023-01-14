package com.userservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=3, max=20,message="Invalid name size")
    @Pattern(regexp = "^(?![\\s\\S]*[^\\w -]+)[\\s\\S]*?$")
    private String userName;

    @Email 
    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY) 
    private Singer singer;

    public UserDto(Long id, String userName, String userEmail){
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
    }

}
