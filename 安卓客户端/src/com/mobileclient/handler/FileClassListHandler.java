package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.FileClass;
public class FileClassListHandler extends DefaultHandler {
	private List<FileClass> fileClassList = null;
	private FileClass fileClass;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (fileClass != null) { 
            String valueString = new String(ch, start, length); 
            if ("classId".equals(tempString)) 
            	fileClass.setClassId(new Integer(valueString).intValue());
            else if ("className".equals(tempString)) 
            	fileClass.setClassName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("FileClass".equals(localName)&&fileClass!=null){
			fileClassList.add(fileClass);
			fileClass = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		fileClassList = new ArrayList<FileClass>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("FileClass".equals(localName)) {
            fileClass = new FileClass(); 
        }
        tempString = localName; 
	}

	public List<FileClass> getFileClassList() {
		return this.fileClassList;
	}
}
