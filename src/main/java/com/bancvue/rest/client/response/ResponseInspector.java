/**
 * Copyright 2016 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.rest.client.response;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ResponseInspector {

    private Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public Cookie getCookie(String name) {
        Cookie cookie = null;
        if (response != null) {
            Map<String, NewCookie> cookies = response.getCookies();
            if (cookies != null) {
                cookie = cookies.get(name);
            }
        }
        return cookie;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getHeaders(String name) {
        List<T> result = Collections.EMPTY_LIST;
        if (response != null) {
            MultivaluedMap<String, Object> headers = response.getHeaders();
            if (headers != null) {
                result = (List<T>) headers.get(name);
            }
        }
        return result;
    }

    public <T> T getHeader(String name) {
        List<T> headers = getHeaders(name);
        return headers.isEmpty() ? null : headers.get(0);
    }

}
