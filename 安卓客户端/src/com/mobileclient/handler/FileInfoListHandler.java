package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.FileInfo;
public class FileInfoListHandler extends DefaultHandler {
	private List<FileInfo> fileInfoList = null;
	private FileInfo fileInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (fileInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("fileId".equals(tempString)) 
            	fileInfo.setFileId(new Integer(valueString).intValue());
            else if ("fileClassObj".equals(tempString)) 
            	fileInfo.setFileClassObj(new Integer(valueString).intValue());
            else if ("fileName".equals(tempString)) 
            	fileInfo.setFileName(valueString); 
            else if ("filePhoto".equals(tempString)) 
            	fileInfo.setFilePhoto(valueString); 
            else if ("fileDesc".equals(tempString)) 
            	fileInfo.setFileDesc(valueString); 
            else if ("privateFlag".equals(tempString)) 
            	fileInfo.setPrivateFlag(new Integer(valueString).intValue());
            else if ("docFile".equals(tempString)) 
            	fileInfo.setDocFile(valueString); 
            else if ("userObj".equals(tempString)) 
            	fileInfo.setUserObj(valueString); 
            else if ("upTime".equals(tempString)) 
            	fileInfo.setUpTime(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("FileInfo".equals(localName)&&fileInfo!=null){
			fileInfoList.add(fileInfo);
			fileInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		fileInfoList = new ArrayList<FileInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("FileInfo".equals(localName)) {
            fileInfo = new FileInfo(); 
        }
        tempString = localName; 
	}

	public List<FileInfo> getFileInfoList() {
		return this.fileInfoList;
	}
}
