//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.spec.req;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.photo.spec.excep.PersistenceMechanismException;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{
	
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws ImageNotFoundException, PersistenceMechanismException; 
 
	public IMediaData getMediaInfo( String imageName, String mediaType ) throws ImageNotFoundException, NullAlbumDataReference;
		
	public void updateImageInfo( IMediaData velhoID , IMediaData novoID ) throws InvalidImageDataException, PersistenceMechanismException;
}
//#endif