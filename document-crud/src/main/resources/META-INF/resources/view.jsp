<%@ include file="/init.jsp" %>
<script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.16/js/dataTables.jqueryui.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/dataTables.jqueryui.min.css" />



<body>
  <!-- <c:out value="${fileEntries}" />  -->   
  <table id="example" class="table table-bordered table-striped" style="width:100%">
    <thead>
      <tr>
       <th>File Title</th>
       <th>File Describtion</th>
       <th>File Url</th>

      </tr>
    </thead>
    <tbody>
    <c:forEach items="${fileDisplayInfoList}" var="fileInfo">
       <tr>
      <!-- <td>${fileInfo.fileEntry.title}</td> --> 
        <td>
           <a href="<portlet:renderURL windowState='normal'>
           <portlet:param name='mvcRenderCommandName' value='/showDetails'/>
           <portlet:param name='fileEntryId' value='${fileInfo.fileEntry.fileEntryId}' />
           </portlet:renderURL>">
               ${fileInfo.fileEntry.title}
           </a>
       </td>
       <td>${fileInfo.fileEntry.description}</td>
       <td><a href="${fileInfo.downloadUrl}">${fileInfo.fileEntry.title}</a></td>

       </tr>
    </c:forEach>
     
    </tbody>
  </table>
  
  <script>
  $(function(){
    $("#example").dataTable({
    	 "iDisplayLength":5, // default page size
         "aLengthMenu": [
           [5, 10, 20, -1],   // per page record options
           [5, 10, 20, "All"]
         ],
         "bLengthChange": true, //Customizable page size 
         "bSort": true, // for Soring
         "bFilter": true, //search box
         "aaSorting": [[0, 'asc']],
         "aoColumns": [{// Columns width
           "sWidth": "30%"
         }, {
           "sWidth": "30%"
         }, {
           "sWidth": "30%"
         }],
         "bAutoWidth": false,
         "oLanguage": {
           "sSearch": "Search: ",
           "sEmptyTable": "<div class='portlet-msg-alert'>No File Found</div>" // default message for no data
         },
         "sPaginationType": "full_numbers"
    });
  })
  </script>
</body>



