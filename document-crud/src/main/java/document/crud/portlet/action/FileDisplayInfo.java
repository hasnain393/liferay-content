package document.crud.portlet.action;

import com.liferay.portal.kernel.repository.model.FileEntry;

public class FileDisplayInfo {
	
	 private FileEntry fileEntry;
	    private String downloadUrl;

	    public FileDisplayInfo(FileEntry fileEntry, String downloadUrl) {
	        this.fileEntry = fileEntry;
	        this.downloadUrl = downloadUrl;
	    }

	    // Getters
	    public FileEntry getFileEntry() {
	        return fileEntry;
	    }

	    public String getDownloadUrl() {
	        return downloadUrl;
	    }

}
