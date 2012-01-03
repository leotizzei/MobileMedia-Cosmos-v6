//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.spec.req;

public interface IMedia {
	
	public void showLastMediaList();
	//#if includeCopyPhoto || includeSmsFeature
	public void initCopyPhotoToAlbum( String mediaName , byte[] mediaBytes , Object media , String mediaType );
	//#endif
}
//#endif