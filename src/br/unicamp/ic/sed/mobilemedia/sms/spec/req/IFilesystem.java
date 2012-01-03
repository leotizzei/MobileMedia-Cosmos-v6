
//#ifdef includeSmsFeature 
package br.unicamp.ic.sed.mobilemedia.sms.spec.req;

import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.PersistenceMechanismException;

/**
 * In MobileMedia-Cosmos-v4, it was exchanged the usage of the datatype MediaData by the interface IMediaData
 * that is implemented by the MediaData datatype.
 * 
 *
 */
public interface IFilesystem{
	  
	public IMediaData getImageInfo( String imageName, String mediaType ) throws MediaNotFoundException, NullAlbumDataReference;
	public byte[] loadImageBytesFromRMS(String recordName, String imageName, int recordId) throws PersistenceMechanismException; 

}

//#endif