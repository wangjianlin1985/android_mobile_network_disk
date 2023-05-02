<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.FileInfo" %>
<%@ page import="com.chengxusheji.domain.FileClass" %>
<%@ page import="com.chengxusheji.domain.YesOrNo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的FileClass信息
    List<FileClass> fileClassList = (List<FileClass>)request.getAttribute("fileClassList");
    //获取所有的YesOrNo信息
    List<YesOrNo> yesOrNoList = (List<YesOrNo>)request.getAttribute("yesOrNoList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    FileInfo fileInfo = (FileInfo)request.getAttribute("fileInfo");

%>
<HTML><HEAD><TITLE>查看文档</TITLE>
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
    <td width=30%>文档id:</td>
    <td width=70%><%=fileInfo.getFileId() %></td>
  </tr>

  <tr>
    <td width=30%>文档分类:</td>
    <td width=70%>
      <%=fileInfo.getFileClassObj().getClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>文档名称:</td>
    <td width=70%><%=fileInfo.getFileName() %></td>
  </tr>

  <tr>
    <td width=30%>文档图片:</td>
    <td width=70%><img src="<%=basePath %><%=fileInfo.getFilePhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>文档描述:</td>
    <td width=70%><%=fileInfo.getFileDesc() %></td>
  </tr>

  <tr>
    <td width=30%>是否公开:</td>
    <td width=70%>
      <%=fileInfo.getPrivateFlag().getYesNoName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>文档文件:</td>
    <td width=70%>
    	<% if(fileInfo.getDocFile()==null || fileInfo.getDocFile().equals("")) { %>
    	暂无文件
    	<% } else { %>
    	<a href="<%=basePath %><%=fileInfo.getDocFile() %>" target="_blank"><%=fileInfo.getDocFile() %></a><br/>
    	<% } %>
    </td>
  </tr>
  <tr>
    <td width=30%>上传用户:</td>
    <td width=70%>
      <%=fileInfo.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>上传时间:</td>
    <td width=70%><%=fileInfo.getUpTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
