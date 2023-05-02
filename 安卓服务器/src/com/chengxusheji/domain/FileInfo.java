package com.chengxusheji.domain;

import java.sql.Timestamp;
public class FileInfo {
    /*文档id*/
    private int fileId;
    public int getFileId() {
        return fileId;
    }
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    /*文档分类*/
    private FileClass fileClassObj;
    public FileClass getFileClassObj() {
        return fileClassObj;
    }
    public void setFileClassObj(FileClass fileClassObj) {
        this.fileClassObj = fileClassObj;
    }

    /*文档名称*/
    private String fileName;
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /*文档图片*/
    private String filePhoto;
    public String getFilePhoto() {
        return filePhoto;
    }
    public void setFilePhoto(String filePhoto) {
        this.filePhoto = filePhoto;
    }

    /*文档描述*/
    private String fileDesc;
    public String getFileDesc() {
        return fileDesc;
    }
    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /*是否公开*/
    private YesOrNo privateFlag;
    public YesOrNo getPrivateFlag() {
        return privateFlag;
    }
    public void setPrivateFlag(YesOrNo privateFlag) {
        this.privateFlag = privateFlag;
    }

    /*文档文件*/
    private String docFile;
    public String getDocFile() {
        return docFile;
    }
    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    /*上传用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*上传时间*/
    private String upTime;
    public String getUpTime() {
        return upTime;
    }
    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

}