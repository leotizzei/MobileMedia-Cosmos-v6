//#ifdef includeSmsFeature
package br.unicamp.ic.sed.mobilemedia.sms_media.impl;

import br.unicamp.ic.sed.mobilemedia.sms.spec.req.IMedia;

public class IAdapterMediaSms implements IMedia {

	private IManager manager = ComponentFactory.createInstance();

	public void showLastMediaList() {
		br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia media = 
			(br.unicamp.ic.sed.mobilemedia.media.spec.prov.IMedia)
				manager.getRequiredInterface("IMedia");
		
		media.showLastMediaList();
		
	}


	
}
//#endif