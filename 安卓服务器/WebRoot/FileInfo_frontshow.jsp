<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.FileInfo" %>
<%@ page import="com.chengxusheji.domain.FileClass" %>
<%@ page import="com.chengxusheji.domain.YesOrNo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�FileClass��Ϣ
    List<FileClass> fileClassList = (List<FileClass>)request.getAttribute("fileClassList");
    //��ȡ���е�YesOrNo��Ϣ
    List<YesOrNo> yesOrNoList = (List<YesOrNo>)request.getAttribute("yesOrNoList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    FileInfo fileInfo = (FileInfo)request.getAttribute("fileInfo");

%>
<HTML><HEAD><TITLE>�鿴�ĵ�</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>�ĵ�id:</td>
    <td width=70%><%=fileInfo.getFileId() %></td>
  </tr>

  <tr>
    <td width=30%>�ĵ�����:</td>
    <td width=70%>
      <%=fileInfo.getFileClassObj().getClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ĵ�����:</td>
    <td width=70%><%=fileInfo.getFileName() %></td>
  </tr>

  <tr>
    <td width=30%>�ĵ�ͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=fileInfo.getFilePhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>�ĵ�����:</td>
    <td width=70%><%=fileInfo.getFileDesc() %></td>
  </tr>

  <tr>
    <td width=30%>�Ƿ񹫿�:</td>
    <td width=70%>
      <%=fileInfo.getPrivateFlag().getYesNoName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ĵ��ļ�:</td>
    <td width=70%>
    	<% if(fileInfo.getDocFile()==null || fileInfo.getDocFile().equals("")) { %>
    	�����ļ�
    	<% } else { %>
    	<a href="<%=basePath %><%=fileInfo.getDocFile() %>" target="_blank"><%=fileInfo.getDocFile() %></a><br/>
    	<% } %>
    </td>
  </tr>
  <tr>
    <td width=30%>�ϴ��û�:</td>
    <td width=70%>
      <%=fileInfo.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ϴ�ʱ��:</td>
    <td width=70%><%=fileInfo.getUpTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
