package com.poseidon.DAO;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poseidon.DTO.BoardDTO;
import com.poseidon.DTO.CommentDTO;
import com.poseidon.DTO.FileDTO;
import com.poseidon.DTO.PageDTO;

@Repository
public class BoardDAO {
	//연결
	
	@Autowired
	private SqlSession sqlSession;

	public List<BoardDTO> boardList(PageDTO page) {
		
		return sqlSession.selectList("board.boardList", page);
	}

	public BoardDTO detail(BoardDTO boardUpdate) {
		
		sqlSession.update("board.countUp", boardUpdate);
		return sqlSession.selectOne("board.detail", boardUpdate);
	}

	public Object write(BoardDTO write) {
		
		return sqlSession.insert("board.write",write);
	}

	public int totalCount(int b_cate) {

		return sqlSession.selectOne("board.totalCount", b_cate);
	}

	public List<CommentDTO> commentList(int b_no) {
		
		return sqlSession.selectList("board.commentList", b_no);
	}

	public void commentWrite(CommentDTO dto) {

		sqlSession.insert("board.commentWrite", dto);
	}

	public void commentDelete(CommentDTO dto) {
		sqlSession.delete("board.commentDelete", dto);
		
	}

	public void commentUpdate(CommentDTO dto) {
		sqlSession.update("board.commentUpdate", dto);
		
	}

	public void boardDelete(BoardDTO dto) {
		sqlSession.delete("board.boardDelete", dto);
		
	}

	public int boardUpdate(BoardDTO dto) {
		
		return sqlSession.update("board.boardUpdate", dto);
	}

	public void fileWrite(FileDTO fileDTO) {
		sqlSession.insert("board.fileWrite", fileDTO);
		
	}

	
	public List<FileDTO> fileList(int b_no) {
		
	return sqlSession.selectList("board.fileList", b_no); 
	
	}

	public void deleteFile(int b_no) {
		sqlSession.delete("board.deleteFile", b_no);
		
	}

}


/*
 * 클래스 생성하기
 * @Controller : 객체 생성, Controller 역할을 합니다.
 * @Service    : 객체 생성, Service
 * @Repository : 객체 생성, DAO
 * @Component  : 객체 생성, 그 외
 * 
 * 객체 연결하기
 * @Autowired : 타입으로 검색해서 넣어주기 = 스프링에서 제공
 * @Inject    : 타입으로 검색해서 = 자바가 제공합니다
 * @Resource  : name으로 검색해서 = 스프링에서 제공
 *
 */ 
