package br.unicamp.ic.sed.mobilemedia.media_album.impl;

import br.unicamp.ic.sed.mobilemedia.album.spec.req.IMedia;

public class IAdapterMediaAlbum implements IMedia {

	IManager manager = ComponentFactory.createInstance();

	public void showMediaList(String albumName,String mediaType) {
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia media =
			(br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia)
				manager.getRequiredInterface("IMedia");
		
		media.showMediaList(albumName,mediaType);
	}
}
