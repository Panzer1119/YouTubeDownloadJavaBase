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

public class YouTubeWebService extends WebService {
    
    public static final String TEMPLATE_API_GET_VIDEO_BY_VIDEO_ID = "/videos/byVideoId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEOS_BY_PLAYLIST_ID = "/videos/byPlaylistId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEO_IDS_BY_PLAYLIST_ID = "/videos/byPlaylistId/%s/getIds"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEOS_BY_CHANNEL_ID = "/videos/byChannelId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEO_IDS_BY_CHANNEL_ID = "/videos/byChannelId/%s/getIds"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEOS_BY_UPLOADER_ID = "/videos/byUploaderId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEO_IDS_BY_UPLOADER_ID = "/videos/byUploaderId/%s/getIds"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEO_COUNT_BY_CHANNEL_ID = "/videos/byChannelId/%s/getCount"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEO_COUNT_BY_UPLOADER_ID = "/videos/byUploaderId/%s/getCount"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLIST_BY_PLAYLIST_ID = "/playlists/byPlaylistId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLISTS_BY_VIDEO_ID = "/playlists/byVideoId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLIST_IDS_BY_VIDEO_ID = "/playlists/byVideoId/%s/getIds"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLISTS_BY_UPLOADER_ID = "/playlists/byUploaderId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLIST_IDS_BY_UPLOADER_ID = "/playlists/byUploaderId/%s/getIds"; // Done // Tested
    public static final String TEMPLATE_API_GET_INDEX_BY_PLAYLIST_ID_AND_VIDEO_ID = "/playlists/byPlaylistId/%s/getIndex/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_VIDEO_COUNT_BY_PLAYLIST_ID = "/videos/byPlaylistId/%s/getCount"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLIST_COUNT_BY_VIDEO_ID = "/playlists/byVideoId/%s/getCount"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLIST_COUNT_BY_UPLOADER_ID = "/playlists/byUploaderId/%s/getCount"; // Done // Tested
    public static final String TEMPLATE_API_GET_PLAYLIST_ID_CONTAINS_VIDEO_ID = "/playlists/byPlaylistId/%s/containsVideo/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_CHANNEL_BY_CHANNEL_ID = "/channels/byChannelId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_CHANNEL_ID_HAS_VIDEO_ID = "/channels/byChannelId/%s/hasVideo/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_UPLOADER_BY_UPLOADER_ID = "/uploaders/byUploaderId/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_UPLOADER_ID_UPLOADED_VIDEO_ID = "/uploaders/byUploaderId/%s/uploadedVideo/%s"; // Done // Tested
    public static final String TEMPLATE_API_GET_UPLOADER_ID_CREATED_PLAYLIST_ID = "/uploaders/byUploaderId/%s/createdPlaylist/%s"; // Done // Tested
    
    private static YouTubeWebService WEB_SERVICE = null;
    
    public YouTubeWebService(String host, String rootPath) {
        super(host, rootPath);
    }
    
    public YouTubeWebService(String host, int port, String rootPath) {
        super(host, port, rootPath);
    }
    
