package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import com.mobileserver.dao.FileInfoDAO;
import com.mobileserver.domain.FileInfo;

import org.json.JSONStringer;

public class FileInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ĵ�ҵ������*/
	private FileInfoDAO fileInfoDAO = new FileInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public FileInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ�ĵ��Ĳ�����Ϣ*/
			int fileClassObj = 0;
			if (request.getParameter("fileClassObj") != null)
				fileClassObj = Integer.parseInt(request.getParameter("fileClassObj"));
			String fileName = request.getParameter("fileName");
			fileName = fileName == null ? "" : new String(request.getParameter(
					"fileName").getBytes("iso-8859-1"), "UTF-8");
			int privateFlag = 0;
			if (request.getParameter("privateFlag") != null)
				privateFlag = Integer.parseInt(request.getParameter("privateFlag"));
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String upTime = request.getParameter("upTime");
			upTime = upTime == null ? "" : new String(request.getParameter(
					"upTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ���ĵ���ѯ*/
			List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfo(fileClassObj,fileName,privateFlag,userObj,upTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<FileInfos>").append("\r\n");
			for (int i = 0; i < fileInfoList.size(); i++) {
				sb.append("	<FileInfo>").append("\r\n")
				.append("		<fileId>")
				.append(fileInfoList.get(i).getFileId())
				.append("</fileId>").append("\r\n")
				.append("		<fileClassObj>")
				.append(fileInfoList.get(i).getFileClassObj())
				.append("</fileClassObj>").append("\r\n")
				.append("		<fileName>")
				.append(fileInfoList.get(i).getFileName())
				.append("</fileName>").append("\r\n")
				.append("		<filePhoto>")
				.append(fileInfoList.get(i).getFilePhoto())
				.append("</filePhoto>").append("\r\n")
				.append("		<fileDesc>")
				.append(fileInfoList.get(i).getFileDesc())
				.append("</fileDesc>").append("\r\n")
				.append("		<privateFlag>")
				.append(fileInfoList.get(i).getPrivateFlag())
				.append("</privateFlag>").append("\r\n")
				.append("		<docFile>")
				.append(fileInfoList.get(i).getDocFile())
				.append("</docFile>").append("\r\n")
				.append("		<userObj>")
				.append(fileInfoList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<upTime>")
				.append(fileInfoList.get(i).getUpTime())
				.append("</upTime>").append("\r\n")
				.append("	</FileInfo>").append("\r\n");
			}
			sb.append("</FileInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(FileInfo fileInfo: fileInfoList) {
				  stringer.object();
			  stringer.key("fileId").value(fileInfo.getFileId());
			  stringer.key("fileClassObj").value(fileInfo.getFileClassObj());
			  stringer.key("fileName").value(fileInfo.getFileName());
			  stringer.key("filePhoto").value(fileInfo.getFilePhoto());
			  stringer.key("fileDesc").value(fileInfo.getFileDesc());
			  stringer.key("privateFlag").value(fileInfo.getPrivateFlag());
			  stringer.key("docFile").value(fileInfo.getDocFile());
			  stringer.key("userObj").value(fileInfo.getUserObj());
			  stringer.key("upTime").value(fileInfo.getUpTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����ĵ�����ȡ�ĵ��������������浽�½����ĵ����� */ 
			FileInfo fileInfo = new FileInfo();
			int fileId = Integer.parseInt(request.getParameter("fileId"));
			fileInfo.setFileId(fileId);
			int fileClassObj = Integer.parseInt(request.getParameter("fileClassObj"));
			fileInfo.setFileClassObj(fileClassObj);
			String fileName = new String(request.getParameter("fileName").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setFileName(fileName);
			String filePhoto = new String(request.getParameter("filePhoto").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setFilePhoto(filePhoto);
			String fileDesc = new String(request.getParameter("fileDesc").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setFileDesc(fileDesc);
			int privateFlag = Integer.parseInt(request.getParameter("privateFlag"));
			fileInfo.setPrivateFlag(privateFlag);
			String docFile = new String(request.getParameter("docFile").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setDocFile(docFile);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setUserObj(userObj);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fileInfo.setUpTime(sdf.format(new java.util.Date()));

			/* ����ҵ���ִ����Ӳ��� */
			String result = fileInfoDAO.AddFileInfo(fileInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ĵ�����ȡ�ĵ����ĵ�id*/
			int fileId = Integer.parseInt(request.getParameter("fileId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = fileInfoDAO.DeleteFileInfo(fileId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����ĵ�֮ǰ�ȸ���fileId��ѯĳ���ĵ�*/
			int fileId = Integer.parseInt(request.getParameter("fileId"));
			FileInfo fileInfo = fileInfoDAO.GetFileInfo(fileId);

			// �ͻ��˲�ѯ���ĵ����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("fileId").value(fileInfo.getFileId());
			  stringer.key("fileClassObj").value(fileInfo.getFileClassObj());
			  stringer.key("fileName").value(fileInfo.getFileName());
			  stringer.key("filePhoto").value(fileInfo.getFilePhoto());
			  stringer.key("fileDesc").value(fileInfo.getFileDesc());
			  stringer.key("privateFlag").value(fileInfo.getPrivateFlag());
			  stringer.key("docFile").value(fileInfo.getDocFile());
			  stringer.key("userObj").value(fileInfo.getUserObj());
			  stringer.key("upTime").value(fileInfo.getUpTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����ĵ�����ȡ�ĵ��������������浽�½����ĵ����� */ 
			FileInfo fileInfo = new FileInfo();
			int fileId = Integer.parseInt(request.getParameter("fileId"));
			fileInfo.setFileId(fileId);
			int fileClassObj = Integer.parseInt(request.getParameter("fileClassObj"));
			fileInfo.setFileClassObj(fileClassObj);
			String fileName = new String(request.getParameter("fileName").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setFileName(fileName);
			String filePhoto = new String(request.getParameter("filePhoto").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setFilePhoto(filePhoto);
			String fileDesc = new String(request.getParameter("fileDesc").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setFileDesc(fileDesc);
			int privateFlag = Integer.parseInt(request.getParameter("privateFlag"));
			fileInfo.setPrivateFlag(privateFlag);
			String docFile = new String(request.getParameter("docFile").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setDocFile(docFile);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setUserObj(userObj);
			String upTime = new String(request.getParameter("upTime").getBytes("iso-8859-1"), "UTF-8");
			fileInfo.setUpTime(upTime);

			/* ����ҵ���ִ�и��²��� */
			String result = fileInfoDAO.UpdateFileInfo(fileInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
