package com.documentmanager.portlet;

import com.documentmanager.constants.DocumentmanagerPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"javax.portlet.name="+DocumentmanagerPortletKeys.DOCUMENTMANAGER,
		"mvc.command.name=uploadFile"
	},
	service = MVCActionCommand.class
)
public class DocumentAction implements MVCActionCommand {
	
	
	private static String ROOT_FOLDER_NAME = "File_Upload";
	private static String ROOT_FOLDER_DESCRIPTION = "This folder is create for Upload documents";
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	
	public Folder getFolder(ThemeDisplay themeDisplay){
		Folder folder = null;
		try {
			 folder =(Folder) DLFolderLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, ROOT_FOLDER_NAME);
		} catch (Exception e) {	
			System.out.println(e.getMessage());
		}
		return folder;
	}

	
	//perfect code 
	/*
	 @Override
	    public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse)
	            throws PortletException {

	        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

	        try {
	            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
	            File file = uploadRequest.getFile("file");
	            String fileName = uploadRequest.getFileName("file");
	            String mimeType = MimeTypesUtil.getContentType(file);
	            long folderId = 45006; // Ensure this is the correct folder ID
	            String description = "This file is added programmatically";

	            // Get the selected category ID from the request
	            long categoryId = ParamUtil.getLong(uploadRequest, "category");

	            // Prepare the service context
	            ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), actionRequest);
	            serviceContext.setAddGroupPermissions(true);
	            serviceContext.setAddGuestPermissions(false);

	            // Add the category to the service context
	            if (categoryId > 0) {
	                serviceContext.setAssetCategoryIds(new long[] { categoryId });
	            }

	            // Upload the file
	            FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
	                themeDisplay.getScopeGroupId(), folderId, fileName, mimeType,
	                fileName, description, "", file, serviceContext);

	            // ... [Rest of your code for handling the uploaded file]

	        } catch (Exception e) {
	            SessionMessages.add(actionRequest, "error while uploading file");
	            e.printStackTrace();
	        }
	        return true;
	    }

	*/
	
	
	
	
	//works-alternate code
	
	
			@Override
			public boolean processAction(
					ActionRequest actionRequest, ActionResponse actionResponse)
				throws PortletException {
				
				ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

				
				try {
				
				
				
				UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
				//
				 UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
//		          
//				//String fileName=uploadPortletRequest.getFileName("uploadedFile");		 		
				 File file = uploadPortletRequest.getFile("file");
				
				
				FileEntry fileEntry = null;
				
				String title = file.getName();
				System.out.println("title is "+file.getName());
				
				
				String fileName=uploadPortletRequest.getFileName("file");	
				System.out.println("filename is "+fileName);
				
				
				
				long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
				
				DLFolder folder = DLFolderLocalServiceUtil.getFolder( themeDisplay.getLayout().getGroupId(), parentFolderId, "File_Upload");
				
				long repoId = folder.getRepositoryId();

				String mimeType = MimeTypesUtil.getContentType(file);
				long folderId = 45006;
				long categoryId = ParamUtil.getLong(uploadRequest, "category");
				ServiceContext context = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), actionRequest);
				context.setAddGroupPermissions(true);
				context.setAddGuestPermissions(false);
				 // Add the category to the service context
	            if (categoryId > 0) {
	            	context.setAssetCategoryIds(new long[] { categoryId });
	            }
			
				
				String description= "this file is added programmatically-ac";
				
				
				fileEntry = DLAppServiceUtil.addFileEntry(repoId, folderId, fileName, mimeType, fileName,description, "", file, context);
				
				List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repoId, folder.getFolderId());
		    	
		    	for (FileEntry documentEntry : fileEntries) {
		    		System.out.println(documentEntry.getFileName());
					System.out.println(documentEntry.getFileEntryId());
				}
				
//				
//				System.out.println("----------------+++++++++++++++++++++++++++++++--------");
//				UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
//
//					String fileName = uploadRequest.getFileName("file");
//					
//					//sysout
//					System.out.println(uploadRequest.getPathInfo());
//					String contentType = uploadRequest.getContentType("file");
//					
//					File file=new File(uploadRequest.getFile(fileName), contentType);
//					System.out.println("=========================>"+file.getPath());
//					
//			//
//				//	ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
//					
//
//		    ServiceContext serviceContext = null;
//			try {
//				serviceContext = ServiceContextFactory.getInstance(
//						DLFileEntry.class.getName(), actionRequest);
//			} catch (PortalException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		    serviceContext.getScopeGroupId();
//		    
//		    UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
//	          
//			//String fileName=uploadPortletRequest.getFileName("uploadedFile");		 			
//			//File file = uploadPortletRequest.getFile("uploadedFile");
//			String mimeType = uploadPortletRequest.getContentType("file");
//                        String title = fileName;
//                        
//			String description = "This file is added via programatically";
//			long repositoryId = themeDisplay.getScopeGroupId();
//			System.out.println("group id is "+themeDisplay.getScopeGroupId());
//			System.out.println("company id is "+ themeDisplay.getCompanyId());
//			System.out.println("Title=>"+title);
//		    try
//		    {  
//		    //Folder folder = getFolder(themeDisplay);
//		    	Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID; // if the id of the parent is set to default
//		    	
//		    	
//		    	//DLAppServiceUtil.getFolder(
//		    	
//		    	DLFolder dir = DLFolderLocalServiceUtil.getFolder( themeDisplay.getLayout().getGroupId(), parentFolderId, "File_Upload");
//		    	//ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), actionRequest);
//		    	//InputStream is = new FileInputStream( file );
//		    	
//		    	System.out.println("user id is"+themeDisplay.getUserId());
//		    	System.out.println("folder id is "+dir.getFolderId());
//		    	
//		    	
//		    	
//		    	
//		    	DLAppServiceUtil.addFileEntry(repositoryId, dir.getFolderId(), fileName, mimeType, title, description, "", file, serviceContext) ;
//		    	
//		    	List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repositoryId, dir.getFolderId());
//		    	
//		    	for (FileEntry fileEntry : fileEntries) {
//		    		System.out.println(fileEntry.getFileName());
//					System.out.println(fileEntry.getFileEntryId());
//				}
//		    	
//		    	SessionMessages.add(actionRequest, "file uploaded successfully");	
////		    	addFileEntry(description, repositoryId, dir.getFolderId(), fileName, mimeType, 
////		    			title, description, "", description, is, file.getTotalSpace(), null, null, serviceContext);
		    	
		     } catch (Exception e)
		    {
		    	  	SessionMessages.add(actionRequest, "error whle uploading file ");
		       System.out.println(e.getMessage());
		    	e.printStackTrace();
		    	
		    }
			return true;

			}
			
			
			
}
