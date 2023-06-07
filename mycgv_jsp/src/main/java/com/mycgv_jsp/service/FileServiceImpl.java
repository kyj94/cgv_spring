package com.mycgv_jsp.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.mycgv_jsp.vo.BoardVo;

@Service("fileService")
public class FileServiceImpl {

	/** fileCheck 기능 - 파일이 존재하면 boardVo에 bfile, bsfile set, 없으면 boardVo 리턴 **/
	public BoardVo fileCheck(BoardVo boardVo) {
		if (boardVo.getFile1().getOriginalFilename() != null
				&& !boardVo.getFile1().getOriginalFilename().contentEquals("")) { // 파일이 존재하면

			// BSFILE 파일 중복 처리
			UUID uuid = UUID.randomUUID();
			String bfile = boardVo.getFile1().getOriginalFilename();
			String bsfile = uuid + "_" + bfile;

			System.out.println(bfile);
			System.out.println(bsfile);

			boardVo.setBfile(bfile);
			boardVo.setBsfile(bsfile);
		} else {
			System.out.println("파일 없음");
//			boardVo.setBfile("");
//			boardVo.setBsfile("");
		}

		return boardVo;
	} // BoardVo fileCheck(BoardVo boardVo)

	
	/** fileSave 기능 - 파일이 존재하면 서버에 저장하는 메소드 **/
	public void fileSave(BoardVo boardVo, HttpServletRequest request) throws Exception {
		// 파일의 저장위치
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		
		// 파일이 존재하면 서버에 저장
		if(boardVo.getBfile() != null && boardVo.getBsfile().equals("")) {
			System.out.println("save file -> " + boardVo.getBfile());
			File saveFile = new File(root_path + attach_path + boardVo.getBsfile());
			boardVo.getFile1().transferTo(saveFile);
		}

	}
	
	
	/** fileDelete 기능 - 파일 삭제 **/
	public void fileDelete(BoardVo boardVo, HttpServletRequest request, String oldFileName) throws Exception {
		// 파일의 삭제 위치
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		
		// 파일이 존재하면 서버에 저장
		if(!boardVo.getFile1().getOriginalFilename().equals("")) { // 새로운 파일 선택
			File deleteFile = new File(root_path + attach_path + oldFileName);
			
			if(deleteFile.exists()) {
				deleteFile.delete();
			}
			
		}

	}
	

} // class
