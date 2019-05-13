/*
 * Copyright (C) 2017 New York City Department of Health and Mental Hygiene, Bureau of Immunization
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

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.cdsframework.client.MtsMGRClient;
import org.cdsframework.dto.NotificationReleaseNoteFileDTO;
import org.cdsframework.dto.PropertyBagDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.exceptions.AuthenticationException;
import org.cdsframework.exceptions.AuthorizationException;
import org.cdsframework.exceptions.ConstraintViolationException;
import org.cdsframework.exceptions.MtsException;
import org.cdsframework.exceptions.NotFoundException;
import org.cdsframework.exceptions.ValidationException;
import org.cdsframework.rs.GeneralRSService;
import org.cdsframework.rs.support.CoreConfiguration;
import org.cdsframework.rs.support.CoreRsConstants;

/**
 *
 * @author HLN Consulting, LLC
 */
@Path(CoreRsConstants.GENERAL_RS_ROOT)
public class CoreRSService extends GeneralRSService {

    public CoreRSService() {
        super(CoreRSService.class);
    }

    /**
     *
     * @param changePasswordForm
     * @return
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("changepassword")
    public boolean changePasword(ChangePasswordForm changePasswordForm)
            throws AuthenticationException, AuthorizationException, ConstraintViolationException, ValidationException, NotFoundException, MtsException {
        final String METHODNAME = "changePasword ";
        String userName = changePasswordForm.getUserName();
        String passwordToken = changePasswordForm.getPasswordToken();
        String currentPassword = changePasswordForm.getCurrentPassword();
        String newPassword = changePasswordForm.getNewPassword();
        String confirmPassword = changePasswordForm.getConfirmPassword();
        logger.info(METHODNAME, "userName=", userName);
        return MtsMGRClient.getSecurityMGR(CoreConfiguration.isMtsUseRemote()).changePassword(userName, currentPassword, passwordToken, newPassword, confirmPassword);
    }

    /**
     *
     * @param userName
     * @return 
     * @throws AuthenticationException
     * @throws AuthorizationException
     * @throws ConstraintViolationException
     * @throws ValidationException
     * @throws NotFoundException
     * @throws MtsException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("forgotpassword")
    public boolean forgotPasword(@QueryParam("userName") String userName)
            throws AuthenticationException, AuthorizationException, ConstraintViolationException, ValidationException, NotFoundException, MtsException {
        final String METHODNAME = "forgotPasword ";
        logger.info(METHODNAME, "userName=", userName);
        return MtsMGRClient.getSecurityMGR(CoreConfiguration.isMtsUseRemote()).forgotPassword(userName);
    }

    @GET
    @Path("notificationreleasenotefiles/download/{fileId}")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response downloadNotificationReleaseNoteFile(
            @PathParam("fileId") final String fileId,
            @QueryParam(CoreRsConstants.QUERYPARMSESSION) String sessionId)
            throws ValidationException, NotFoundException, MtsException, AuthenticationException, AuthorizationException, ConstraintViolationException,
            NamingException, URISyntaxException, IOException {
        final String METHODNAME = "downloadNotificationReleaseNoteFile ";
        String data = "fileId=" + fileId + "; sessionId=" + sessionId;
        logger.info(METHODNAME, "data=", data);

        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setSessionId(sessionId);

        Response response;
        PropertyBagDTO propertyBagDTO = new PropertyBagDTO();
        propertyBagDTO.setQueryClass("ByteArrayByFileId");

        NotificationReleaseNoteFileDTO notificationReleaseNoteFileDTO = new NotificationReleaseNoteFileDTO();
        notificationReleaseNoteFileDTO.setFileId(fileId);

        byte[] fileData = getGeneralMGR().findObjectByQuery(notificationReleaseNoteFileDTO, sessionDTO, byte[].class, propertyBagDTO);

        notificationReleaseNoteFileDTO = getGeneralMGR().findByPrimaryKey(notificationReleaseNoteFileDTO, sessionDTO, new PropertyBagDTO());

        StreamingOutput streamingOutput = (OutputStream out) -> {
            out.write(fileData);
            out.flush();
        };
        Response.ResponseBuilder responseBuilder = Response.ok(streamingOutput);
        responseBuilder.header("content-disposition", "attachment; filename=" + notificationReleaseNoteFileDTO.getSourceFileName());
        responseBuilder.header("content-type", notificationReleaseNoteFileDTO.getMimeType());
        response = responseBuilder.build();

        return response;
    }

}
