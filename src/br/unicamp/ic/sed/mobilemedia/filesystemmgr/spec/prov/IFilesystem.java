package br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov;

import java.io.InputStream;

import javax.microedition.lcdui.Image;



import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.main.IMediaData;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{

	// #ifdef includeCopyPhoto
	public void addMediaData(String photoName, IMediaData mediaData, String albumName) throws InvalidImageDataException, PersistenceMechanismException;
	// #endif 
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname) throws InvalidImageDataException, PersistenceMechanismException;
	//#endif 
	
	public void addNewMediaToAlbum ( String mediaType, String imageName, String imagePath, String albumName ) throws InvalidImageDataException, PersistenceMechanismException; 
	
	public void createNewMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException, InvalidPhotoAlbumNameException; 
	
	public void deleteMedia ( String mediaType, String imageName, String albumName ) throws PersistenceMechanismException, ImageNotFoundException; 
	
	public void deleteMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException; 
	
	public String[] getAlbumNames (String mediaType  ); 
	
	//#ifdef Album
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws ImageNotFoundException, PersistenceMechanismException; 
	
	/**
	 * Added in MobileMedia-Cosmos-OO-v5
	 * @return an array of bytes related to the image specified by the parameters
	 */
	public byte[] loadImageBytesFromRMS(String recordName, String imageName, int recordId) throws PersistenceMechanismException;
	//#endif
	
	public IMediaData getMediaInfo( String imageName, String mediaType ) throws ImageNotFoundException, NullAlbumDataReference;
	
	public IMediaData[] getMedias( String albumName, String mediaType )throws UnavailablePhotoAlbumException; 
	
	public void resetMediaData ( String mediaType ) throws PersistenceMechanismException;
	
	public void updateMediaInfo( IMediaData velhoID , IMediaData novoID ) throws InvalidImageDataException, PersistenceMechanismException;

	/**add v6 OO cosmos**/
	//#ifdef includeMusic
	public InputStream getMusicFromRecordStore(String recordName , String musicName ) throws ImageNotFoundException, PersistenceMechanismException;
	public String getMusicType( String musicName )throws ImageNotFoundException;
	public void setMusicType( String musicName , String type ) throws ImageNotFoundException, InvalidImageDataException, PersistenceMechanismException, NullAlbumDataReference;
	//#endif
}