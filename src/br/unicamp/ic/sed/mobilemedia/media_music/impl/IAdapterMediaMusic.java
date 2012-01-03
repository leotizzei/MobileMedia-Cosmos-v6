//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.media_music.impl;

import br.unicamp.ic.sed.mobilemedia.music.spec.req.IMedia;

public class IAdapterMediaMusic implements IMedia {

	IManager manager = ComponentFactory.createInstance();

	public void showLastMediaList() {
	
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia media = 
			(br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia)
				manager.getRequiredInterface("IMedia");
		
		media.showLastMediaList();
	}

	public void initCopyPhotoToAlbum(String mediaName, byte[] mediaBytes,
			Object mediaObj, String mediaType) {
		
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia media = 
			(br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia)
				manager.getRequiredInterface("IMedia");
		
		media.initCopyPhotoToAlbum(mediaName, mediaBytes, mediaObj, mediaType);	
	}
}
//#endif