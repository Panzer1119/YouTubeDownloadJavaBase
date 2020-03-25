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
import de.codemakers.download.entities.AbstractVideo;
import de.codemakers.download.remote.YouTubeWebService;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.time.Instant;
import java.util.List;

public class BasicYouTubeVideo extends AbstractVideo<BasicYouTubeVideo, YouTubeSource, BasicYouTubePlaylist> {
    
    public static final String KEY_TIMESTAMP = "uploadDate";
    public static final String KEY_VIDEO_ID = "id";
    public static final String KEY_UPLOADER_ID = "uploaderId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DURATION = "duration";
    
    public BasicYouTubeVideo(String videoId) {
        super(videoId);
    }
    
    public BasicYouTubeVideo(String videoId, String uploaderId, String title, long durationMillis) { //TODO FIXME IMPORTANT What is with the channel and other information?!
        super(videoId, uploaderId, title, durationMillis);
    }
    
    @Override
    public List<String> getPlaylistIds() {
        return null;
    }
    
    @Override
    public List<BasicYouTubePlaylist> getPlaylists() {
        return null;
    }
    
    @Override
    public boolean isInPlaylist(String playlistId) {
        return false;
    }
    
    @Override
    public boolean isInPlaylist(BasicYouTubePlaylist playlist) {
        return false;
    }
    
    @Override
    public int getIndexInPlaylist(String playlistId) {
        return 0;
    }
    
    @Override
    public int getIndexInPlaylist(BasicYouTubePlaylist playlist) {
        return 0;
    }
    
    @Override
    public YouTubeSource getSource() {
        return null;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_VIDEO_ID, getVideoId());
        jsonObject.addProperty(KEY_UPLOADER_ID, getUploaderId());
        jsonObject.addProperty(KEY_TITLE, getTitle());
        jsonObject.addProperty(KEY_DURATION, getDurationMillis());
        jsonObject.addProperty(KEY_TIMESTAMP, getTimestampAsEpochMilli());
        return jsonObject;
    }
    
    @Override
    public String toString() {
        return "BasicYouTubeVideo{" + "uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", durationMillis=" + durationMillis + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
    public static final BasicYouTubeVideo ofJsonObject(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        final String videoId = jsonObject.has(KEY_VIDEO_ID) ? jsonObject.getAsJsonPrimitive(KEY_VIDEO_ID).getAsString() : null;
        final String uploaderId = jsonObject.has(KEY_UPLOADER_ID) ? jsonObject.getAsJsonPrimitive(KEY_UPLOADER_ID).getAsString() : null;
        final String title = jsonObject.has(KEY_TITLE) ? jsonObject.getAsJsonPrimitive(KEY_TITLE).getAsString() : null;
        final long durationMillis = jsonObject.has(KEY_DURATION) ? jsonObject.getAsJsonPrimitive(KEY_DURATION).getAsLong() : -1;
        final long timestamp = jsonObject.has(KEY_TIMESTAMP) ? jsonObject.getAsJsonPrimitive(KEY_TIMESTAMP).getAsLong() : -1;
        final BasicYouTubeVideo video = new BasicYouTubeVideo(videoId, uploaderId, title, durationMillis);
        video.setTimestamp(Instant.ofEpochMilli(timestamp)); //FIXME Upload Date is currently saved as "yyyyMMdd"
        return video;
    }
    
    public static final List<BasicYouTubeVideo> ofJsonArray(JsonArray jsonArray) {
        return YouTubeWebService.convertJsonArray(jsonArray, JsonElement::getAsJsonObject, BasicYouTubeVideo::ofJsonObject);
    }
    
}
