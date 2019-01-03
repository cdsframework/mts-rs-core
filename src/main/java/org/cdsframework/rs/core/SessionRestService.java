/*
 * Copyright (C) 2014 New York City Department of Health and Mental Hygiene, Bureau of Immunization
 * Contributions by HLN Consulting, LLC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version. You should have received a copy of the GNU Lesser
 * General Public License along with this program. If not, see <http://www.gnu.org/licenses/> for more
 * details.
 *
 * The above-named contributors (HLN Consulting, LLC) are also licensed by the New York City
 * Department of Health and Mental Hygiene, Bureau of Immunization to have (without restriction,
 * limitation, and warranty) complete irrevocable access and rights to this project.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; THE
 *
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about the this software, see http://www.hln.com/ice or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.rs.core;

import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.ejb.local.GeneralMGRInterface;
import org.cdsframework.ejb.local.SecurityMGRInterface;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.rs.support.CoreConfiguration;
import org.cdsframework.rs.support.CoreRsConstants;
import org.cdsframework.client.MtsMGRClient;
import org.cdsframework.security.UserSecuritySchemePermissionMap;
import org.cdsframework.util.LogUtils;

/**
 * REST Web Service
 *
 * @author HLN Consulting, LLC
 */
@Path(CoreRsConstants.SESSION_RS_ROOT)
public class SessionRestService {

    private final static LogUtils logger = LogUtils.getLogger(SessionRestService.class);

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String login(@FormParam("username") String username,
            @FormParam("password") String password,
            @FormParam("applicationName") String applicationName)
            throws MtsException, AuthenticationException, AuthorizationException {
        return getSession(username, password, applicationName);
    }

    @POST
    @Path("{sessionId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getProxiedUserSession(@PathParam("sessionId") String sessionId, String userId)
            throws MtsException, AuthenticationException, AuthorizationException, ValidationException, NotFoundException {
        final String METHODNAME = "getProxiedUserSession ";
        if (logger.isDebugEnabled()) {
            logger.debug(METHODNAME, "sessionId=", sessionId, " userId=", userId);
        }
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionId);
//        sessionDTO = getGeneralMGRClient().findByPrimaryKey(sessionDTO, sessionDTO, new PropertyBagDTO());
        sessionDTO = getGeneralMGR().findByPrimaryKey(sessionDTO, sessionDTO, new PropertyBagDTO());
        return getSecurityMGR().getProxiedUserSession(userId, sessionDTO).getSessionId();

    }

    @DELETE
    @Path("{sessionId}")
    public boolean logout(@PathParam("sessionId") String sessionId)
            throws MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "logout ";
        boolean loggedOut = false;
        try {
            // The call absorbes the NotFoundException so bad sessions will always be successful
            SessionDTO sessionDTO = new SessionDTO();
            sessionDTO.setSessionId(sessionId);
            getSecurityMGR().logout(sessionDTO);
            loggedOut = true;
        } catch (MtsException | AuthenticationException e) {
            logger.error(METHODNAME, "An Unexpected Exception has occurred; Message: " + e.getMessage(), e);
            throw e;
        }
        return loggedOut;

    }

    @GET
    @Path("{sessionId}")
    public boolean isSessionValid(@PathParam("sessionId") String sessionId) throws AuthenticationException {
        final String METHODNAME = "isSessionValid ";
        boolean sessionValid = false;
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionId);
        try {
            sessionValid = getSecurityMGR().isSessionValid(sessionDTO);
        } catch (MtsException ex) {
            // The call above never throws this in the code but in the signature it does so
            // it needs to be caught and logged even though it will never be thrown
            logger.error(METHODNAME, "An ", ex.getClass().getSimpleName(), " has occurred; Message: ", ex.getMessage(), ex);
        }
        return sessionValid;
    }

    @GET
    @Path("{sessionId}/userpermissions")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, UserSecuritySchemePermissionMap> userSecuritySchemePermissions(@PathParam("sessionId") String sessionId) throws AuthenticationException, AuthorizationException, MtsException {
        final String METHODNAME = "userSecuritySchemePermissions ";
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionId);
        return getSecurityMGR().getUserSecuritySchemePermissionMaps(sessionDTO);
    }

    private SecurityMGRInterface getSecurityMGR() throws MtsException {
        return MtsMGRClient.getSecurityMGR(CoreConfiguration.isMtsUseRemote());
    }

    private GeneralMGRInterface getGeneralMGR() throws MtsException {
        return MtsMGRClient.getGeneralMGR(CoreConfiguration.isMtsUseRemote());
    }

    private String getSession(String username, String password, String applicationName) throws MtsException, AuthenticationException, AuthorizationException {
        final String METHODNAME = "getSession ";
        logger.debug(METHODNAME, "username=", username, " applicationName=", applicationName);

        String sessionId = null;
        try {
            SessionDTO sessionDTO = getSecurityMGR().login(username, password, applicationName);
            sessionId = sessionDTO.getSessionId();
            if (logger.isDebugEnabled()) {
                logger.debug(METHODNAME + "sessionId=" + sessionId);
            }
        } catch (MtsException | AuthenticationException | AuthorizationException e) {
            logger.error(METHODNAME, "An Unexpected Exception has occurred; Message: " + e.getMessage(), e);
            throw e;
        }
        return sessionId;
    }

}
