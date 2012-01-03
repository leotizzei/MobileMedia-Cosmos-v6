/**
 * University of Campinas - Brazil
 * Institute of Computing
 * SED group (http://www.sed.ic.unicamp.br)
 *
 * date: February 2009
 * 
 */   
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import java.io.InputStream;

import javax.microedition.lcdui.Image;


//#ifdef includeMusic
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MultiMediaData;
//#endif

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem;
import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
 
class IFilesystemFacade implements IFilesystem{

	//#if Album && includeMusic
	public static final String IMAGE_MEDIA = "ImageMedia";
	public static final String MUSIC_MEDIA = "MusicMedia";
	//#endif
	
	//#ifdef Album
	private AlbumData imageAlbumData = new ImageAlbumData();
	//#endif
	
	//#ifdef includeMusic
	private AlbumData musicAlbumData = new MusicAlbumData();
	//#endif
	
	// #ifdef includeCopyPhoto
	public void addMediaData(String photoName, IMediaData mediaData,
			String albumName) throws InvalidImageDataException, PersistenceMechanismException {
		mediaData.setMediaLabel( photoName );
		
		//#if Album && includeMusic
		if( mediaData instanceof MultiMediaData )
			this.musicAlbumData.addMediaData( mediaData, albumName);
		else
			this.imageAlbumData.addMediaData( mediaData, albumName);
		//#elif Album
			this.imageAlbumData.addMediaData( mediaData, albumName);
		//#elif includeMusic
			this.musicAlbumData.addMediaData( mediaData, albumName);
		//#endif
	} 
	// #endif 
	
	/**[Original][cosmos sms]Included in cosmos v5*/
	// #ifdef includeSmsFeature
	public void addImageData(String photoname, byte[] img, String albumname) throws InvalidImageDataException, PersistenceMechanismException {
		this.imageAlbumData.addImageData(photoname, img, albumname);
	}
	//#endif
	
		
	public void addNewMediaToAlbum ( String mediaType, String imageName, String imagePath, String albumName) throws InvalidImageDataException, PersistenceMechanismException{	
		System.out.println("[IFilesystemFacade] -> mediaType: " + mediaType + " mediaName: "+ imageName +" imagePath: " + imagePath+ " albumName: " +albumName );
		
		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			this.imageAlbumData.addNewMediaToAlbum(imageName, imagePath, albumName);
		else
			this.musicAlbumData.addNewMediaToAlbum(imageName, imagePath, albumName);
		
		//#elif Album
		this.imageAlbumData.addNewMediaToAlbum(imageName, imagePath, albumName);
		
		//#elif includeMusic
		this.musicAlbumData.addNewMediaToAlbum(imageName, imagePath, albumName);
		//#endif

	
	} 
	
	public void createNewMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException, InvalidPhotoAlbumNameException{
		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			this.imageAlbumData.createNewAlbum(albumName);
		else
			this.musicAlbumData.createNewAlbum(albumName);
		
		//#elif Album
		this.imageAlbumData.createNewAlbum(albumName);
		
		//#elif includeMusic
		this.musicAlbumData.createNewAlbum(albumName);
		//#endif

	} 
	
	public void deleteMedia ( String mediaType, String imageName, String albumName ) throws PersistenceMechanismException, ImageNotFoundException{
		
		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			this.imageAlbumData.deleteMedia(imageName, albumName);
		else
			this.musicAlbumData.deleteMedia(imageName, albumName);
		
		//#elif Album
		this.imageAlbumData.deleteMedia(imageName, albumName);
		
		//#elif includeMusic
		this.musicAlbumData.deleteMedia(imageName, albumName);
		//#endif
		
	} 
	
	public void deleteMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException{
			
		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			this.imageAlbumData.deleteAlbum(albumName);
		else
			this.musicAlbumData.deleteAlbum(albumName);
		
		//#elif Album
		this.imageAlbumData.deleteAlbum(albumName);
		
		//#elif includeMusic
		this.musicAlbumData.deleteAlbum(albumName);
		//#endif

	} 
	
