/**
 * CAT Core support plugin project.
 *
 * Copyright (C) 2016 New York City Department of Health and Mental Hygiene, Bureau of Immunization
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
 * SOFTWARE IS PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING,
 * BUT NOT LIMITED TO, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE COPYRIGHT HOLDERS, IF ANY, OR DEVELOPERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES, OR OTHER LIABILITY OF ANY KIND, ARISING FROM, OUT OF, OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information about this software, see https://www.hln.com/services/open-source/ or send
 * correspondence to ice@hln.com.
 */
package org.cdsframework.rs.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.cdsframework.rs.core.support.ConfigServiceConstantsCore;
import org.cdsframework.util.LogUtils;

/**
 *
 * @author sdn
 */
@Path(ConfigServiceConstantsCore.BASE_RS_ROOT)
public class ConfigServiceBase {

    private final static LogUtils logger = LogUtils.getLogger(ConfigServiceBase.class);
    public static String CAT_VERSION;
    public static String CAT_BUILD_TIMESTAMP;

    public ConfigServiceBase() {
        initializeMain();
    }

    public final void initializeMain() {
        final String METHODNAME = "initializeMain ";
        if (CAT_VERSION == null) {
            InputStream buildPropertiesStream = null;
            try {
                buildPropertiesStream = ConfigServiceBase.class.getClassLoader().getResourceAsStream("cat-build.properties");
                if (buildPropertiesStream != null) {
                    Properties buildProperties = new Properties();
                    buildProperties.load(buildPropertiesStream);
                    CAT_VERSION = buildProperties.getProperty("BUILD_VERSION");
                    CAT_BUILD_TIMESTAMP = buildProperties.getProperty("BUILD_TIMESTAMP");
                    logger.info(METHODNAME, "CAT_VERSION=", CAT_VERSION);
                    logger.info(METHODNAME, "CAT_BUILD_TIMESTAMP=", CAT_BUILD_TIMESTAMP);
                } else {
                    logger.error(METHODNAME, "buildPropertiesStream is null");
                }
            } catch (IOException e) {
                logger.error(METHODNAME, e);
            } finally {
                if (buildPropertiesStream != null) {
                    try {
                        buildPropertiesStream.close();
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            }
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("version")
    public String version() {
        final String METHODNAME = "version ";
        logger.info(METHODNAME, "called");
        return CAT_VERSION;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("build_timestamp")
    public String buildTimestamp() {
        final String METHODNAME = "buildTimestamp ";
        logger.info(METHODNAME, "called");
        return CAT_BUILD_TIMESTAMP;
    }

}
