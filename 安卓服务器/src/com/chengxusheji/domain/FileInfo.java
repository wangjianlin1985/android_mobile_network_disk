package com.chengxusheji.domain;

import java.sql.Timestamp;
public class FileInfo {
    /*�ĵ�id*/
    private int fileId;
    public int getFileId() {
        return fileId;
    }
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    /*�ĵ�����*/
    private FileClass fileClassObj;
    public FileClass getFileClassObj() {
        return fileClassObj;
    }
    public void setFileClassObj(FileClass fileClassObj) {
        this.fileClassObj = fileClassObj;
    }

    /*�ĵ�����*/
    private String fileName;
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /*�ĵ�ͼƬ*/
    private String filePhoto;
    public String getFilePhoto() {
        return filePhoto;
    }
    public void setFilePhoto(String filePhoto) {
        this.filePhoto = filePhoto;
    }

    /*�ĵ�����*/
    private String fileDesc;
    public String getFileDesc() {
        return fileDesc;
    }
    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /*�Ƿ񹫿�*/
    private YesOrNo privateFlag;
    public YesOrNo getPrivateFlag() {
        return privateFlag;
    }
    public void setPrivateFlag(YesOrNo privateFlag) {
        this.privateFlag = privateFlag;
    }

    /*�ĵ��ļ�*/
    private String docFile;
    public String getDocFile() {
        return docFile;
    }
    public void setDocFile(String docFile) {
        this.docFile = docFile;
    }

    /*�ϴ��û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*�ϴ�ʱ��*/
    private String upTime;
    public String getUpTime() {
        return upTime;
    }
    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

}