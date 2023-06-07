package com.mycgv_jsp.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.mycgv_jsp.vo.BoardVo;

@Service("fileService")
public class FileServiceImpl {

	/** fileCheck ��� - ������ �����ϸ� boardVo�� bfile, bsfile set, ������ boardVo ���� **/
	public BoardVo fileCheck(BoardVo boardVo) {
		if (boardVo.getFile1().getOriginalFilename() != null
				&& !boardVo.getFile1().getOriginalFilename().contentEquals("")) { // ������ �����ϸ�

			// BSFILE ���� �ߺ� ó��
			UUID uuid = UUID.randomUUID();
			String bfile = boardVo.getFile1().getOriginalFilename();
			String bsfile = uuid + "_" + bfile;

			System.out.println(bfile);
			System.out.println(bsfile);

			boardVo.setBfile(bfile);
			boardVo.setBsfile(bsfile);
		} else {
			System.out.println("���� ����");
//			boardVo.setBfile("");
//			boardVo.setBsfile("");
		}

		return boardVo;
	} // BoardVo fileCheck(BoardVo boardVo)

	
	/** fileSave ��� - ������ �����ϸ� ������ �����ϴ� �޼ҵ� **/
	public void fileSave(BoardVo boardVo, HttpServletRequest request) throws Exception {
		// ������ ������ġ
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		
		// ������ �����ϸ� ������ ����
		if(boardVo.getBfile() != null && boardVo.getBsfile().equals("")) {
			System.out.println("save file -> " + boardVo.getBfile());
			File saveFile = new File(root_path + attach_path + boardVo.getBsfile());
			boardVo.getFile1().transferTo(saveFile);
		}

	}
	
	
	/** fileDelete ��� - ���� ���� **/
	public void fileDelete(BoardVo boardVo, HttpServletRequest request, String oldFileName) throws Exception {
		// ������ ���� ��ġ
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		
		// ������ �����ϸ� ������ ����
		if(!boardVo.getFile1().getOriginalFilename().equals("")) { // ���ο� ���� ����
			File deleteFile = new File(root_path + attach_path + oldFileName);
			
			if(deleteFile.exists()) {
				deleteFile.delete();
			}
			
		}

	}
	

} // class
