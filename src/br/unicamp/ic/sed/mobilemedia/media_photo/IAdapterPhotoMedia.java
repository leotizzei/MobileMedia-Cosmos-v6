//#ifdef Album
package br.unicamp.ic.sed.mobilemedia.media_photo;

import br.unicamp.ic.sed.mobilemedia.media.spec.req.IPhoto;

public class IAdapterPhotoMedia implements IPhoto {

	IManager manager = ComponentFactory.createInstance();
	
	public void showPhoto(String albumName , String imageName) {
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
				manager.getRequiredInterface("IPhoto");
		
		photo.showImage(albumName, imageName);
		
	}

	public void showLastImage() {
		br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto photo = 
			(br.unicamp.ic.sed.mobilemedia.photo.spec.prov.IPhoto)
				manager.getRequiredInterface("IPhoto");
		
		photo.showLastImage();
	}


}
//#endif