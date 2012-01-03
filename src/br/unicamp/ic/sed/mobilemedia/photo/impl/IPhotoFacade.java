//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.photo.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto;

class IPhotoFacade implements IPhoto{

	private PhotoController mainPhotocontroller = new PhotoController();

	//#ifdef includeSmsFeature 
	public void initPhotoViewScreen(Image image , byte[] img ) {
		this.mainPhotocontroller.initPhotoViewScreen(image, img);
	}
	//#endif

	public String getImageName() {
		return this.mainPhotocontroller.getImageName();
	}
	
	public void showLastImage() {
		this.mainPhotocontroller.showLastImage();
	}
	
	public void showImage(String albumName, String imageName) {
		this.mainPhotocontroller.showImage(albumName, imageName);
	}

	
}
//#endif