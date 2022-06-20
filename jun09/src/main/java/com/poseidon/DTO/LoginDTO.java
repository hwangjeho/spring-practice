package com.poseidon.DTO;

import lombok.Data;

@Data
public class LoginDTO {
	private String u_id, u_pw, u_date, u_name, u_email;
	private int u_no, u_grade, u_resign;
}
