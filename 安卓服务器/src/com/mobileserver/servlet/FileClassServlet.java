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

	/*构造文档分类业务层对象*/
	private FileClassDAO fileClassDAO = new FileClassDAO();

	/*默认构造函数*/
	public FileClassServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询文档分类的参数信息*/

			/*调用业务逻辑层执行文档分类查询*/
			List<FileClass> fileClassList = fileClassDAO.QueryFileClass();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加文档分类：获取文档分类参数，参数保存到新建的文档分类对象 */ 
			FileClass fileClass = new FileClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			fileClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			fileClass.setClassName(className);

			/* 调用业务层执行添加操作 */
			String result = fileClassDAO.AddFileClass(fileClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除文档分类：获取文档分类的分类id*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			/*调用业务逻辑层执行删除操作*/
			String result = fileClassDAO.DeleteFileClass(classId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新文档分类之前先根据classId查询某个文档分类*/
			int classId = Integer.parseInt(request.getParameter("classId"));
			FileClass fileClass = fileClassDAO.GetFileClass(classId);

			// 客户端查询的文档分类对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新文档分类：获取文档分类参数，参数保存到新建的文档分类对象 */ 
			FileClass fileClass = new FileClass();
			int classId = Integer.parseInt(request.getParameter("classId"));
			fileClass.setClassId(classId);
			String className = new String(request.getParameter("className").getBytes("iso-8859-1"), "UTF-8");
			fileClass.setClassName(className);

			/* 调用业务层执行更新操作 */
			String result = fileClassDAO.UpdateFileClass(fileClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
