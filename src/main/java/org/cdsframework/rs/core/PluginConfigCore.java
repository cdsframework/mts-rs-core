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

import org.cdsframework.rs.core.support.CatResourceConfig;
import org.cdsframework.dto.AppDTO;
import org.cdsframework.dto.NotificationDTO;
import org.cdsframework.dto.NotificationLogDTO;
import org.cdsframework.dto.NotificationRecipientDTO;
import org.cdsframework.dto.NotificationReleaseNoteDTO;
import org.cdsframework.dto.NotificationStateDTO;
import org.cdsframework.dto.SecurityPermissionDTO;
import org.cdsframework.dto.SecuritySchemeDTO;
import org.cdsframework.dto.SecuritySchemeRelMapDTO;
import org.cdsframework.dto.SessionDTO;
import org.cdsframework.dto.SystemPropertyDTO;
import org.cdsframework.dto.UserDTO;
import org.cdsframework.dto.UserPreferenceDTO;
import org.cdsframework.dto.UserSecurityMapDTO;
import org.cdsframework.rs.base.BasePluginConfig;

/**
 *
 * @author sdn
 */
public class PluginConfigCore extends BasePluginConfig {

    public PluginConfigCore(String baseCrudUri) {
        super(baseCrudUri);
        registerConfig(AppDTO.class, new CatResourceConfig(AppDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "AppDTO initialize ";
                logger.info(METHODNAME);
                setBaseHeader("Application");
                addDataTableColumn("appName");
                addDataTableColumn("description");
                addDataTableColumn("lastModDatetime");
            }
        });

        
        registerConfig(SystemPropertyDTO.class, new CatResourceConfig(SystemPropertyDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "SystemPropertyDTO initialize ";
                logger.info(METHODNAME);
                setBaseHeader("System Property");
                addDataTableColumn("name");
                addDataTableColumn("group");
                addDataTableColumn("scope");
                addDataTableColumn("value");       
                addDataTableColumn("mtsOnly");                       
            }
        });
        
        
        registerConfig(UserDTO.class, new CatResourceConfig(UserDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "UserDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("username");
                addDataTableColumn("firstName");
                addDataTableColumn("lastName");
                addDataTableColumn("email");
                addDataTableColumn("lastModDatetime");
            }
        });

        registerConfig(SessionDTO.class, new CatResourceConfig(SessionDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "SessionDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("sessionId");
                addDataTableColumn("appDTO.appName", "Application");
                addDataTableColumn("userDTO.username", "Username");
                addDataTableColumn("proxy");
                addDataTableColumn("proxyUserDTO.username", "Proxy Username");
                addDataTableColumn("createDatetime");
                addDataTableColumn("lastModDatetime");
            }
        });

        registerConfig(UserPreferenceDTO.class, new CatResourceConfig(UserPreferenceDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "UserPreferenceDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("name");
                addDataTableColumn("value");
                addDataTableColumn("type");
                addDataTableColumn("sessionPreference");
                addDataTableColumn("sessionPersistent");
                addDataTableColumn("defaultValue");
                addDataTableColumn("userEditable");
            }
        });

        registerConfig(UserSecurityMapDTO.class, new CatResourceConfig(UserSecurityMapDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "UserSecurityMapDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("securitySchemeDTO.schemeName");
                addDataTableColumn("securitySchemeDTO.description");
            }
        });

        registerConfig(SecuritySchemeDTO.class, new CatResourceConfig(SecuritySchemeDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "SecuritySchemeDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("schemeName");
                addDataTableColumn("description");
            }
        });

        registerConfig(SecurityPermissionDTO.class, new CatResourceConfig(SecurityPermissionDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "SecurityPermissionDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("cascade");
                addDataTableColumn("deny");
                addDataTableColumn("permissionType");
                addDataTableColumn("permissionClass");
            }
        });

        registerConfig(SecuritySchemeRelMapDTO.class, new CatResourceConfig(SecuritySchemeRelMapDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "SecuritySchemeRelMapDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("relatedSecuritySchemeDTO.schemeName");
                addDataTableColumn("relatedSecuritySchemeDTO.description");
            }
        });

        registerConfig(NotificationDTO.class, new CatResourceConfig(NotificationDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "NotificationDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("displayId");
                addDataTableColumn("name");
                addDataTableColumn("status");
                addDataTableColumn("lastModDatetime", "Last Modified");
            }
        });

        registerConfig(NotificationRecipientDTO.class, new CatResourceConfig(NotificationRecipientDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "NotificationRecipientDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("recipientType", "Type");
                addDataTableColumn("securitySchemeDTO.schemeName", "Group");
                addDataTableColumn("userDTO", "User");
            }
        });

        registerConfig(NotificationLogDTO.class, new CatResourceConfig(NotificationLogDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "NotificationLogDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("status");
                addDataTableColumn("type");
                addDataTableColumn("lastModDatetime");
            }
        });

        registerConfig(NotificationStateDTO.class, new CatResourceConfig(NotificationStateDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "NotificationStateDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("messageTitle", "Subject");
                addDataTableColumn("state", "Status");
                addDataTableColumn("updateTime", "Message Time");
            }
        });

        registerConfig(NotificationReleaseNoteDTO.class, new CatResourceConfig(NotificationReleaseNoteDTO.class) {
            @Override
            public void initialize() {
                final String METHODNAME = "NotificationLogDTO initialize ";
                logger.info(METHODNAME);
                addDataTableColumn("title");
                addDataTableColumn("description");
            }
        });

    }
}
