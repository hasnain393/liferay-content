	package document.crud.portlet.action;
	
	
	
	
	import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import document.crud.constants.DocumentCrudPortletKeys;
	
	@Component(
		    immediate = true,
		    property = {
		       "javax.portlet.name=" + DocumentCrudPortletKeys.DOCUMENTCRUD,
		       "mvc.command.name=/"
		    },
		    service = MVCRenderCommand.class
	)
	public class DefaultController implements MVCRenderCommand{
		
	
	
		@Override
		public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
			// TODO Auto-generated method stub
			System.out.println("---------------------111111111111hello this is  DEFAULT CONTROLLER fro DOC CRUD ------------------");
			
			 ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	
			    try {
			        long folderId = 45006;/* Your Folder ID */;
			        List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(themeDisplay.getScopeGroupId(), folderId);
			        List<FileDisplayInfo> fileInfoList = new ArrayList<>();
			        
			        for (FileEntry fileEntry : fileEntries) {
			        	System.out.println("crud");
			        	System.out.println(fileEntry.getFileName());
			        	
			            String url = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + "/" +
	                             fileEntry.getFolderId() + "/" + fileEntry.getTitle();

	                fileInfoList.add(new FileDisplayInfo(fileEntry, url));
			        
						
					}
	
			        renderRequest.setAttribute("fileDisplayInfoList", fileInfoList);
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
	
			  
	
			    return "/view.jsp";
			}
			
			
	
	
	}
