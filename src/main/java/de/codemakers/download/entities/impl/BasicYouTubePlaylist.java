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

package de.codemakers.download.entities.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.codemakers.download.entities.AbstractPlaylist;
import de.codemakers.download.remote.YouTubeWebService;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.time.Instant;
import java.util.List;

public class BasicYouTubePlaylist extends AbstractPlaylist<BasicYouTubePlaylist, YouTubeSource, BasicYouTubeVideo> {
    
    public static final String KEY_PLAYLIST_ID = "id";
    public static final String KEY_UPLOADER_ID = "uploaderId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_PLAYLIST = "playlist";
    public static final String KEY_TIMESTAMP = "uploadDate";
    //public static final String KEY_COUNT = "count"; //TODO Maybe just send the information from the sql server how many videos are in there?
    
    protected String playlist;
    
    public BasicYouTubePlaylist(String playlistId) {
        this(YouTubeSource.playlistOfId(playlistId));
    }
    
    public BasicYouTubePlaylist(YouTubeSource source) {
        super(source);
        this.playlist = null;
    }
    
    public BasicYouTubePlaylist(String playlistId, String uploaderId, String title) {
        this(playlistId, uploaderId, title, null);
    }
    
    public BasicYouTubePlaylist(YouTubeSource source, String uploaderId, String title) {
        this(source, uploaderId, title, null);
    }
    
    public BasicYouTubePlaylist(String playlistId, String uploaderId, String title, String playlist) {
        this(YouTubeSource.playlistOfId(playlistId), uploaderId, title, playlist);
    }
    
    public BasicYouTubePlaylist(YouTubeSource source, String uploaderId, String title, String playlist) {
        super(source, uploaderId, title);
        this.playlist = playlist;
    }
    
    public String getPlaylist() {
        return playlist;
    }
    
    public BasicYouTubePlaylist setPlaylist(String playlist) {
        this.playlist = playlist;
        return this;
    }
    
    @Override
    public int getVideoCount() {
        return YouTubeWebService.getVideoCountByPlaylistIdViaInstance(getPlaylistId());
    }
    
    @Override
    public List<String> getVideoIds() {
        return YouTubeWebService.convertJsonArray(YouTubeWebService.getVideoIdsByPlaylistIdViaInstance(getPlaylistId()), JsonElement::getAsString);
    }
    
    @Override
    public List<BasicYouTubeVideo> getVideos() {
        return BasicYouTubeVideo.ofJsonArray(YouTubeWebService.getVideosByPlaylistIdViaInstance(getPlaylistId()));
    }
    
    @Override
    public boolean containsVideo(String videoId) {
        if (videoId == null || videoId.isEmpty()) {
            return false;
        }
        return YouTubeWebService.isVideoInPlaylistViaInstance(videoId, getPlaylistId());
    }
    
    @Override
    public boolean containsVideo(BasicYouTubeVideo video) {
        if (video == null) {
            return false;
        }
        return containsVideo(video.getVideoId());
    }
    
    @Override
    public int getIndex(String videoId) {
        if (videoId == null || videoId.isEmpty()) {
            return -1;
        }
        return YouTubeWebService.getIndexViaInstance(getPlaylistId(), videoId);
    }
    
    @Override
    public int getIndex(BasicYouTubeVideo video) {
        if (video == null) {
            return -1;
        }
        return getIndex(video.getVideoId());
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_PLAYLIST_ID, getPlaylistId());
        jsonObject.addProperty(KEY_UPLOADER_ID, getUploaderId());
        jsonObject.addProperty(KEY_TITLE, getTitle());
        jsonObject.addProperty(KEY_PLAYLIST, getPlaylist());
        //jsonObject.addProperty(KEY_TIMESTAMP, getTimestampAsEpochMilli());
        return jsonObject;
    }
    
    @Override
    public String toString() {
        return "BasicYouTubePlaylist{" + "playlist='" + playlist + '\'' + ", uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", source=" + source + ", timestamp=" + timestamp + '}';
    }
    
    public static final BasicYouTubePlaylist ofJsonObject(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        final String playlistId = jsonObject.has(KEY_PLAYLIST_ID) ? jsonObject.getAsJsonPrimitive(KEY_PLAYLIST_ID).getAsString() : null;
        final String uploaderId = jsonObject.has(KEY_UPLOADER_ID) ? jsonObject.getAsJsonPrimitive(KEY_UPLOADER_ID).getAsString() : null;
        final String title = jsonObject.has(KEY_TITLE) ? jsonObject.getAsJsonPrimitive(KEY_TITLE).getAsString() : null;
        final String playlist_ = jsonObject.has(KEY_PLAYLIST) ? jsonObject.getAsJsonPrimitive(KEY_PLAYLIST).getAsString() : null;
        final long timestamp = jsonObject.has(KEY_TIMESTAMP) ? jsonObject.getAsJsonPrimitive(KEY_TIMESTAMP).getAsLong() : -1;
        final BasicYouTubePlaylist playlist = new BasicYouTubePlaylist(playlistId, uploaderId, title, playlist_);
        if (timestamp != -1) {
            playlist.setTimestamp(Instant.ofEpochMilli(timestamp)); //FIXME Upload Date is currently saved as "yyyyMMdd"
        }
        return playlist;
    }
    
    public static final List<BasicYouTubePlaylist> ofJsonArray(JsonArray jsonArray) {
        return YouTubeWebService.convertJsonArray(jsonArray, JsonElement::getAsJsonObject, BasicYouTubePlaylist::ofJsonObject);
    }
    
}
