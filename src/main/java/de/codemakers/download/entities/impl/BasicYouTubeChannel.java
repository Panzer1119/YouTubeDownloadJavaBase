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
import de.codemakers.download.entities.AbstractChannel;
import de.codemakers.download.remote.YouTubeWebService;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.time.Instant;
import java.util.List;

public class BasicYouTubeChannel extends AbstractChannel<BasicYouTubeChannel, YouTubeSource, BasicYouTubeVideo> {
    
    public static final String KEY_CHANNEL_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_TIMESTAMP = "creationDate";
    
    public BasicYouTubeChannel(String channelId) {
        super(channelId);
    }
    
    public BasicYouTubeChannel(String channelId, String name) {
        super(channelId, name);
    }
    
    @Override
    public List<String> getVideoIds() {
        return YouTubeWebService.convertJsonArray(YouTubeWebService.getVideoIdsByChannelIdViaInstance(getChannelId()), JsonElement::getAsString);
    }
    
    @Override
    public List<BasicYouTubeVideo> getVideos() {
        return BasicYouTubeVideo.ofJsonArray(YouTubeWebService.getVideosByChannelIdViaInstance(getChannelId()));
    }
    
    @Override
    public boolean hasVideo(String videoId) {
        if (videoId == null || videoId.isEmpty()) {
            return false;
        }
        return YouTubeWebService.isVideoOnChannelViaInstance(videoId, getChannelId());
    }
    
    @Override
    public boolean hasVideo(BasicYouTubeVideo video) {
        if (video == null) {
            return false;
        }
        return hasVideo(video.getVideoId());
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_CHANNEL_ID, getChannelId());
        jsonObject.addProperty(KEY_NAME, getName());
        jsonObject.addProperty(KEY_TIMESTAMP, getTimestampAsEpochMilli());
        return jsonObject;
    }
    
    @Override
    public String toString() {
        return "BasicYouTubeChannel{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
    public static final BasicYouTubeChannel ofJsonObject(JsonObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        final String channelId = jsonObject.has(KEY_CHANNEL_ID) ? jsonObject.getAsJsonPrimitive(KEY_CHANNEL_ID).getAsString() : null;
        final String name = jsonObject.has(KEY_NAME) ? jsonObject.getAsJsonPrimitive(KEY_NAME).getAsString() : null;
        final long timestamp = jsonObject.has(KEY_TIMESTAMP) ? jsonObject.getAsJsonPrimitive(KEY_TIMESTAMP).getAsLong() : -1;
        final BasicYouTubeChannel channel = new BasicYouTubeChannel(channelId, name);
        if (timestamp != -1) {
            channel.setTimestamp(Instant.ofEpochMilli(timestamp)); //FIXME Upload Date is currently saved as "yyyyMMdd"
        }
        return channel;
    }
    
    public static final List<BasicYouTubeChannel> ofJsonArray(JsonArray jsonArray) {
        return YouTubeWebService.convertJsonArray(jsonArray, JsonElement::getAsJsonObject, BasicYouTubeChannel::ofJsonObject);
    }
    
}