    public JsonObject getVideoByVideoId(String videoId) {
        return (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_BY_VIDEO_ID, videoId)).executeToJsonElement();
    }
    
    public JsonArray getVideoIdsByPlaylistId(String playlistId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_IDS_BY_PLAYLIST_ID, playlistId)).executeToJsonElement();
    }
    
    public JsonArray getVideosByPlaylistId(String playlistId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEOS_BY_PLAYLIST_ID, playlistId)).executeToJsonElement();
    }
    
    public JsonObject getPlaylistByPlaylistId(String playlistId) {
        return (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_BY_PLAYLIST_ID, playlistId)).executeToJsonElement();
    }
    
    public JsonArray getPlaylistIdsByVideoId(String videoId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_IDS_BY_VIDEO_ID, videoId)).executeToJsonElement();
    }
    
    public JsonArray getPlaylistsByVideoId(String videoId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLISTS_BY_VIDEO_ID, videoId)).executeToJsonElement();
    }
    
    public boolean isVideoInPlaylist(String videoId, String playlistId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_ID_CONTAINS_VIDEO_ID, playlistId, videoId)).executeToJsonElement();
        return jsonObject != null && jsonObject.has(KEY_RESULT) && jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsBoolean();
    }
    
    public int getIndex(String playlistId, String videoId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_INDEX_BY_PLAYLIST_ID_AND_VIDEO_ID, playlistId, videoId)).executeToJsonElement();
        if (jsonObject == null || !jsonObject.has(KEY_RESULT)) {
            return -1;
        }
        return jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsInt();
    }
    
    public int getVideoCountByPlaylistId(String playlistId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_COUNT_BY_PLAYLIST_ID, playlistId)).executeToJsonElement();
        if (jsonObject == null || !jsonObject.has(KEY_RESULT)) {
            return -1;
        }
        return jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsInt();
    }
    
    public JsonObject getChannelByChannelId(String channelId) {
        return (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_CHANNEL_BY_CHANNEL_ID, channelId)).executeToJsonElement();
    }
    
    public JsonArray getVideoIdsByChannelId(String channelId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_IDS_BY_CHANNEL_ID, channelId)).executeToJsonElement();
    }
    
    public JsonArray getVideosByChannelId(String channelId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEOS_BY_CHANNEL_ID, channelId)).executeToJsonElement();
    }
    
    public boolean isVideoOnChannel(String videoId, String channelId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_CHANNEL_ID_HAS_VIDEO_ID, channelId, videoId)).executeToJsonElement();
        return jsonObject != null && jsonObject.has(KEY_RESULT) && jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsBoolean();
    }
    
    public int getVideoCountByChannelId(String channelId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_COUNT_BY_CHANNEL_ID, channelId)).executeToJsonElement();
        if (jsonObject == null || !jsonObject.has(KEY_RESULT)) {
            return -1;
        }
        return jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsInt();
    }
    
    public JsonObject getUploaderByUploaderId(String uploaderId) {
        return (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_UPLOADER_BY_UPLOADER_ID, uploaderId)).executeToJsonElement();
    }
    
    public JsonArray getVideoIdsByUploaderId(String uploaderId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_IDS_BY_UPLOADER_ID, uploaderId)).executeToJsonElement();
    }
    
    public JsonArray getVideosByUploaderId(String uploaderId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_VIDEOS_BY_UPLOADER_ID, uploaderId)).executeToJsonElement();
    }
    
    public boolean hasVideoUploaded(String videoId, String uploaderId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_UPLOADER_ID_UPLOADED_VIDEO_ID, uploaderId, videoId)).executeToJsonElement();
        return jsonObject != null && jsonObject.has(KEY_RESULT) && jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsBoolean();
    }
    
    public int getVideoCountByUploaderId(String uploaderId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_VIDEO_COUNT_BY_UPLOADER_ID, uploaderId)).executeToJsonElement();
        if (jsonObject == null || !jsonObject.has(KEY_RESULT)) {
            return -1;
        }
        return jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsInt();
    }
    
    public JsonArray getPlaylistIdsByUploaderId(String uploaderId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_IDS_BY_UPLOADER_ID, uploaderId)).executeToJsonElement();
    }
    
    public JsonArray getPlaylistsByUploaderId(String uploaderId) {
        return (JsonArray) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLISTS_BY_UPLOADER_ID, uploaderId)).executeToJsonElement();
    }
    
    public boolean hasPlaylistCreated(String playlistId, String uploaderId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_UPLOADER_ID_CREATED_PLAYLIST_ID, uploaderId, playlistId)).executeToJsonElement();
        return jsonObject != null && jsonObject.has(KEY_RESULT) && jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsBoolean();
    }
    
    public int getPlaylistCountByVideoId(String videoId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_COUNT_BY_VIDEO_ID, videoId)).executeToJsonElement();
        if (jsonObject == null || !jsonObject.has(KEY_RESULT)) {
            return -1;
        }
        return jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsInt();
    }
    
    public int getPlaylistCountByUploaderId(String uploaderId) {
        final JsonObject jsonObject = (JsonObject) createGetRequest(String.format(TEMPLATE_API_GET_PLAYLIST_COUNT_BY_UPLOADER_ID, uploaderId)).executeToJsonElement();
        if (jsonObject == null || !jsonObject.has(KEY_RESULT)) {
            return -1;
        }
        return jsonObject.getAsJsonPrimitive(KEY_RESULT).getAsInt();
    }
    
    @Override
    public String toString() {
        return "YouTubeWebService{" + "host='" + host + '\'' + ", port=" + port + ", rootPath='" + rootPath + '\'' + '}';
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
    
    public static final JsonObject getPlaylistByPlaylistIdViaInstance(String playlistId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getPlaylistByPlaylistId(playlistId);
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
    
    public static final JsonObject getChannelByChannelIdViaInstance(String channelId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getChannelByChannelId(channelId);
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
    
    public static final int getVideoCountByChannelIdViaInstance(String channelId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return -1;
        }
        return webService.getVideoCountByChannelId(channelId);
    }
    
    public static final JsonObject getUploaderByUploaderIdViaInstance(String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return null;
        }
        return webService.getUploaderByUploaderId(uploaderId);
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
    
    public static final int getVideoCountByUploaderIdViaInstance(String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return -1;
        }
        return webService.getVideoCountByUploaderId(uploaderId);
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
    
    public static final int getPlaylistCountByVideoIdViaInstance(String videoId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return -1;
        }
        return webService.getPlaylistCountByVideoId(videoId);
    }
    
    public static final int getPlaylistCountByUploaderIdViaInstance(String uploaderId) {
        final YouTubeWebService webService = getInstance();
        if (webService == null) {
            return -1;
        }
        return webService.getPlaylistCountByUploaderId(uploaderId);
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
