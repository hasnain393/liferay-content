package document.crud.portlet.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import document.crud.constants.DocumentCrudPortletKeys;

@Component(
	    property = {
	        "javax.portlet.name=" + DocumentCrudPortletKeys.DOCUMENTCRUD,
	        "mvc.command.name=/showDetails"
	    },
	    service = MVCRenderCommand.class
	)
	public class ShowDetailsMVCRenderCommand implements MVCRenderCommand {

	    @Override
	    public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
	        System.out.println("***********************************************");
	    	long fileEntryId = ParamUtil.getLong(renderRequest, "fileEntryId");

	        try {
	            FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);
	            renderRequest.setAttribute("selectedFileEntry", fileEntry);
	            
	            // Fetch categories associated with the FileEntry
	            List<AssetCategory> categories = AssetCategoryLocalServiceUtil.getCategories(FileEntry.class.getName(), fileEntryId);
	            for (AssetCategory assetCategory : categories) {
	            	System.out.println("cat assigned to file is");
					System.out.println("name"+assetCategory.getName());
					System.out.println("title"+assetCategory.getTitle());
				}
	            renderRequest.setAttribute("fileCategories", categories);
	            
	        } catch (PortalException e) {
	            e.printStackTrace();
	        }

	        return "/detail.jsp"; // Path to the JSP that will show the details
	    }
	}
