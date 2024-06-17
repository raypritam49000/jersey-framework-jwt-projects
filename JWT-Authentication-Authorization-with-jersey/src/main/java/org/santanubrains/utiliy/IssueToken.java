package org.santanubrains.utiliy;

import javax.ws.rs.core.UriInfo;

import org.santanubrains.domain.Credential;

public interface IssueToken {
	
	public String generateJWTToken(UriInfo uriInfo, Credential credential);
}
