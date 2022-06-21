package com.poseidon.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.poseidon.DTO.BoardDTO;
import com.poseidon.DTO.CommentDTO;
import com.poseidon.DTO.FileDTO;
import com.poseidon.DTO.PageDTO;
import com.poseidon.Service.BoardService;
import com.poseidon.Util.FileSave;
import com.poseidon.Util.Util;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class BoardController {
	
	@Autowired
	private 
	BoardService 
	boardService;
	
	@Autowired
	private 
	Util 
	util;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private FileSave fileSave;
	
	@GetMapping(value = "/success")
	public String success() {
		return "success";
	}
	@GetMapping(value = "/failure")
	public String failure(){
		return "failure";
	}
	@GetMapping(value = "/detail")
	public ModelAndView detail(@RequestParam("b_no") int b_no) {
		ModelAndView mv = new ModelAndView("detail");
		BoardDTO detail = new BoardDTO();
		detail.setB_no(b_no);
		BoardDTO dto = new BoardDTO();
		dto.setB_no(b_no);
		dto = boardService.detail(dto);
		mv.addObject("detail", dto);
		List<CommentDTO> cList = boardService.commentList(b_no);
		mv.addObject("cList", cList);
		
		//파일업로드 여부 확인
		if(dto.getFileCount() > 0) {
			System.out.println("파일이 있습니다.");
			//파일이 있다면 해당 파일 이름도 보내주기
			List<FileDTO> fileList = boardService.fileList(b_no);
			mv.addObject("fileList", fileList);
		}
		return mv;
	}
	@GetMapping(value = "/index")
	public String index() {
		return "index";
	}
	//글쓰기 화면 나오게 하기
	//화면만 이동하려면 String으로 하시면 편합니다.
	@GetMapping(value = "/write")
	public String write() {
		return "write";
	}
	@PostMapping(value = "/write")
	public String write(HttpServletRequest request, MultipartFile[] files) throws Exception {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		//해야 할 일 -> db로
		BoardDTO write = new BoardDTO();
		write.setB_title(title);
		write.setB_content(content);
		
		write.setU_id((String) session.getAttribute("id"));
		
		int result = (int) boardService.write(write);
		
		System.out.println("처리 결과 : " + result);
		System.out.println("방금 저장된 PK : " + write.getB_no());
		
		//배열처리
		for (MultipartFile file : files) {
			if( !(  file.getOriginalFilename().isEmpty()  ) ) {
				System.out.println("업로드한 파일 이름 : " + file.getOriginalFilename());
				System.out.println("사이즈 : " + file.getSize());
				System.out.println("유무 : " + file.isEmpty());
				
				//파일을 저장할 실제 경로 얻어오기	톰캣가상경로
				String realPath = servletContext.getRealPath("resources/upload/");
				System.out.println("파일이 저장되는 경로 : " + realPath);
			
				//호출
				String realFileName = fileSave.save(realPath, file);
				System.out.println("저장한 파일 이름 : " + realFileName);
				
				//파일이름을 데이터베이스에 저장하는 작업
				//b_no, realFilename을 저장합니다.
				FileDTO fileDTO = new FileDTO();
				fileDTO.setB_no(write.getB_no());
				fileDTO.setF_filename(realFileName);
				
				boardService.fileWrite(fileDTO);
				
				System.out.println("업로드 끝. 경로로 들어가서 확인하세요.");
			}
		}
		
		
		if(result == 1) {
			//return "redirect:/success";
			return "redirect:/detail?b_no=" + write.getB_no();
		} else {
			return "redirect:/failure";
		}
		
	}	
	
	@RequestMapping(value = "/board")
	public ModelAndView board(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("board"); //jsp
		//값 보내기
		//mv.addObject("test", "테스트");//이름, 값
		//service에게 일 시키기
		
		//카테고리 잡기
		int b_cate = 1;
		if(request.getParameter("b_cate") != null) {
			b_cate = util.str2Int(request.getParameter("b_cate"));
		}
		//전자정부페이징 사용하기
		int pageNo = 1;
		if(request.getParameter("pageNo") != null) {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}
		
		//recordCountPageNo 한 페이지당 게시되는 게시물 수 yes
		int listScale = 10;
		//pageSize = 페이지 리스트에 게시되는 페이지 수 yes
		int pageScale = 10;
		//totalRecordCount	전체 게시물 건수 yes
		int totalCount = boardService.totalCount(b_cate);
		
		//System.out.println("totalCount : " + totalCount);
		
		//전자정부페이징 호출
		PaginationInfo paginationInfo = new PaginationInfo();
		//값대입
		paginationInfo.setCurrentPageNo(pageNo);
		paginationInfo.setRecordCountPerPage(listScale);
		paginationInfo.setPageSize(pageScale);
		paginationInfo.setTotalRecordCount(totalCount);
		//전자정부 계산하기
		int startPage = paginationInfo.getFirstRecordIndex();
		int lastPage = paginationInfo.getRecordCountPerPage();
		
		//서버로 보내기
		PageDTO page = new PageDTO();
		page.setStartPage(startPage);
		page.setLastPage(lastPage);
		page.setB_cate(b_cate);
		
		List<BoardDTO> boardList = boardService.boardList(page);
		mv.addObject("list", boardList);
		mv.addObject("paginationInfo", paginationInfo);
		mv.addObject("pageNo", pageNo);
		mv.addObject("b_cate", b_cate);
		
		//파일업로드 여부 확인
		//파일이 있다면 해당 파일 이름도 보내주기
		
		
		return mv;
	}
	
	@PostMapping(value = "/comment")
	public String comment(HttpServletRequest request) throws UnsupportedEncodingException {
		//한글설정
		//html < > /
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null
				&& request.getParameter("b_no") != null
				&& request.getParameter("comment") != null) {
			
			CommentDTO dto = new CommentDTO();
			
			dto.setB_no(Integer.parseInt(request.getParameter("b_no")));
			//html tag막기, 줄바꿈 설정하기
			String comment = request.getParameter("comment");
			comment = util.html(comment);
			dto.setC_content(  comment  );
			dto.setU_id((String) session.getAttribute("id"));
			boardService.commentWrite(dto);
			System.out.println(comment);
			return "redirect:/detail?b_no=" + request.getParameter("b_no");//되돌아가기
		} else {
			return "redirect:/login";
		}
	}
	
	//댓글삭제
	@GetMapping(value = "/commentDelete")
	public String commentDelete(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null
				&& request.getParameter("b_no") != null
				&& request.getParameter("c_no") != null) {
			CommentDTO dto = new CommentDTO();
			dto.setB_no(util.str2Int(request.getParameter("b_no")));
			dto.setC_no(util.str2Int(request.getParameter("c_no")));
			dto.setU_id((String)session.getAttribute("id"));
			boardService.commentDelete(dto);
		}
		
		
		return "redirect:/detail?b_no=" + request.getParameter("b_no");
	}
	
	//댓글수정
	@PostMapping(value = "/commentUpdate")
	public String commentUpdate(HttpServletRequest request) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		if(session.getAttribute("id") != null
				&& request.getParameter("b_no") != null
				&& request.getParameter("c_no") != null) {
			CommentDTO dto = new CommentDTO();
			String comment = request.getParameter("comment");
			comment = util.html(comment);
			dto.setC_content(  comment  );
			dto.setB_no(util.str2Int(request.getParameter("b_no")));
			dto.setC_no(util.str2Int(request.getParameter("c_no")));
			dto.setU_id((String)session.getAttribute("id"));
			boardService.commentUpdate(dto);
		}
		return "redirect:/detail?b_no=" + request.getParameter("b_no");
	}
	
	//게시글삭제
	@GetMapping(value = "/boardDelete")
	public String boardDelete(@RequestParam(value = "b_no", required = false, defaultValue = "0") int b_no, HttpSession session) {
		if(b_no > 0 && session.getAttribute("id") != null) {
			BoardDTO dto = new BoardDTO();
			dto.setB_no(b_no);
			dto.setU_id((String) session.getAttribute("id"));
				
			//파일 삭제
			List<FileDTO> fileList = boardService.fileList(b_no);
			//경로
			String realPath = servletContext.getRealPath("resources/upload/");
			//파일명
			for(FileDTO fileDTO : fileList) {
				File file = new File(realPath, fileDTO.getF_filename());
				if(file.exists()) {
					if(file.delete()) {
						System.out.println("파일이 삭제되었습니다.");
					}else {
						System.out.println("실패했습니다.");
					}
				}
				
				
			}
			//테이블에서 삭제
			boardService.deleteFile(b_no);
			
			boardService.boardDelete(dto);
		}
		return "redirect:/board";
	}
	
	//게시글수정
	@GetMapping(value = "/boardUpdate")
	public ModelAndView boardUpdate(HttpServletRequest request, MultipartFile[] files) throws IllegalStateException, IOException{
		request.setCharacterEncoding("UTF-8");
		ModelAndView mv = new ModelAndView("boardUpdate");
		HttpSession session = request.getSession();
		if(session.getAttribute("id") != null
				&& request.getParameter("b_no") != null) {
			//dto에 담아서 mv에 붙이기 = detail
			BoardDTO boardUpdate = new BoardDTO();
			boardUpdate.setB_no(util.str2Int(request.getParameter("b_no")));
			boardUpdate.setU_id((String)session.getAttribute("id"));
			
			BoardDTO dto = boardService.detail(   boardUpdate  );
			System.out.println(dto);
			
			for (MultipartFile file : files) {
				if( !(  file.getOriginalFilename().isEmpty()  ) ) {
					
					//파일을 저장할 실제 경로 얻어오기	톰캣가상경로
					String realPath = servletContext.getRealPath("resources/upload/");
				
					//호출
					String realFileName = fileSave.save(realPath, file);
					
					//파일이름을 데이터베이스에 저장하는 작업
					//b_no, realFilename을 저장합니다.
					FileDTO fileDTO = new FileDTO();
					fileDTO.setB_no(boardUpdate.getB_no());
					fileDTO.setF_filename(realFileName);
					
					boardService.fileUpdate(fileDTO);
					
					System.out.println("업로드 끝. 경로로 들어가서 확인하세요.");
				}
			}
			if(dto != null) {
				mv.addObject("dto", dto);
			}
		}else {
			//다른 페이지로 이동시키기
			mv.setViewName("redirect:/failure");
		}
		return mv;
	}
	
	//글수정저장
	@PostMapping(value = "boardUpdate")
	public String boardUpdate2(HttpServletRequest request) throws UnsupportedEncodingException {
		//필요한 값 : b_no, session, b_title, b_content
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		if(session.getAttribute("id") != null && request.getParameter("b_no") != null
				&& request.getParameter("title") != null && request.getParameter("content") != null) {
			//값이 다 있다면 DTO에 담기
			BoardDTO dto = new BoardDTO();
			dto.setU_id((String) session.getAttribute("id"));
			dto.setB_no(util.str2Int(request.getParameter("b_no")));
			dto.setB_title(request.getParameter("title"));
			dto.setB_content(request.getParameter("content"));
			
			int result = boardService.boardUpdate(dto);
			
			if(result == 1) {
				return "redirect:/success";
			} else {
				return "redirect:/failure";
			}
		
		} else {
			return "redirect:/failure";
		}
	}
}
