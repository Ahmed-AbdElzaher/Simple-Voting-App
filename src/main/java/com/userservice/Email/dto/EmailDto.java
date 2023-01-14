package com.userservice.Email.dto;

import com.userservice.Email.enums.EmailFrom;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailDto {
	
	private EmailFrom emailFrom;
	private String emailTo;
	private String emailSubject;
	private String emailMessage;

}
