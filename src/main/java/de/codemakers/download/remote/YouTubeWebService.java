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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.codemakers.base.util.tough.ToughFunction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class YouTubeWebService extends WebService<YouTubeWebService> {
    
    public static final String TEMPLATE_API_GET_VIDEO_BY_VIDEO_ID = "/videos/%s";
    public static final String TEMPLATE_API_GET_VIDEOS_BY_PLAYLIST_ID = "/videos/byPlaylistId/%s";
    public static final String TEMPLATE_API_GET_VIDEO_IDS_BY_PLAYLIST_ID = "/videoIds/byPlaylistId/%s";
    
    private static YouTubeWebService WEB_SERVICE = null;
    
    public YouTubeWebService(String host) {
        super(host);
    }
    
    public YouTubeWebService(String host, int port) {
        super(host, port);
    }
    
    @Override
    protected void auth(WebServiceRequest webServiceRequest) {
        webServiceRequest.addParameter("authToken", "AUTH_TOKEN"); //DEBUG REMOVE this!!!
        //TODO IMPORTANT
    }
    
    public JsonObject getVideoByVideoId(String videoId) {
        return (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_BY_VIDEO_ID, videoId)).executeToJsonElement();
    }
    
    public JsonArray getVideosByPlaylistId(String playlistId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEOS_BY_PLAYLIST_ID, playlistId)).executeToJsonElement();
    }
    
    public JsonArray getVideoIdsByPlaylistId(String playlistId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_IDS_BY_PLAYLIST_ID, playlistId)).executeToJsonElement();
    }
    
    public static final YouTubeWebService getInstance() {
        return WEB_SERVICE;
    }
    
    public static final void setInstance(YouTubeWebService webService) {
        YouTubeWebService.WEB_SERVICE = webService;
    }
    
    public static final JsonObject getVideoByVideoIdViaInstance(String videoId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getVideoByVideoId(videoId);
    }
    
    public static final JsonArray getVideosByPlaylistIdViaInstance(String playlistId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getVideosByPlaylistId(playlistId);
    }
    
    public static final JsonArray getVideoIdsByPlaylistIdViaInstance(String playlistId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getVideoIdsByPlaylistId(playlistId);
    }
    
    public static final <R> List<R> convertJsonArray(JsonArray jsonArray, ToughFunction<JsonElement, R> function) {
        if (jsonArray == null || function == null) {
            return null;
        }
        return StreamSupport.stream(jsonArray.spliterator(), false).map(function::applyWithoutException).collect(Collectors.toList());
    }
    
    public static final <R, T> List<R> convertJsonArray(JsonArray jsonArray, ToughFunction<JsonElement, T> function1, ToughFunction<T, R> function2) {
        if (jsonArray == null || function1 == null || function2 == null) {
            return null;
        }
        return StreamSupport.stream(jsonArray.spliterator(), false).map(function1::applyWithoutException).map(function2::applyWithoutException).collect(Collectors.toList());
    }
    
}
