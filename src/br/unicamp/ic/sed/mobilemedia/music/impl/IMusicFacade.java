//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.impl;

import br.unicamp.ic.sed.mobilemedia.music.spec.prov.IMusic;

class IMusicFacade implements IMusic{

	private MusicController mainPhotocontroller = new MusicController();

	public void PlayMusic(String albumName, String musicName) {
		this.mainPhotocontroller.PlayMusic(albumName, musicName);
	}

	
}
//#endif