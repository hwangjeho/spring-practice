package com.poseidon.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.DAO.BoardDAO;
import com.poseidon.DTO.BoardDTO;
import com.poseidon.DTO.CommentDTO;
import com.poseidon.DTO.FileDTO;
import com.poseidon.DTO.PageDTO;

@Service
public class BoardService {
	
	@Autowired
	private BoardDAO boardDAO;

	public List<BoardDTO> boardList(PageDTO page) {
		
		return boardDAO.boardList(page);
	}

	public BoardDTO detail(BoardDTO boardUpdate) {
		
		return boardDAO.detail(boardUpdate);
	}

	public Object write(BoardDTO write) {
		
		return boardDAO.write(write);
	}

	public int totalCount(int b_cate) {
		
		return boardDAO.totalCount(b_cate);
	}

	public List<CommentDTO> commentList(int b_no) {
		
		return boardDAO.commentList(b_no);
	}

	public void commentWrite(CommentDTO dto) {
		boardDAO.commentWrite(dto);
	}

	public void commentDelete(CommentDTO dto) {
		boardDAO.commentDelete(dto);
		
	}

	public void commentUpdate(CommentDTO dto) {
		boardDAO.commentUpdate(dto);
		
	}

	public void boardDelete(BoardDTO dto) {
		boardDAO.boardDelete(dto);
		
	}

	public int boardUpdate(BoardDTO dto) {
		
		return boardDAO.boardUpdate(dto);
	}

	public void fileWrite(FileDTO fileDTO) {
		boardDAO.fileWrite(fileDTO);
		
	}


	 public List<FileDTO> fileList(int b_no) {
	  
	 return boardDAO.fileList(b_no); 
	 
	 }

	public void deleteFile(int b_no) {
		boardDAO.deleteFile(b_no);
		
	}

	public void fileUpdate(FileDTO fileDTO) {
		boardDAO.fileUpdate(fileDTO);
		
	}

	 
}
