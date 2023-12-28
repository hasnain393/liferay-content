<%@ include file="./init.jsp" %>

<p>
	<b><liferay-ui:message key="assetbycategory.caption"/></b>
</p>


<portlet:renderURL var="fileRenderURL" >
  <portlet:param name="mvcRenderCommandName" value="filerender" /> 
 
 <%--  <portlet:param name="mvcPath" value="/addform.jsp"></portlet:param> --%>
  </portlet:renderURL>



<aui:form action="${fileRenderURL}" method="post">

<aui:input type="text" name="category" label="Enter Category"  />

<aui:button type="submit" value="SUBMIT" />

</aui:form>