	public String[] getAlbumNames ( String mediaType  ){
		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			return imageAlbumData.getAlbumNames();
		else
			return musicAlbumData.getAlbumNames();
		//#elif Album
		return imageAlbumData.getAlbumNames();
		//#elif includeMusic
		return musicAlbumData.getAlbumNames();
		//#endif

	} 
	
	//#ifdef Album
	public Image getImageFromRecordStore ( String albumName, String imageName ) throws ImageNotFoundException, PersistenceMechanismException{
		return ((ImageAlbumData)imageAlbumData).getImageFromRecordStore(albumName, imageName);
	} 
	//#endif
	
	public IMediaData getMediaInfo(String imageName, String mediaType) throws ImageNotFoundException, NullAlbumDataReference {

		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			return imageAlbumData.getMediaInfo(imageName);
		else{
			IMediaData media = musicAlbumData.getMediaInfo(imageName);
			System.out.println( "GETMEDIAINFO: " + ((MultiMediaData)media).getTypeMedia() );
			return media;
		}
		//#elif Album
		return imageAlbumData.getMediaInfo(imageName);
		
		//#elif includeMusic
		return musicAlbumData.getMediaInfo(imageName);
		//#endif

	}

	
	public IMediaData[] getMedias ( String albumName, String mediaType ) throws UnavailablePhotoAlbumException{

		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			return imageAlbumData.getMedias(albumName);
		else
			return musicAlbumData.getMedias(albumName);
		
		//#elif Album
		return imageAlbumData.getMedias(albumName);
		
		//#elif includeMusic
		return musicAlbumData.getMedias(albumName);
		//#endif

	}

	
	public void resetMediaData ( String mediaType ) throws PersistenceMechanismException{
		//#if Album && includeMusic
		if( mediaType.equals( IFilesystemFacade.IMAGE_MEDIA ) )
			imageAlbumData.resetMediaData();
		else
			musicAlbumData.resetMediaData();
		
		//#elif Album
		imageAlbumData.resetMediaData();
		
		//#elif includeMusic
		musicAlbumData.resetMediaData();
		//#endif

	}

	public void updateMediaInfo(IMediaData velhoID, IMediaData novoID) throws InvalidImageDataException, PersistenceMechanismException {
		
		//#if Album && includeMusic
		if( velhoID instanceof MultiMediaData ){
			//System.out.println( "music" );
			this.musicAlbumData.updateMediaInfo(velhoID, novoID);
			//System.out.println( "novoId: "+novoID.getMediaLabel() + ":" + ((MultiMediaData)novoID).getTypeMedia() );
		}else{
			//System.out.println( "photo" );
			this.imageAlbumData.updateMediaInfo(velhoID, novoID);
		}
		//#elif Album
		this.imageAlbumData.updateMediaInfo(velhoID, novoID);
		
		//#elif includeMusic
		this.musicAlbumData.updateMediaInfo(velhoID, novoID);
		//#endif

	}
	

	/**
	 * Added in MobileMedia-Cosmos-OO-v5
	 * @return an array of bytes related to the image specified by the parameters
	 */
	//#ifdef Album
	public byte[] loadImageBytesFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {
		
		return this.imageAlbumData.loadMediaBytesFromRMS(recordName, recordId);
	}
	//#endif
	
	//#ifdef includeMusic
	public InputStream getMusicFromRecordStore(String recordName , String musicName ) throws ImageNotFoundException, PersistenceMechanismException{
		return ((MusicAlbumData)this.musicAlbumData).getMusicFromRecordStore(recordName, musicName);
	}
	
	public String getMusicType(String musicName) throws ImageNotFoundException {
		return ((MusicAlbumData)this.musicAlbumData).getMusicType( musicName );
	}
	
	public void setMusicType( String musicName , String type ) throws ImageNotFoundException, InvalidImageDataException, PersistenceMechanismException, NullAlbumDataReference{
		
		MediaData media = (MediaData) musicAlbumData.getMediaInfo(musicName);
		MultiMediaData musicMedia = new MultiMediaData( media , type );
		musicAlbumData.updateMediaInfo(media, musicMedia);
		
		((MusicAlbumData)this.musicAlbumData).setMusicType(musicName, type);
	}
	//#endif

	
	
}