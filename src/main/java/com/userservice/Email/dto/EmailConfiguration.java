package com.userservice.Email.dto;
import lombok.*;


@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfiguration {
    private String host;

    private Integer port;
	
	private String username;
	
	private String password;
	
	private String senderEmail;

}
