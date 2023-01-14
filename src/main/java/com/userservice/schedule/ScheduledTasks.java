package com.userservice.schedule;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.userservice.Email.dto.EmailDto;
import com.userservice.Email.dto.MailResponse;
import com.userservice.Email.enums.EmailFrom;
import com.userservice.Email.service.NotificationCenterService;
import com.userservice.model.Singer;
import com.userservice.model.SingerRepository;
import com.userservice.model.UserDto;
import com.userservice.model.UserRepository;
import java.util.List;


@Component
public class ScheduledTasks {
    
    @Autowired
    private NotificationCenterService notificationCenterService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SingerRepository singerRepository;


    @Scheduled(cron = "0 0 0 30 1 ?") 
    public void scheduleTaskUsingCronExpression() {
        List<UserDto> users = userRepository.findAll();
        List<Singer> singers = singerRepository.findAll();
        int max = 0;
        Singer winninSinger = null;
        for (Singer singer : singers) {
            if (singer.getUsers().size() > max) {
                max = singer.getUsers().size();
                winninSinger = singer;
            }
        }
		
        for (UserDto user : users) {
            EmailDto emailDto = EmailDto.builder()
            .emailFrom(EmailFrom.NO_REPLY)
            .emailTo(user.getUserEmail())
            .emailSubject("Vote Results")
            .emailMessage("the Winner" + winninSinger.getName())
            .build();
            notificationCenterService.sendEmail(emailDto);
        }
    }

}
