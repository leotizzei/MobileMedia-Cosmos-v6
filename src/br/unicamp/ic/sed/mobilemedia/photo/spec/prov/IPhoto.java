//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.spec.prov;

import javax.microedition.lcdui.Image;

public interface IPhoto{
	
	/**[MD][Cosmos][SMS]*/
	//#ifdef includeSmsFeature 
	public void initPhotoViewScreen(Image image, byte[] img);
	//#endif
	
	public void showImage( String albumName , String imageName );

	/** add Cosmos OO v6**/
	public String getImageName();
	public void showLastImage();
	
	
}
//#endif