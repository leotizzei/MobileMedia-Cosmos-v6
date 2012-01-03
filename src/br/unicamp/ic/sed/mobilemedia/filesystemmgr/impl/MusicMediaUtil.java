// #ifdef includeMusic
// [NC] Added in the scenario 07
package br.unicamp.ic.sed.mobilemedia.filesystemmgr.impl;

import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.dt.MultiMediaData;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidArrayFormatException;
import br.unicamp.ic.sed.mobilemedia.filesystemmgr.spec.excep.InvalidImageDataException;
import br.unicamp.ic.sed.mobilemedia.main.IMediaData;

public class MusicMediaUtil extends MediaUtil {

	public byte[] getBytesFromMediaInfo(IMediaData ii)
			throws InvalidImageDataException {
		try {
			byte[] mediadata = super.getBytesFromMediaInfo(ii);
			if (ii instanceof MultiMediaData) {
				
				String byteString = new String(mediadata);
				byteString = byteString.concat(DELIMITER);

				byteString = byteString.concat(((MultiMediaData) ii).getTypeMedia());
					
				return byteString.getBytes();

			}
			return mediadata;
		} catch (Exception e) {
			throw new InvalidImageDataException(
					"The provided data are not valid");
		}
		
	}

	public IMediaData getMultiMediaInfoFromBytes(byte[] bytes)
			throws InvalidArrayFormatException {
		MediaData mediadata =  super.getMediaInfoFromBytes(bytes);
		String iiString = new String(bytes);
		
		
		int startIndex = getEndIndex() + 1;
		if (getEndIndex()==iiString.length()){
			return mediadata;	
		}
		setEndIndex(iiString.indexOf(DELIMITER, startIndex));
		
		if (getEndIndex() == -1)
			setEndIndex(iiString.length());
		String mediaType = iiString.substring(startIndex, getEndIndex());
		MultiMediaData mmedi = new MultiMediaData(mediadata,mediaType);
		return mmedi;
	}
}
//#endif