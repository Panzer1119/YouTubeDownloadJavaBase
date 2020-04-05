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
import de.codemakers.base.exceptions.NotYetImplementedRuntimeException;
import de.codemakers.download.entities.AbstractUploader;
import de.codemakers.download.remote.YouTubeWebService;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.time.Instant;
import java.util.List;

public class BasicYouTubeUploader extends AbstractUploader<BasicYouTubeUploader, YouTubeSource, BasicYouTubeVideo, BasicYouTubePlaylist> {
    
    public static final String KEY_UPLOADER_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TIMESTAMP = "creationDate";
    
    public BasicYouTubeUploader(String uploaderId) {
        super(uploaderId);
    }
    
    public BasicYouTubeUploader(String uploaderId, String name) {
        super(uploaderId, name);
    }
    
    @Override
    public List<String> getUploadedVideoIds() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<BasicYouTubeVideo> getUploadedVideos() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasVideoUploaded(String videoId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasVideoUploaded(BasicYouTubeVideo video) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<String> getCreatedPlaylistIds() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public List<BasicYouTubePlaylist> getCreatedPlaylists() {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasPlaylistCreated(String playlistId) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public boolean hasPlaylistCreated(BasicYouTubePlaylist playlist) {
        throw new NotYetImplementedRuntimeException();
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_UPLOADER_ID, getUploaderId());
        jsonObject.addProperty(KEY_NAME, getName());
        jsonObject.addProperty(KEY_TIMESTAMP, getTimestampAsEpochMilli());
        return jsonObject;
    }
    
    @Override
    public String toString() {
        return "BasicYouTubeUploader{" + "name='" + name + '\'' + ", source=" + source + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
    public static final BasicYouTubeUploader ofJsonObject(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        final String uploaderId = jsonObject.has(KEY_UPLOADER_ID) ? jsonObject.getAsJsonPrimitive(KEY_UPLOADER_ID).getAsString() : null;
        final String name = jsonObject.has(KEY_NAME) ? jsonObject.getAsJsonPrimitive(KEY_NAME).getAsString() : null;
        final long timestamp = jsonObject.has(KEY_TIMESTAMP) ? jsonObject.getAsJsonPrimitive(KEY_TIMESTAMP).getAsLong() : -1;
        final BasicYouTubeUploader uploader = new BasicYouTubeUploader(uploaderId, name);
        if (timestamp != -1) {
            uploader.setTimestamp(Instant.ofEpochMilli(timestamp)); //FIXME Upload Date is currently saved as "yyyyMMdd"
        }
        return uploader;
    }
    
    public static final List<BasicYouTubeUploader> ofJsonArray(JsonArray jsonArray) {
        return YouTubeWebService.convertJsonArray(jsonArray, JsonElement::getAsJsonObject, BasicYouTubeUploader::ofJsonObject);
    }
    
}
