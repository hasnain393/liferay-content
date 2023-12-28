<%@ include file="./init.jsp" %>

<p>
	<b><liferay-ui:message key="assetbycategory.caption"/></b>
</p>


<h4>Result:</h4>


 <!-- Check if the filebycat list is empty -->
<c:choose>
    <c:when test="${empty filebycat}">
        <!-- Display this message if the list is empty -->
        <p>No file matching the category.</p>
    </c:when>
    <c:otherwise>
        <!-- Iterate over the list and display each file -->
        <c:forEach items="${filebycat}" var="file">
            <p>${file.getTitle()}</p>
            
            <!-- Prepare URL for download -->
            <c:set var="baseUrl" value="http://localhost:8080/documents/d/guest/" />
            <c:set var="dynamicParam" value="${file.getTitle()}" />
            <c:set var="staticParam" value="?download=true" />
            <c:set var="dynamicUrl" value="${baseUrl}${dynamicParam}${staticParam}" />

            <!-- Display download links -->
            <a href="${dynamicUrl}">Bad Document url</a>
            <a href="${file.getUrl()}">Good Document url</a>
        </c:forEach>
    </c:otherwise>
</c:choose>