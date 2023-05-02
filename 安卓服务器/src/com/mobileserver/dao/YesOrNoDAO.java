package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.YesOrNo;
import com.mobileserver.util.DB;

public class YesOrNoDAO {

	public List<YesOrNo> QueryYesOrNo() {
		List<YesOrNo> yesOrNoList = new ArrayList<YesOrNo>();
		DB db = new DB();
		String sql = "select * from YesOrNo where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				YesOrNo yesOrNo = new YesOrNo();
				yesOrNo.setYesNoId(rs.getInt("yesNoId"));
				yesOrNo.setYesNoName(rs.getString("yesNoName"));
				yesOrNoList.add(yesOrNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return yesOrNoList;
	}
	/* 传入是否信息对象，进行是否信息的添加业务 */
	public String AddYesOrNo(YesOrNo yesOrNo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新是否信息 */
			String sqlString = "insert into YesOrNo(yesNoName) values (";
			sqlString += "'" + yesOrNo.getYesNoName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "是否信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "是否信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除是否信息 */
	public String DeleteYesOrNo(int yesNoId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from YesOrNo where yesNoId=" + yesNoId;
			db.executeUpdate(sqlString);
			result = "是否信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "是否信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录id获取到是否信息 */
	public YesOrNo GetYesOrNo(int yesNoId) {
		YesOrNo yesOrNo = null;
		DB db = new DB();
		String sql = "select * from YesOrNo where yesNoId=" + yesNoId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				yesOrNo = new YesOrNo();
				yesOrNo.setYesNoId(rs.getInt("yesNoId"));
				yesOrNo.setYesNoName(rs.getString("yesNoName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return yesOrNo;
	}
	/* 更新是否信息 */
	public String UpdateYesOrNo(YesOrNo yesOrNo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update YesOrNo set ";
			sql += "yesNoName='" + yesOrNo.getYesNoName() + "'";
			sql += " where yesNoId=" + yesOrNo.getYesNoId();
			db.executeUpdate(sql);
			result = "是否信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "是否信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
