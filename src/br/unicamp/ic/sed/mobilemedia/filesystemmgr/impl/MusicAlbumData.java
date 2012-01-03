// #ifdef includeMusic
// [NC] Added in the scenario 07
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MultiMediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.IMediaData;

public class MusicAlbumData extends AlbumData {
	
	public MusicAlbumData() {
		mediaAccessor = new MusicMediaAccessor();
	}
	
	public InputStream getMusicFromRecordStore(String recordStore, String musicName) throws ImageNotFoundException, PersistenceMechanismException {

		IMediaData mediaInfo = null;
		mediaInfo = mediaAccessor.getMediaInfo(musicName);
		//Find the record ID and store name of the image to retrieve
		int mediaId = mediaInfo.getForeignRecordId();
		String album = mediaInfo.getParentAlbumName();
		//Now, load the image (on demand) from RMS and cache it in the hashtable
		byte[] musicData = (mediaAccessor).loadMediaBytesFromRMS(album, mediaId);
		return new ByteArrayInputStream(musicData);

	}
	
	public String getMusicType(String musicName) throws ImageNotFoundException {
		
		MultiMediaData music = (MultiMediaData) this.getMediaInfo(musicName);
		return music.getTypeMedia();
	}
	
	public void setMusicType( String musicName , String type ) throws ImageNotFoundException{
		MultiMediaData music = (MultiMediaData) this.getMediaInfo(musicName);
		music.setTypeMedia( type );
	}
}
//#endif
