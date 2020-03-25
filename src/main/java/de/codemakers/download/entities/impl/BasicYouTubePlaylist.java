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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.codemakers.download.entities.AbstractPlaylist;
import de.codemakers.download.remote.YouTubeWebService;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.util.List;

public class BasicYouTubePlaylist extends AbstractPlaylist<BasicYouTubePlaylist, YouTubeSource, BasicYouTubeVideo> {
    
    //public static final String KEY_TIMESTAMP = "uploadDate";
    public static final String KEY_PLAYLIST_ID = "id";
    public static final String KEY_UPLOADER_ID = "uploaderId";
    public static final String KEY_TITLE = "title";
    //public static final String KEY_COUNT = "count"; //TODO Maybe just send the information from the sql server how many videos are in there?
    
    public BasicYouTubePlaylist(String playlistId) {
        super(playlistId);
    }
    
    public BasicYouTubePlaylist(String playlistId, String uploaderId, String title) {
        super(playlistId, uploaderId, title);
    }
    
    @Override
    public int getVideoCount() {
        return 0;
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
        return false;
    }
    
    @Override
    public boolean containsVideo(BasicYouTubeVideo video) {
        return false;
    }
    
    @Override
    public int getIndex(String videoId) {
        return 0;
    }
    
    @Override
    public int getIndex(BasicYouTubeVideo video) {
        return 0;
    }
    
    @Override
    public YouTubeSource getSource() {
        return null;
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_PLAYLIST_ID, getPlaylistId());
        jsonObject.addProperty(KEY_UPLOADER_ID, getUploaderId());
        jsonObject.addProperty(KEY_TITLE, getTitle());
        //jsonObject.addProperty(KEY_TIMESTAMP, getTimestampAsEpochMilli());
        return jsonObject;
    }
    
    @Override
    public String toString() {
        return "BasicYouTubePlaylist{" + "uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
}
