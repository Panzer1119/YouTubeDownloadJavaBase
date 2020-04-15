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
import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.base.util.tough.ToughFunction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class YouTubeWebService extends WebService<YouTubeWebService> {
    
    public static final String TEMPLATE_API_GET_VIDEO_BY_VIDEO_ID = "/videos/%s";
    public static final String TEMPLATE_API_GET_VIDEOS_BY_PLAYLIST_ID = "/videos/byPlaylistId/%s";
    public static final String TEMPLATE_API_GET_VIDEO_IDS_BY_PLAYLIST_ID = "/videoIds/byPlaylistId/%s";
    public static final String TEMPLATE_API_GET_PLAYLIST_BY_PLAYLIST_ID = "/playlists/%s";
    public static final String TEMPLATE_API_GET_PLAYLISTS_BY_VIDEO_ID = "/playlists/byVideoId/%s";
    public static final String TEMPLATE_API_GET_PLAYLIST_IDS_BY_VIDEO_ID = "/playlistIds/byVideoId/%s";
    
    private static YouTubeWebService WEB_SERVICE = null;
    
    public YouTubeWebService(String host, String rootPath) {
        super(host, rootPath);
    }
    
    public YouTubeWebService(String host, int port, String rootPath) {
        super(host, port, rootPath);
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
    
    public JsonObject getPlaylistsByPlaylistId(String playlistId) {
        return (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_BY_PLAYLIST_ID, playlistId)).executeToJsonElement();
    }
    
    public JsonArray getPlaylistsByVideoId(String videoId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLISTS_BY_VIDEO_ID, videoId)).executeToJsonElement();
    }
    
    public JsonArray getPlaylistIdsByVideoId(String videoId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_IDS_BY_VIDEO_ID, videoId)).executeToJsonElement();
    }
    
    public boolean isVideoInPlaylist(String videoId, String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public int getIndex(String playlistId, String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public int getVideoCountByPlaylistId(String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public JsonArray getVideoIdsByChannelId(String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public JsonArray getVideosByChannelId(String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public boolean isVideoOnChannel(String videoId, String channelId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public JsonArray getVideoIdsByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public JsonArray getVideosByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public boolean hasVideoUploaded(String videoId, String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public JsonArray getPlaylistIdsByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public JsonArray getPlaylistsByUploaderId(String uploaderId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    public boolean hasPlaylistCreated(String playlistId, String uploaderId) {
        throw new NotYetImplementedRuntimeException();
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
    
    public static final JsonObject getPlaylistsByPlaylistIdViaInstance(String playlistId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getPlaylistsByPlaylistId(playlistId);
    }
    
    public static final JsonArray getPlaylistsByVideoIdViaInstance(String videoId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getPlaylistsByVideoId(videoId);
    }
    
    public static final JsonArray getPlaylistIdsByVideoIdViaInstance(String videoId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getPlaylistIdsByVideoId(videoId);
    }
    
    public static final boolean isVideoInPlaylistViaInstance(String videoId, String playlistId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return false;
        }
        return webService.isVideoInPlaylist(videoId, playlistId);
    }
    
    public static final int getIndexViaInstance(String playlistId, String videoId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return -1;
        }
        return webService.getIndex(playlistId, videoId);
    }
    
    public static final int getVideoCountByPlaylistIdViaInstance(String playlistId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return -1;
        }
        return webService.getVideoCountByPlaylistId(playlistId);
    }
    
    public static final JsonArray getVideoIdsByChannelIdViaInstance(String channelId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getVideoIdsByChannelId(channelId);
    }
    
    public static final JsonArray getVideosByChannelIdViaInstance(String channelId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getVideosByChannelId(channelId);
    }
    
    public static final boolean isVideoOnChannelViaInstance(String videoId, String channelId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return false;
        }
        return webService.isVideoOnChannel(videoId, channelId);
    }
    
    public static final JsonArray getVideoIdsByUploaderIdViaInstance(String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getVideoIdsByUploaderId(uploaderId);
    }
    
    public static final JsonArray getVideosByUploaderIdViaInstance(String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getVideosByUploaderId(uploaderId);
    }
    
    public static final boolean hasVideoUploadedViaInstance(String videoId, String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return false;
        }
        return webService.hasVideoUploaded(videoId, uploaderId);
    }
    
    public static final JsonArray getPlaylistIdsByUploaderIdViaInstance(String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getPlaylistIdsByUploaderId(uploaderId);
    }
    
    public static final JsonArray getPlaylistsByUploaderIdViaInstance(String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getPlaylistsByUploaderId(uploaderId);
    }
    
    public static final boolean hasPlaylistCreatedViaInstance(String playlistId, String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return false;
        }
        return webService.hasPlaylistCreated(playlistId, uploaderId);
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
