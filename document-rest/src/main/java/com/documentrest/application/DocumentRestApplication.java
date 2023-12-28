package com.documentrest.application;




import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;



/**
 * @author Hasnain Ahmed
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/greetings",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Greetings.Rest"
	},
	service = Application.class
)
public class DocumentRestApplication extends Application {
	
	private DLAppLocalService dlAppLocalService;
	
	@Reference(unbind = "-")
	public void setDlAppLocalService(DLAppLocalService dlAppLocalService) {
		this.dlAppLocalService = dlAppLocalService;
	}

	public Set<Object> getSingletons() {
		return Collections.<Object>singleton(this);
	}

	@GET
	@Produces("text/plain")
	public String working() {
		return "It works!";
	}
	

	@POST
	@Path("/add-document/{fund-id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces("text/plain")
	public String addDocument(@Context HttpServletRequest request,@PathParam("fund-id") String fundId) {
		
		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
		long groupId = serviceContext.getScopeGroupId();
		ServletFileUpload fileUpload = new ServletFileUpload();
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		if(isMultiPart) {
			try {
				FileItemIterator fileIterator = fileUpload.getItemIterator(request);
				
				while (fileIterator.hasNext()) {
					FileItemStream item = fileIterator.next();
					
					String originalFileName = item.getName();
					String contentType = item.getContentType();
					
					if (!item.isFormField()) {
						InputStream inputStream = null;
						try {
							inputStream = item.openStream();
							ByteArrayOutputStream buffer = new ByteArrayOutputStream(); 
							int bytesRead;
							byte[] data = new byte[2048]; 
							while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) { 
								buffer.write(data, 0, bytesRead); 
							} 
							 
							DLAppLocalServiceUtil.addFileEntry("",serviceContext.getGuestOrUserId() , groupId,
									//DocumentsUtil.getFolderId(groupId, 0, DocumentKeys.PARENT_FOLDER_NAME)
									45006,
									originalFileName, contentType, buffer.toByteArray(), null, null, serviceContext);
							buffer.flush();
							
							
						} catch (PortalException e) {
							e.printStackTrace();
						}finally {
							inputStream.close();
						}
						
						
					}
				}
			} catch (FileUploadException | IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return "Document uploaded successfully!";
	}

	
	
	@GET
	@Path("/morning")
	@Produces("text/plain")
	public String hello() {
		//ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		return "Good morning!";
	}

	@GET
	@Path("/morning/{name}")
	@Produces("text/plain")
	public String morning(
		@PathParam("name") String name,
		@QueryParam("drink") String drink) {

		String greeting = "Good Morning " + name;

		if (drink != null) {
			greeting += ". Would you like some " + drink + "?";
		}

		return greeting;
	}
	
	




}