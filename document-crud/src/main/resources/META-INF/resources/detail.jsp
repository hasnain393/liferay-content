<%@ include file="./init.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.liferay.portal.kernel.repository.model.FileEntry" %>
<%@ page import="com.liferay.asset.kernel.model.AssetCategory" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    FileEntry selectedFileEntry = (FileEntry) request.getAttribute("selectedFileEntry");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String creationDateStr = selectedFileEntry != null ? dateFormat.format(selectedFileEntry.getCreateDate()) : "";
    List<AssetCategory> fileCategories = (List<AssetCategory>) request.getAttribute("fileCategories");
%>

<h2>File Details</h2>
<% if (selectedFileEntry != null) { %>
    <p><strong>Title:</strong> <%= HtmlUtil.escape(selectedFileEntry.getTitle()) %></p>
    <p><strong>Description:</strong> <%= HtmlUtil.escape(selectedFileEntry.getDescription()) %></p>
    <p><strong>User Created:</strong> <%= HtmlUtil.escape(selectedFileEntry.getUserName()) %></p>
    <p><strong>Creation Date:</strong> <%= HtmlUtil.escape(creationDateStr) %></p>

    <p><strong>Categories:</strong> </p>
   
   <% if (fileCategories != null && !fileCategories.isEmpty()) { %>
    <ul>
        <c:forEach items="${fileCategories}" var="category">
            <li>some data</li>
        </c:forEach>
    </ul>
<% } else { %>
    <p>No categories.</p>
<% } %>
   
   
   
   
   
   

    <!-- Back Link -->
    <button>
        <a href="<portlet:renderURL><portlet:param name='mvcRenderCommandName' value='/' /></portlet:renderURL>">Back to List</a>
    </button>

<% } else { %>
    <p>File not found.</p>
<% } %>
