package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.FileInfo;
import com.mobileclient.util.HttpUtil;

/*文档管理业务逻辑层*/
public class FileInfoService {
	/* 添加文档 */
	public String AddFileInfo(FileInfo fileInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("fileId", fileInfo.getFileId() + "");
		params.put("fileClassObj", fileInfo.getFileClassObj() + "");
		params.put("fileName", fileInfo.getFileName());
		params.put("filePhoto", fileInfo.getFilePhoto());
		params.put("fileDesc", fileInfo.getFileDesc());
		params.put("privateFlag", fileInfo.getPrivateFlag() + "");
		params.put("docFile", fileInfo.getDocFile());
		params.put("userObj", fileInfo.getUserObj());
		params.put("upTime", fileInfo.getUpTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询文档 */
	public List<FileInfo> QueryFileInfo(FileInfo queryConditionFileInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "FileInfoServlet?action=query";
		if(queryConditionFileInfo != null) {
			urlString += "&fileClassObj=" + queryConditionFileInfo.getFileClassObj();
			urlString += "&fileName=" + URLEncoder.encode(queryConditionFileInfo.getFileName(), "UTF-8") + "";
			urlString += "&privateFlag=" + queryConditionFileInfo.getPrivateFlag();
			urlString += "&userObj=" + URLEncoder.encode(queryConditionFileInfo.getUserObj(), "UTF-8") + "";
			urlString += "&upTime=" + URLEncoder.encode(queryConditionFileInfo.getUpTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		FileInfoListHandler fileInfoListHander = new FileInfoListHandler();
		xr.setContentHandler(fileInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<FileInfo> fileInfoList = fileInfoListHander.getFileInfoList();
		return fileInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				FileInfo fileInfo = new FileInfo();
				fileInfo.setFileId(object.getInt("fileId"));
				fileInfo.setFileClassObj(object.getInt("fileClassObj"));
				fileInfo.setFileName(object.getString("fileName"));
				fileInfo.setFilePhoto(object.getString("filePhoto"));
				fileInfo.setFileDesc(object.getString("fileDesc"));
				fileInfo.setPrivateFlag(object.getInt("privateFlag"));
				fileInfo.setDocFile(object.getString("docFile"));
				fileInfo.setUserObj(object.getString("userObj"));
				fileInfo.setUpTime(object.getString("upTime"));
				fileInfoList.add(fileInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileInfoList;
	}

	/* 更新文档 */
	public String UpdateFileInfo(FileInfo fileInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("fileId", fileInfo.getFileId() + "");
		params.put("fileClassObj", fileInfo.getFileClassObj() + "");
		params.put("fileName", fileInfo.getFileName());
		params.put("filePhoto", fileInfo.getFilePhoto());
		params.put("fileDesc", fileInfo.getFileDesc());
		params.put("privateFlag", fileInfo.getPrivateFlag() + "");
		params.put("docFile", fileInfo.getDocFile());
		params.put("userObj", fileInfo.getUserObj());
		params.put("upTime", fileInfo.getUpTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除文档 */
	public String DeleteFileInfo(int fileId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("fileId", fileId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "文档信息删除失败!";
		}
	}

	/* 根据文档id获取文档对象 */
	public FileInfo GetFileInfo(int fileId)  {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("fileId", fileId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "FileInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				FileInfo fileInfo = new FileInfo();
				fileInfo.setFileId(object.getInt("fileId"));
				fileInfo.setFileClassObj(object.getInt("fileClassObj"));
				fileInfo.setFileName(object.getString("fileName"));
				fileInfo.setFilePhoto(object.getString("filePhoto"));
				fileInfo.setFileDesc(object.getString("fileDesc"));
				fileInfo.setPrivateFlag(object.getInt("privateFlag"));
				fileInfo.setDocFile(object.getString("docFile"));
				fileInfo.setUserObj(object.getString("userObj"));
				fileInfo.setUpTime(object.getString("upTime"));
				fileInfoList.add(fileInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = fileInfoList.size();
		if(size>0) return fileInfoList.get(0); 
		else return null; 
	}
}
