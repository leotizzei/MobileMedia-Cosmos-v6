/*
 * Created on Sep 28, 2004
 */
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import javax.microedition.rms.RecordStoreException;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.UnavailablePhotoAlbumException;
import br.unicamp.ic.sed.mobilemedia.main.IMediaData;

/**
 * @author tyoung
 * 
 * This class represents the data model for Photo Albums. A Photo Album object
 * is essentially a list of photos or images, stored in a Hashtable. Due to
 * constraints of the J2ME RecordStore implementation, the class stores a table
 * of the images, indexed by an identifier, and a second table of image metadata
 * (ie. labels, album name etc.)
 * 
 * This uses the ImageAccessor class to retrieve the image data from the
 * recordstore (and eventually file system etc.)
 */
public abstract class AlbumData {

	protected MediaAccessor mediaAccessor;
	
	/**
	 *  Load any photo albums that are currently defined in the record store
	 */
	public String[] getAlbumNames() {
		//Shouldn't load all the albums each time
		//Add a check somewhere in ImageAccessor to see if they've been
		//loaded into memory already, and avoid the extra work...
		try {
			mediaAccessor.loadAlbums();
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
		} catch (PersistenceMechanismException e) {
			e.printStackTrace();
		}
		return mediaAccessor.getAlbumNames();
	}

	/**
	 *  Get all images for a given Photo Album that exist in the Record Store.
	 * @throws UnavailablePhotoAlbumException 
	 * @throws InvalidImageDataException 
	 * @throws PersistenceMechanismException 
	 */
	public IMediaData[] getMedias(String recordName) throws UnavailablePhotoAlbumException  {
		IMediaData[] result;
		try {
			result = mediaAccessor.loadMediaDataFromRMS(recordName);
		} catch (PersistenceMechanismException e) {
			throw new UnavailablePhotoAlbumException( e.getMessage() );
		} catch (InvalidImageDataException e) {
			throw new UnavailablePhotoAlbumException( e.getMessage() );
		}
		return result;
	}

	/**
	 *  Define a new user photo album. This results in the creation of a new
	 *  RMS Record store.
	 * @throws PersistenceMechanismException 
	 * @throws InvalidPhotoAlbumNameException 
	 */
	public void createNewAlbum(String albumName) throws PersistenceMechanismException, InvalidPhotoAlbumNameException {
		mediaAccessor.createNewAlbum(albumName);
	}
	
	/**
	 * @param albumName
	 * @throws PersistenceMechanismException
	 */
	public void deleteAlbum(String albumName) throws PersistenceMechanismException{
		mediaAccessor.deleteAlbum(albumName);
	}

	/**
	 * @param label
	 * @param path
	 * @param album
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	public void addNewMediaToAlbum(String label, String path, String album) throws InvalidImageDataException, PersistenceMechanismException{
		mediaAccessor.addMediaData(label, path, album);
		mediaAccessor.reLoadMediaDataFromRMS(album);
	}
	
	// #ifdef includeCopyPhoto
	/**
	 * @param mediaData
	 * @param albumname
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	public void addMediaData(IMediaData mediaData, String albumname) throws InvalidImageDataException, PersistenceMechanismException {
		mediaAccessor.addMediaData(mediaData, albumname);
	}
	// #endif

	// #ifdef includeSmsFeature
	/* [NC] Added in scenario 06 */
	/**
	 * @param photoname
	 * @param imgdata
	 * @param albumname
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	public void addImageData(String photoName, byte[] imgdata, String albumName) throws InvalidImageDataException, PersistenceMechanismException {
		if (mediaAccessor instanceof ImageMediaAccessor)
			try {
				((ImageMediaAccessor)mediaAccessor).addImageFromSms(photoName, albumName, imgdata);
			} catch (RecordStoreException e) {
				throw new PersistenceMechanismException( e.getMessage() );
			}
	}
	// #endif
	
	/**
	 *  Delete a photo from the photo album. This permanently deletes the image from the record store
	 * @throws ImageNotFoundExcepPhototion 
	 * @throws PersistenceMechanismException 
	 */
	public void deleteMedia(String mediaName, String storeName) throws PersistenceMechanismException, ImageNotFoundException {
			mediaAccessor.deleteSingleMediaFromRMS( storeName , mediaName );
	}
	
	/**
	 *  Reset the image data for the application. This is a wrapper to the ImageAccessor.resetImageRecordStore
	 *  method. It is mainly used for testing purposes, to reset device data to the default album and photos.
	 * @throws PersistenceMechanismException 
	 * @throws InvalidImageDataException 
	 */
	public void resetMediaData() throws PersistenceMechanismException {
		try {
			mediaAccessor.resetRecordStore();
		} catch (InvalidImageDataException e) {
			e.printStackTrace();
		}
	}

	/**
	 * [EF] Added in order to have access to ImageData
	 * @param imageName
	 * @return
	 * @throws ImageNotFoundException
	 */
	public IMediaData getMediaInfo(String imageName) throws ImageNotFoundException {
		return mediaAccessor.getMediaInfo(imageName);
	}

	/**
	 * @param recordName
	 * @return
	 * @throws PersistenceMechanismException
	 * @throws InvalidImageDataException
	 */
	public IMediaData[] loadMediaDataFromRMS(String recordName) throws PersistenceMechanismException, InvalidImageDataException {
		return mediaAccessor.loadMediaDataFromRMS(recordName);
	}
	
	/**
	 * @param oldData
	 * @param newData
	 * @return
	 * @throws InvalidImageDataException
	 * @throws PersistenceMechanismException
	 */
	public boolean updateMediaInfo(IMediaData oldData, IMediaData newData) throws InvalidImageDataException, PersistenceMechanismException {
		return mediaAccessor.updateMediaInfo(oldData, newData);
	}

	/**
	 * @param recordName
	 * @param recordId
	 * @return
	 * @throws PersistenceMechanismException
	 */
	public byte[] loadMediaBytesFromRMS(String recordName, int recordId) throws PersistenceMechanismException {
		return mediaAccessor.loadMediaBytesFromRMS(recordName, recordId);
	}
}