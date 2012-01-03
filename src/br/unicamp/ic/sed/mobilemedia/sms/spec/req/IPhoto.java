//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms.spec.req;

import javax.microedition.lcdui.Image;

public interface IPhoto{
	
	public void initPhotoViewScreen(Image image, byte[] img);
	
	/**add Cosmos v6**/
	public String getImageName();
	public void showLastImage();
}

//#endif