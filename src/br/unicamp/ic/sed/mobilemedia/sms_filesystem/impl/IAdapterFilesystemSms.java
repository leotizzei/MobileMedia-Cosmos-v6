//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms_filesystem.impl;

import br.unicamp.ic.sed.mobilemedia.main.IMediaData;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.MediaNotFoundException;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.NullAlbumDataReference;
import br.unicamp.ic.sed.mobilemedia.sms.spec.excep.PersistenceMechanismException;
import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IFilesystem;

public class IAdapterFilesystemSms implements IFilesystem {

	public IMediaData getImageInfo(String imageName , String mediaType )
			throws MediaNotFoundException, NullAlbumDataReference {
		
		try{
			IManager manager = ComponentFactory.createInstance();
			br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem
				= (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)
					manager.getRequiredInterface("IFilesystem");
			
			return filesystem.getMediaInfo(imageName, mediaType );
		
		}catch (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.ImageNotFoundException e) {
			throw new MediaNotFoundException( e.getMessage() );
		}catch (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.NullAlbumDataReference e) {
			throw new NullAlbumDataReference( e.getMessage() );
		}
		
	}

	public byte[] loadImageBytesFromRMS(String recordName, String imageName,
			int recordId) throws PersistenceMechanismException {
		
		try{
			IManager manager = ComponentFactory.createInstance();
			br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem filesystem
				= (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.prov.IFilesystem)
					manager.getRequiredInterface("IFilesystem");
		
			return filesystem.loadImageBytesFromRMS(recordName, imageName, recordId);
		}catch (br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.PersistenceMechanismException e) {
			throw new PersistenceMechanismException( e.getMessage() );
		}
		
	}

}
//#endif