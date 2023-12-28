package com.documentmanager.portlet.action;




import com.documentmanager.constants.DocumentmanagerPortletKeys;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
	    immediate = true,
	    property = {
	       "javax.portlet.name=" + DocumentmanagerPortletKeys.DOCUMENTMANAGER,
	       "mvc.command.name=/"
	    },
	    service = MVCRenderCommand.class
)
public class DefaultController implements MVCRenderCommand{
	


	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub
		System.out.println("---------------------111111111111hello this is  DEFAULT CONTROLLER ------------------");
		
	
		  ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		    List<AssetCategory> categoryList = null;
		    AssetVocabulary vocabulary = null;
		    try {
		        vocabulary = AssetVocabularyLocalServiceUtil.getGroupVocabulary(themeDisplay.getScopeGroupId(), "MyTag");
		    } catch (PortalException e2) {
		        System.out.println("no such vocabulary found");
		        e2.printStackTrace();
		    }
		    if (vocabulary != null) {
		        long vocabularyId = vocabulary.getVocabularyId();
		        categoryList = AssetCategoryLocalServiceUtil.getVocabularyCategories(vocabularyId, -1, -1, null); // List of all categories
		    }

		    renderRequest.setAttribute("categoryList", categoryList); // Send the category list to the JSP

		    // ... [Rest of your code] ...

		    return "/view.jsp";
		}
		
		


}
