//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.filesystemmgr_music.impl;

import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.music.spec.req.IFilesystem;
 

public class IAdapterFilesystemMusic implements IFilesystem {

	
	public IMediaData getMediaInfo(String imageName, String mediaType ) throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.ImageNotFoundException, br.unicamp.ic.sed.mobilemedia.music.spec.excep.NullAlbumDataReference {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getMediaInfo(imageName, mediaType );
		} catch (ImageNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.ImageNotFoundException( e.getMessage() );
		} catch (NullAlbumDataReference e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.NullAlbumDataReference(e);
		}
	}

	public void updateImageInfo(IMediaData velhoID, IMediaData novoID) throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.InvalidImageDataException, br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			filesystem.updateMediaInfo(velhoID, novoID);
		} catch (InvalidImageDataException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.InvalidImageDataException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}


	public InputStream getMusicFromRecordStore(String recordName,
			String musicName)
			throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.ImageNotFoundException,
			br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException {
		
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
		try {
			return filesystem.getMusicFromRecordStore(recordName, musicName);
		} catch (ImageNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.ImageNotFoundException( e.getMessage() );
		} catch (PersistenceMechanismException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException( e.getMessage() );
		}
	}


	public String getMusicType(String musicName)
			throws br.unicamp.ic.sed.mobilemedia.music.spec.excep.ImageNotFoundException {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem = (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)manager.getRequiredInterface("IFilesystem");
	
		try {
			return filesystem.getMusicType(musicName);
		} catch (ImageNotFoundException e) {
			throw new br.unicamp.ic.sed.mobilemedia.music.spec.excep.ImageNotFoundException( e.getMessage() );
		}
	}

}
//#endif