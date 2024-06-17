package org.santanubrains.utiliy;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.santanubrains.domain.Credential;
import org.santanubrains.log4j.Log4jUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class IssueTokenImpl implements IssueToken{

	private static final Logger logger = Log4jUtil.getLogger(IssueTokenImpl.class);
	
	private KeyGenerator keyGenerator;
	
	@Inject
	public IssueTokenImpl(KeyGenerator keyGenerator) {
		super();
		this.keyGenerator = keyGenerator;
	}

	@Override
	public String generateJWTToken(UriInfo uriInfo, Credential credential) {
		
		Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(credential.getUsername())
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(30L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        logger.info("#### generating token for a key : " + jwtToken + " - " + key);
        return jwtToken;
	}
	
	private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
