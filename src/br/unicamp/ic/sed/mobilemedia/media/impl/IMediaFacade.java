package br.unicamp.ic.sed.mobilemedia.media.impl;

import br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia;

public class IMediaFacade implements IMedia{

	private MediaMainController mediaMainController = new MediaMainController();
	
	public void showMediaList(String albumName , String mediaType ) {
		this.mediaMainController.showMediaList(albumName , mediaType );
	}
	
	public void showLastMediaList(){
		this.mediaMainController.showLastMediaList();
	}

	public void initCopyPhotoToAlbum( String mediaName , byte[] mediaBytes , Object media , String mediaType ) {
		this.mediaMainController.initCopyPhotoToAlbum( mediaName , mediaBytes , media , mediaType );
	}
	
//	public String getSelectedMediaName() {
//		return this.mediaMainController.getSelectedMediaName();
//	}
	

}
