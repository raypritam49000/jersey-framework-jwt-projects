package org.santanubrains.utiliy;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

public class SimpleKeyGenerator implements KeyGenerator {

	@Override
	public Key generateKey() {

		String keyString = "simplekey";
		Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");

		return key;
	}

}
