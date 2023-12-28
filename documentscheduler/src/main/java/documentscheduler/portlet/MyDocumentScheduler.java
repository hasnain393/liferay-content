package documentscheduler.portlet;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

@Component(
		immediate = true,
		property = {
			"cron.expression= 0 0/01 1/1 1/1 * ?"   // scheduler runs every  min.
		},
		service = MyDocumentScheduler.class
	)
	public class MyDocumentScheduler extends BaseMessageListener {
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

	@Override
	protected void doReceive(Message message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Scheduled task executed...!!!!!!!!!");
		List<DLFileEntry> recentFiles = new ArrayList<>();
		Date fiveMinutesAgo = new Date(System.currentTimeMillis() - (5 * 60 * 1000));
        recentFiles = DLFileEntryLocalServiceUtil.getFileEntries(-1, -1)
                .stream()
                .filter(fileEntry -> fileEntry.getCreateDate().after(fiveMinutesAgo))
                .collect(Collectors.toList());
        
        for (DLFileEntry dlFileEntry : recentFiles) {
			System.out.println("in lsst 5 min"+dlFileEntry.getFileName());
		}
//		ThemeDisplay themeDisplay = (ThemeDisplay) message.get(WebKeys.THEME_DISPLAY);
//		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
//		DLFolder folder = DLFolderLocalServiceUtil.getFolder( themeDisplay.getLayout().getGroupId(), parentFolderId, "File_Upload");
//		
//		long repoId = folder.getRepositoryId();
//		List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(repoId, folder.getFolderId());
//    	
//    	for (FileEntry documentEntry : fileEntries) {
//    		System.out.println(documentEntry.getFileName());
//			System.out.println(documentEntry.getFileEntryId());
//		}
		
//		try {
//			List<DLFileEntry> fileEntries = DLFileEntryLocalServiceUtil.getFileEntries(0, DLFileEntryLocalServiceUtil.getFileEntriesCount());
//			
//			for (DLFileEntry fileEntry : fileEntries) {
//				System.out.println("File Name: " + fileEntry.getFileName());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
		
	
		
	}
	

 
@Activate
@Modified
protected void activate(Map<String, Object> properties) throws SchedulerException {
 
    try {
        String cronExpression = GetterUtil.getString(properties.get("cron.expression"), "cronExpression");
    	System.out.println("scheduler activating");
        System.out.println(" cronExpression: " + cronExpression);
 
        String listenerClass = getClass().getName();
        Trigger jobTrigger = TriggerFactoryUtil.createTrigger(listenerClass, listenerClass, new Date(), null, cronExpression, null);
 
        SchedulerEntryImpl schedulerEntryImpl = new SchedulerEntryImpl(listenerClass, jobTrigger);
      
      
 
        SchedulerEngineHelperUtil.register(this, schedulerEntryImpl, DestinationNames.SCHEDULER_DISPATCH);
 
    } catch (Exception e) {
    	
       e.printStackTrace();
    }
}


 
@Deactivate
protected void deactive() {
	System.out.println("scheduler deactivating");
    SchedulerEngineHelperUtil.unregister(this);
}

	}
