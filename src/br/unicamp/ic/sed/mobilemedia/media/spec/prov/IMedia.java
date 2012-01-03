package br.unicamp.ic.sed.mobilemedia.media.spec.prov;

public interface IMedia {

	public void showMediaList( String albumName , String mediaType );	
	
	/** add cosmos OO v6**/
	//#if includeCopyPhoto || includeSmsFeature
	public void initCopyPhotoToAlbum( String mediaName , byte[] mediaBytes , Object media , String mediaType );
	//#endif
	public void showLastMediaList();
}
