package br.com.mymarket.helpers;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
/****
 * 
 * @author iago.quirino
 *	Classe que valida se o numero de telefone tem conta no aplicativo ou se já foi cadastrado anteriormente.
 */
public class OauthHelper {

	private Context context;
	
	public OauthHelper(Context context){
		this.context = context;
	}
	
	
	public boolean verifyPhoneNumber(String phone){
		return false;		
	}


	public boolean acessarAplicativo(String phone) {
		if(PhoneNumberUtils.isWellFormedSmsAddress(phone))
		{
			processarAcesso(phone);
			return true;
		}else{
			return false;
		}
		
	}


	private void processarAcesso(String phone) {
		// TODO Auto-generated method stub
		
	}
}
