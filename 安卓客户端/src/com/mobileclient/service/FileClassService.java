package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.FileClass;
import com.mobileclient.util.HttpUtil;

/*文档分类管理业务逻辑层*/
public class FileClassService {
	/* 添加文档分类 */
	public String AddFileClass(FileClass fileClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", fileClass.getClassId() + "");
		params.put("className", fileClass.getClassName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询文档分类 */
	public List<FileClass> QueryFileClass(FileClass queryConditionFileClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "FileClassServlet?action=query";
		if(queryConditionFileClass != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		FileClassListHandler fileClassListHander = new FileClassListHandler();
		xr.setContentHandler(fileClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<FileClass> fileClassList = fileClassListHander.getFileClassList();
		return fileClassList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<FileClass> fileClassList = new ArrayList<FileClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				FileClass fileClass = new FileClass();
				fileClass.setClassId(object.getInt("classId"));
				fileClass.setClassName(object.getString("className"));
				fileClassList.add(fileClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileClassList;
	}

	/* 更新文档分类 */
	public String UpdateFileClass(FileClass fileClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", fileClass.getClassId() + "");
		params.put("className", fileClass.getClassName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除文档分类 */
	public String DeleteFileClass(int classId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "文档分类信息删除失败!";
		}
	}

	/* 根据分类id获取文档分类对象 */
	public FileClass GetFileClass(int classId)  {
		List<FileClass> fileClassList = new ArrayList<FileClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("classId", classId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				FileClass fileClass = new FileClass();
				fileClass.setClassId(object.getInt("classId"));
				fileClass.setClassName(object.getString("className"));
				fileClassList.add(fileClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = fileClassList.size();
		if(size>0) return fileClassList.get(0); 
		else return null; 
	}
}
