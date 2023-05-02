package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.FileInfo;
import com.mobileserver.util.DB;

public class FileInfoDAO {

	public List<FileInfo> QueryFileInfo(int fileClassObj,String fileName,int privateFlag,String userObj,String upTime) {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		DB db = new DB();
		String sql = "select * from FileInfo where 1=1";
		if (fileClassObj != 0)
			sql += " and fileClassObj=" + fileClassObj;
		if (!fileName.equals(""))
			sql += " and fileName like '%" + fileName + "%'";
		if (privateFlag != 0)
			sql += " and privateFlag=" + privateFlag;
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!upTime.equals(""))
			sql += " and upTime like '%" + upTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setFileId(rs.getInt("fileId"));
				fileInfo.setFileClassObj(rs.getInt("fileClassObj"));
				fileInfo.setFileName(rs.getString("fileName"));
				fileInfo.setFilePhoto(rs.getString("filePhoto"));
				fileInfo.setFileDesc(rs.getString("fileDesc"));
				fileInfo.setPrivateFlag(rs.getInt("privateFlag"));
				fileInfo.setDocFile(rs.getString("docFile"));
				fileInfo.setUserObj(rs.getString("userObj"));
				fileInfo.setUpTime(rs.getString("upTime"));
				fileInfoList.add(fileInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return fileInfoList;
	}
	/* 传入文档对象，进行文档的添加业务 */
	public String AddFileInfo(FileInfo fileInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新文档 */
			String sqlString = "insert into FileInfo(fileClassObj,fileName,filePhoto,fileDesc,privateFlag,docFile,userObj,upTime) values (";
			sqlString += fileInfo.getFileClassObj() + ",";
			sqlString += "'" + fileInfo.getFileName() + "',";
			sqlString += "'" + fileInfo.getFilePhoto() + "',";
			sqlString += "'" + fileInfo.getFileDesc() + "',";
			sqlString += fileInfo.getPrivateFlag() + ",";
			sqlString += "'" + fileInfo.getDocFile() + "',";
			sqlString += "'" + fileInfo.getUserObj() + "',";
			sqlString += "'" + fileInfo.getUpTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "文档添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文档添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除文档 */
	public String DeleteFileInfo(int fileId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from FileInfo where fileId=" + fileId;
			db.executeUpdate(sqlString);
			result = "文档删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文档删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据文档id获取到文档 */
	public FileInfo GetFileInfo(int fileId) {
		FileInfo fileInfo = null;
		DB db = new DB();
		String sql = "select * from FileInfo where fileId=" + fileId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				fileInfo = new FileInfo();
				fileInfo.setFileId(rs.getInt("fileId"));
				fileInfo.setFileClassObj(rs.getInt("fileClassObj"));
				fileInfo.setFileName(rs.getString("fileName"));
				fileInfo.setFilePhoto(rs.getString("filePhoto"));
				fileInfo.setFileDesc(rs.getString("fileDesc"));
				fileInfo.setPrivateFlag(rs.getInt("privateFlag"));
				fileInfo.setDocFile(rs.getString("docFile"));
				fileInfo.setUserObj(rs.getString("userObj"));
				fileInfo.setUpTime(rs.getString("upTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return fileInfo;
	}
	/* 更新文档 */
	public String UpdateFileInfo(FileInfo fileInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update FileInfo set ";
			sql += "fileClassObj=" + fileInfo.getFileClassObj() + ",";
			sql += "fileName='" + fileInfo.getFileName() + "',";
			sql += "filePhoto='" + fileInfo.getFilePhoto() + "',";
			sql += "fileDesc='" + fileInfo.getFileDesc() + "',";
			sql += "privateFlag=" + fileInfo.getPrivateFlag() + ",";
			sql += "docFile='" + fileInfo.getDocFile() + "',";
			sql += "userObj='" + fileInfo.getUserObj() + "',";
			sql += "upTime='" + fileInfo.getUpTime() + "'";
			sql += " where fileId=" + fileInfo.getFileId();
			db.executeUpdate(sql);
			result = "文档更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文档更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
