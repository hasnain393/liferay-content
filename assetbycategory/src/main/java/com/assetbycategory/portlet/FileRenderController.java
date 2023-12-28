package com.assetbycategory.portlet;

import com.assetbycategory.constants.AssetByCategoryPortletKeys;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;



@Component(
	    immediate = true,
	    property = {
	       "javax.portlet.name=" +  AssetByCategoryPortletKeys.ASSETBYCATEGORY,
	       "mvc.command.name=filerender"
	    },
	    service = MVCRenderCommand.class
)
public class FileRenderController implements MVCRenderCommand{

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		System.out.println("///////////////////////AAAADDDDDDD///////////////////");
		
		String category= ParamUtil.getString(renderRequest,"category");
		
		System.out.println("category uis "+category);
		
		 if (category == null || category.isEmpty()) {
	            System.out.println("No category entered. Returning empty result list.");
	            // Set an empty list in the request attribute
	            renderRequest.setAttribute("filebycat", Collections.emptyList());
	            return "/result.jsp";
	        }

		
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		List<AssetCategory> categoryList = null;
		AssetVocabulary vocabulary = null;
		try {
			// Frist get the vocabulary 
			vocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(themeDisplay.getScopeGroupId(), "MyTag");
		} catch (PortalException e2) {
			System.out.println("no such vocabulary found");
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} //pass name of Vocabulary
		long vocabularyId = vocabulary.getVocabularyId();
	long categoryId = 0L;
	// then the category 
		categoryList = AssetCategoryLocalServiceUtil.getVocabularyCategories(vocabularyId, -1, -1, null); //List of all categories

		for(AssetCategory asset: categoryList)
		{
		String categoryName = asset.getName();
		System.out.println("category namr is "+categoryName );
		System.out.println("category entered by user is "+category );
		//get category Name
		if(categoryName.equalsIgnoreCase(category)){
		//perform other operations
			System.out.println("hurray ");
			categoryId= asset.getCategoryId();
			System.out.println(categoryId);
		}
		}
		
		
		
		//*************************************************************************************

		// Create an instance of AssetEntryQuery
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		// Set the category ID for filtering assets
		//long categoryId = 46427; // Replace with your desired category ID
		assetEntryQuery.setAnyCategoryIds(new long[] {categoryId});

		// Retrieve the list of asset entries based on the query
		List<AssetEntry> results = AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);

		// Process the results
		for (AssetEntry assetEntry : results) {
		//	System.out.println("ddfsdsdsdsddsdsdsdssssssssssssssssssssssssssssssssss");
			System.out.println("title"+assetEntry.getTitle());
			System.out.println("descrip"+assetEntry.getDescription());
			System.out.println("summary"+assetEntry.getSummary());
			 String url = getAllFileLink(themeDisplay, "File_Upload", assetEntry.getTitle()); 
		assetEntry.setUrl(url);
		System.out.println(assetEntry.getUrl());
			System.out.println("summary"+assetEntry.getSummary()); 
			
		
		    // Do something with the asset entry
		    // For example, you can access the asset's title using assetEntry.getTitle()
		}
		
		//******************************************************************************
		
		
		renderRequest.setAttribute("filebycat",results );
		
		return "/result.jsp";
	}
	public String getAllFileLink(ThemeDisplay themeDisplay, String folderName, String filename) {
        long repositoryId = themeDisplay.getScopeGroupId();
        Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
        try {
            Folder folder = DLAppServiceUtil.getFolder(repositoryId, parentFolderId, folderName);
            List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
            for (FileEntry file : fileEntries) {
                if (file.getTitle().equalsIgnoreCase(filename)) {
                    String url = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + repositoryId + "/" +
                                 file.getFolderId() + "/" + file.getTitle();
                    System.out.println("Link=>" + url);
                    return url;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ""; // Or return null if you prefer
    }
}