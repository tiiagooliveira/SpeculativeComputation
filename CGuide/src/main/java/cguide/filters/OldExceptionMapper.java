package cguide.filters;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import cguide.OldAuthTokenException;

/**
 * Created by IntelliJ IDEA.
 * User: andre
 * Date: 12/2/12
 * Time: 7:44 PM
 */
@Provider
public class OldExceptionMapper implements ExceptionMapper<OldAuthTokenException> {

    @Override
	public Response toResponse(OldAuthTokenException e) {
        return Response.status(Response.Status.UNAUTHORIZED).entity("Token provided is too old. Please renew it.").build();
    }
}
