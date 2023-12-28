<%@ include file="./init.jsp" %>






<p>
	<b><liferay-ui:message key="documentmanager.caption"/></b>
</p>


<portlet:actionURL name="uploadFile" var="uploadFileURL" />



<aui:form action="<%= uploadFileURL %>" method="post" enctype="multipart/form-data">
    <aui:input type="file" name="file" label="File" />
    
    <select name="category" id="categoryDropdown">
    <c:forEach items="${categoryList}" var="category">
        <option value="${category.categoryId}">${category.name}</option>
    </c:forEach>
</select>

    <aui:button-row>
        <aui:button type="submit" value="Upload" />
    </aui:button-row>
</aui:form>