//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms_photo.impl;

import javax.microedition.lcdui.Image;

import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IPhoto;

public class IAdapterPhotoSms implements IPhoto {
	
	public void initPhotoViewScreen(Image image , byte[] img) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
			manager.getRequiredInterface("IPhoto");
		
		photo.initPhotoViewScreen(image, img);
	}

	public String getImageName() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
			manager.getRequiredInterface("IPhoto");
		
		return photo.getImageName();
	}

	public void showLastImage() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
			manager.getRequiredInterface("IPhoto");
		
		photo.showLastImage();
	}
}
//#endif