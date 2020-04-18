/*
 *    Copyright 2020 Paul Hagedorn (Panzer1119)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package de.codemakers.download.remote;

import com.google.gson.JsonElement;
import de.codemakers.base.logger.Logger;
import de.codemakers.download.entities.AbstractToken;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class WebServiceRequest<T extends WebServiceRequest, W extends WebService> {
    
    private final W webService;
    private final RequestType type;
    //
    private String path;
    private final Map<String, String> parameters;
    
    public WebServiceRequest(W webService, RequestType type) {
        this(webService, type, null);
    }
    
    public WebServiceRequest(W webService, RequestType type, String path) {
        this(webService, type, path, new ConcurrentHashMap<>());
    }
    
    public WebServiceRequest(W webService, RequestType type, String path, Map<String, String> parameters) {
        this.webService = Objects.requireNonNull(webService, "webService");
        this.type = type == null ? RequestType.UNKNOWN : type;
        this.path = path;
        this.parameters = parameters;
    }
    
    public W getWebService() {
        return webService;
    }
    
    public RequestType getType() {
        if (type == null) {
            return RequestType.UNKNOWN;
        }
        return type;
    }
    
    public String getPath() {
        return path;
    }
    
    public T setPath(String path) {
        this.path = path;
        return (T) this;
    }
    
    public Map<String, String> getParameters() {
        return parameters;
    }
    
    public T addParameter(String key, String value) {
        if (value == null) {
            parameters.remove(key);
            return (T) this;
        }
        parameters.put(key, value);
        return (T) this;
    }
    
    public URL toURL(boolean withParameters) {
        try {
            return new URL(combineUrl(withParameters));
        } catch (MalformedURLException e) {
            Logger.handleError(e);
            return null;
        }
    }
    
    public String combineBaseUrl() {
        final WebService webService = getWebService();
        final String host = webService.getHost();
        final int port = webService.getPort();
        final String path = getPath();
        if (port == -1) {
            return String.format("%s%s", host, path == null ? "" : path);
        }
        return String.format("%s:%d%s", host, port, path == null ? "" : path);
    }
    
    private String combineParameters() {
        return parameters == null || parameters.isEmpty() ? "" : parameters.entrySet().stream().map((entry) -> String.format("%s=%s", encode(entry.getKey()), encode(entry.getValue()))).collect(Collectors.joining("&", "?", ""));
    }
    
    public String combineUrl(boolean withParameters) {
        final String baseUrl = combineBaseUrl();
        if (!withParameters) {
            return baseUrl;
        }
        return baseUrl + combineParameters();
    }
    
    public T auth(AbstractToken token) {
        if (token == null) {
            return addParameter(AbstractToken.KEY_TOKEN, null);
        }
        return addParameter(AbstractToken.KEY_TOKEN, token.getToken());
    }
    
    public void execute() {
        getWebService().execute(this);
    }
    
    public JsonElement executeToJsonElement() {
        return getWebService().executeToJsonElement(this);
    }
    
    @Override
    public String toString() {
        return "WebServiceRequest{" + "webService=" + webService + ", type=" + type + ", path='" + path + '\'' + ", parameters=" + parameters + '}';
    }
    
    public static final String encode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }
    
}
