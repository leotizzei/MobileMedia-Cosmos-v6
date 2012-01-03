package br.unicamp.ic.sed.mobilemedia.media_album.impl;

import br.unicamp.ic.sed.mobilemedia.media.spec.req.IAlbum;

public class IAdapterAlbumMedia implements IAlbum {

	IManager manager = ComponentFactory.createInstance();

	public void reinitAlbumListScreen() {
		br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum album
		= (br.unicamp.ic.sed.mobilemedia.album.spec.prov.IAlbum)
			manager.getRequiredInterface("IAlbum");
	
		album.reinitAlbumListScreen();
	
	}
	


}
