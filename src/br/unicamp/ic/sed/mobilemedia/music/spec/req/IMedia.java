//#ifdef includeMusic
package br.unicamp.ic.sed.mobilemedia.music.spec.req;

public interface IMedia {
	public void showLastMediaList();
	//#ifdef includeCopyPhoto
	public void initCopyPhotoToAlbum( String mediaName , byte[] mediaBytes , Object media , String mediaType );
	//#endif
}
//#endif