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
import de.codemakers.base.multiplets.Doublet;
import de.codemakers.base.util.JsonSettings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class WebService<T extends WebService> {
    
    protected final String host;
    protected final int port;
    protected boolean useAuth = false;
    
    public WebService(String host) {
        this(host, -1);
    }
    
    public WebService(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public String getHost() {
        return host;
    }
    
    public int getPort() {
        return port;
    }
    
    public boolean isUsingAuth() {
        return useAuth;
    }
    
    public WebService setUseAuth(boolean useAuth) {
        this.useAuth = useAuth;
        return this;
    }
    
    protected String combineUrl(String template, Object... values) {
        final String host = getHost();
        final int port = getPort();
        final String suffix = String.format(template, values);
        if (port == -1) {
            return String.format("%s%s", host, suffix);
        }
        return String.format("%s:%d%s", host, port, suffix);
    }
    
    public WebServiceRequest emptyGetRequest() {
        return new WebServiceRequest(this, RequestType.GET);
    }
    
    public WebServiceRequest createGetRequest(String path) {
        return new WebServiceRequest(this, RequestType.GET, path);
    }
    
    public WebServiceRequest createGetRequest(String path, Map<String, String> parameters) {
        return new WebServiceRequest(this, RequestType.GET, path, parameters);
    }
    
    public WebServiceRequest createGetRequest(String path, Object... parameters) {
        final Map<String, String> map = new ConcurrentHashMap<>();
        String key = null;
        for (int i = 0; i < parameters.length; i++) {
            final Object object = parameters[i];
            if (i % 2 == 0) {
                if (object == null) {
                    throw new NullPointerException("Parameter key may not be null");
                }
                key = object.toString();
            } else {
                map.put(key, object == null ? "" : object.toString());
                key = null;
            }
        }
        if (key != null) {
            map.put(key, "");
        }
        return new WebServiceRequest(this, RequestType.GET, path, map);
    }
    
    public void execute(WebServiceRequest webServiceRequest) {
        final Doublet<Integer, String> response = (Doublet<Integer, String>) executeIntern(webServiceRequest, false);
        //TODO What to do with the response?
    }
    
    public JsonElement executeToJsonElement(WebServiceRequest webServiceRequest) {
        return (JsonElement) executeIntern(webServiceRequest, true);
    }
    
    private final Object executeIntern(WebServiceRequest webServiceRequest, boolean returnJsonElement) {
        if (isUsingAuth()) {
            auth(webServiceRequest);
        }
        return executeInternStatic(webServiceRequest, returnJsonElement);
    }
    
    protected abstract void auth(WebServiceRequest webServiceRequest);
    
    @Override
    public String toString() {
        return "WebService{" + "host='" + host + '\'' + ", port=" + port + '}';
    }
    
    private static final Object executeInternStatic(WebServiceRequest webServiceRequest, boolean returnJsonElement) {
        if (webServiceRequest == null || webServiceRequest.getType() == RequestType.UNKNOWN) {
            Logger.logWarning("WebService::executeIntern");
            return null;
        }
        final URL url = webServiceRequest.toURL(true);
        if (url == null) {
            return null;
        }
        Logger.logDebug(String.format("YouTubeWebService::executeIntern: url=\"%s\"", url)); //DEBUG REMOVE this!!!
        try {
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try {
                connection.setRequestMethod(webServiceRequest.getType().name());
                if (returnJsonElement) {
                    connection.setRequestProperty("Content-Type", "application/json");
                }
                //connection.setConnectTimeout(5000); //TODO
                //connection.setReadTimeout(5000); //TODO
                final int status = connection.getResponseCode(); //TODO What to do with the response code?
                final StringBuilder response = new StringBuilder();
                try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append("\n");
                        response.append(line);
                    }
                }
                connection.disconnect();
                Logger.logDebug(String.format("YouTubeWebService::executeIntern: response.toString()=\"%s\"", response.toString())); //DEBUG REMOVE this!!!
                if (returnJsonElement) {
                    return JsonSettings.GSON.fromJson(response.toString(), JsonElement.class);
                }
                return new Doublet<>(status, response.toString());
            } catch (Exception ex) {
                Logger.handleError(ex);
                connection.disconnect();
                return null;
            }
        } catch (Exception ex) {
            Logger.handleError(ex);
            return null;
        }
    }
    
}
