package com.poseidon.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class BoardDTO {
	private int b_no, b_count, u_no, commentcount, fileCount;
	private String b_title, b_content, u_id;
	private Date b_date;
}