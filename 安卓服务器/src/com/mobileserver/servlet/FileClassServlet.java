package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.FileClassDAO;
import com.mobileserver.domain.FileClass;

import org.json.JSONStringer;

public class FileClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ĵ�����ҵ������*/
	private FileClassDAO fileClassDAO = new FileClassDAO();

	/*Ĭ�Ϲ��캯��*/
	public FileClassServlet() {
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
			/*��ȡ��ѯ�ĵ�����Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ���ĵ������ѯ*/
			List<FileClass> fileClassList = fileClassDAO.QueryFileClass();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<FileClasss>").append("\r\n");
			for (int i = 0; i < fileClassList.size(); i++) {
				sb.append("	<FileClass>").append("\r\n")
				.append("		<classId>")
				.append(fileClassList.get(i).getClassId())
				.append("</classId>").append("\r\n")
				.append("		<className>")
				.append(fileClassList.get(i).getClassName())
				.append("</className>").append("\r\n")
				.append("	</FileClass>").append("\r\n");
			}
			sb.append("</FileClasss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(FileClass fileClass: fileClassList) {
				  stringer.object();
			  stringer.key("classId").value(fileClass.getClassId());
			  stringer.key("className").value(fileClass.getClassName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����ĵ����ࣺ��ȡ�ĵ�����������������浽�½����ĵ�������� */ 
			FileClass fileClass = new FileClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			fileClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			fileClass.setClassName(className);

			/* ����ҵ���ִ����Ӳ��� */
			String result = fileClassDAO.AddFileClass(fileClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���ĵ����ࣺ��ȡ�ĵ�����ķ���id*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = fileClassDAO.DeleteFileClass(classId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����ĵ�����֮ǰ�ȸ���classId��ѯĳ���ĵ�����*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			FileClass fileClass = fileClassDAO.GetFileClass(classId);

			// �ͻ��˲�ѯ���ĵ�������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("classId").value(fileClass.getClassId());
			  stringer.key("className").value(fileClass.getClassName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����ĵ����ࣺ��ȡ�ĵ�����������������浽�½����ĵ�������� */ 
			FileClass fileClass = new FileClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			fileClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			fileClass.setClassName(className);

			/* ����ҵ���ִ�и��²��� */
			String result = fileClassDAO.UpdateFileClass(fileClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
