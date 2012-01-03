package br.unicamp.ic.sed.mobilemedia.album.spec.req;

import br.unicamp.ic.sed.mobilemedia.album.spec.excep.InvalidPhotoAlbumNameException;
import br.unicamp.ic.sed.mobilemedia.album.spec.excep.PersistenceMechanismException;

public interface IFilesystem{

	public void createNewMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException, InvalidPhotoAlbumNameException; 
		
	public void deleteMediaAlbum ( String mediaType, String albumName ) throws PersistenceMechanismException; 
	
	public String[] getAlbumNames (String mediaType  );  
 		
	public void resetMediaData ( String mediaType  ) throws PersistenceMechanismException;
	
}