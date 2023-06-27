package com.mycgv_jsp.service;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mycgv_jsp.vo.AdminNoticeVo;
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

			System.out.println("bfile : " + bfile);
			System.out.println("bsfile : " + bsfile);

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
		if(boardVo.getBfile() != null && !boardVo.getBsfile().equals("")) {
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
	
	
	/** fileDelete ��� - ���� ���� **/
	public void fileDelete(HttpServletRequest request, String oldFileName) throws Exception {
		// ������ ���� ��ġ
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		
		// ������ �����ϸ� ������ ����
		if(oldFileName != null && !oldFileName.equals("")) { 
			File deleteFile = new File(root_path + attach_path + oldFileName);
			
			if(deleteFile.exists()) {
				deleteFile.delete();
			}
			
		}
	}
	
	
	/** multiFileCheck - ��Ƽ���� üũ ��� **/
	public AdminNoticeVo multiFileCheck(AdminNoticeVo noticeVo) {
		
		for(CommonsMultipartFile file : noticeVo.getFiles()) {
			
			if(!file.getOriginalFilename().equals("")) {
				// ���� ����
				UUID uuid = UUID.randomUUID();
				noticeVo.getNfiles().add(file.getOriginalFilename());
				noticeVo.getNsfiles().add(uuid+"_"+file.getOriginalFilename());
			} else {
				// ���� ����
				noticeVo.getNfiles().add("");
				noticeVo.getNsfiles().add("");
			} 
		} // for
		
		noticeVo.setNfile1(noticeVo.getNfiles().get(0));
		noticeVo.setNsfile1(noticeVo.getNsfiles().get(0));
		noticeVo.setNfile2(noticeVo.getNfiles().get(1));
		noticeVo.setNsfile2(noticeVo.getNsfiles().get(1));
		
		return noticeVo;
	}
	
	
	/** MutiFileSave - ��Ƽ���� ���� ��� **/
	public void mutiFileSave(AdminNoticeVo noticeVo, HttpServletRequest request) throws Exception {
		
		// ������ ���� ��ġ
		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "\\resources\\upload\\";
		
		int count = 0;
		
		for(CommonsMultipartFile file : noticeVo.getFiles()) {
			// ������ �����ϸ� ������ ����
			if(file.getOriginalFilename() != null && !file.getOriginalFilename().equals("")) {
				File saveFile = new File(root_path + attach_path + noticeVo.getNsfiles().get(count));
				file.transferTo(saveFile);
			}
			
			count ++;
		}
		
	}
	
	
	

	

} // class
