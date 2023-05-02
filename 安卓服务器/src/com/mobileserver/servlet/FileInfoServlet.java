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

	/*构造文档业务层对象*/
	private FileInfoDAO fileInfoDAO = new FileInfoDAO();

	/*默认构造函数*/
	public FileInfoServlet() {
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
			/*获取查询文档的参数信息*/
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

			/*调用业务逻辑层执行文档查询*/
			List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfo(fileClassObj,fileName,privateFlag,userObj,upTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加文档：获取文档参数，参数保存到新建的文档对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = fileInfoDAO.AddFileInfo(fileInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除文档：获取文档的文档id*/
			int fileId = Integer.parseInt(request.getParameter("fileId"));
			/*调用业务逻辑层执行删除操作*/
			String result = fileInfoDAO.DeleteFileInfo(fileId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新文档之前先根据fileId查询某个文档*/
			int fileId = Integer.parseInt(request.getParameter("fileId"));
			FileInfo fileInfo = fileInfoDAO.GetFileInfo(fileId);

			// 客户端查询的文档对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新文档：获取文档参数，参数保存到新建的文档对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = fileInfoDAO.UpdateFileInfo(fileInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
