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

import javax.ws.rs.ApplicationPath;
import org.cdsframework.rs.base.BaseResourceConfig;
import org.cdsframework.rs.provider.CoreJacksonJsonProvider;
import static org.cdsframework.rs.support.CoreConfiguration.getInstanceProperties;
import org.cdsframework.rs.support.CoreRsConstants;
import org.cdsframework.util.StringUtils;

/**
 *
 * @author HLN Consulting, LLC
 */
@ApplicationPath(CoreRsConstants.RESOURCE_ROOT)
public class ApplicationConfig extends BaseResourceConfig {

    public ApplicationConfig() {
        super();
        
        CoreJacksonJsonProvider coreJacksonJsonProvider = new CoreJacksonJsonProvider();
        coreJacksonJsonProvider.registerDTOs("org.cdsframework.dto");
        register(coreJacksonJsonProvider);
        register(CoreRSService.class);         
        register(SessionRestService.class);
        register(ConfigServiceCore.class);
        register(ConfigServiceBase.class);
    }
    
}
