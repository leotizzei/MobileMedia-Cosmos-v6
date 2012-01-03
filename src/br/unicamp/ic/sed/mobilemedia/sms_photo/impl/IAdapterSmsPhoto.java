//#ifdef includeSmsFeature 

package br.unicamp.ic.sed.mobilemedia.sms_photo.impl;

import javax.microedition.lcdui.Command;

import br.unicamp.ic.sed.mobilemedia.main.ControllerInterface;
import br.unicamp.ic.sed.mobilemedia.photo.spec.req.ISms;

public class IAdapterSmsPhoto implements ISms {

	public boolean postCommand(Command command) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms sms = 
			(br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms) 
			manager.getRequiredInterface("ISms");
		
		return sms.postCommand(command);
	}
	
	public ControllerInterface getNextController() {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms sms = 
			(br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms) 
			manager.getRequiredInterface("ISms");
		
		return sms.getNextController();
	}

	public void setNextController(ControllerInterface controller) {
		IManager manager = ComponentFactory.createInstance();
		br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms sms = 
			(br.unicamp.ic.sed.mobilemedia.sms.spec.prov.ISms) 
			manager.getRequiredInterface("ISms");
		
		sms.setNextController(controller);
	}

}
//#endif