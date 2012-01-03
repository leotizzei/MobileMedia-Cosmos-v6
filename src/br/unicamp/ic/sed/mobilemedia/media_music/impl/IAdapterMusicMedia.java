//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.media_music.impl;

import br.unicamp.ic.sed.mobilemedia.media.spec.req.IMusic;

public class IAdapterMusicMedia implements IMusic {

	IManager manager = ComponentFactory.createInstance();

	public void PlayMusic(String albumName, String musicName) {
		br.unicamp.ic.sed.mobilemedia.music.spec.prov.IMusic music = 
			(br.unicamp.ic.sed.mobilemedia.music.spec.prov.IMusic)
				manager.getRequiredInterface("IMusic");
		
		music.PlayMusic(albumName, musicName);
	}
	


}
//#endif