package br.unicamp.ic.sed.mobilemedia.media.spec.req;

import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.media.spec.excep.PersistenceMechanismException;


/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{

	//#ifdef includeCopyPhoto
	public void addMediaData(String photoName, IMediaData mediaData, String albumName) throws InvalidImageDataException, PersistenceMechanismException;
	//#endif
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname) throws InvalidImageDataException, PersistenceMechanismException;
	//#endif
	
	public IMediaData getMediaInfo( String imageName , String mediaType ) throws ImageNotFoundException, NullAlbumDataReference;
	public void addNewMediaToAlbum ( String mediaType , String imageName, String imagePath, String albumName ) throws InvalidImageDataException, PersistenceMechanismException;
	public void updateMediaInfo( IMediaData velhoID , IMediaData novoID ) throws InvalidImageDataException, PersistenceMechanismException;
	public void deleteMedia ( String mediaType , String imageName, String albumName ) throws PersistenceMechanismException, ImageNotFoundException;
	public IMediaData[] getMedias( String albumName , String mediaType )throws UnavailablePhotoAlbumException;

	//#ifdef includeMusic
	public void setMusicType( String musicName , String type ) throws ImageNotFoundException;
	//#endif
}