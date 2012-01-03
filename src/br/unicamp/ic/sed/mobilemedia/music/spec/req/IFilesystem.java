//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.spec.req;

import java.io.InputStream;

import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.music.spec.excep.PersistenceMechanismException;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{
 
	public IMediaData getMediaInfo( String imageName , String mediaType ) throws ImageNotFoundException, NullAlbumDataReference;
		
	public void updateImageInfo( IMediaData velhoID , IMediaData novoID ) throws InvalidImageDataException, PersistenceMechanismException;

	/**add v6 OO cosmos**/
	//#ifdef includeMusic
	public InputStream getMusicFromRecordStore(String recordName , String musicName ) throws ImageNotFoundException, PersistenceMechanismException;
	public String getMusicType( String musicName )throws ImageNotFoundException;
	//#endif
}
//#endif