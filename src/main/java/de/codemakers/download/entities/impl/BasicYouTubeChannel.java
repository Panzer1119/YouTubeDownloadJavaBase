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

import com.google.gson.JsonObject;
import de.codemakers.download.entities.AbstractChannel;
import de.codemakers.download.sources.impl.YouTubeSource;

import java.util.List;

public class BasicYouTubeChannel extends AbstractChannel<BasicYouTubeChannel, YouTubeSource, BasicYouTubeVideo> {
    
    public BasicYouTubeChannel(String channelId) {
        super(channelId);
    }
    
    public BasicYouTubeChannel(String channelId, String name) {
        super(channelId, name);
    }
    
    @Override
    public List<String> getVideoIds() {
        return null;
    }
    
    @Override
    public List<BasicYouTubeVideo> getVideos() {
        return null;
    }
    
    @Override
    public boolean hasVideo(String videoId) {
        return false;
    }
    
    @Override
    public boolean hasVideo(BasicYouTubeVideo video) {
        return false;
    }
    
    @Override
    public YouTubeSource getSource() {
        return null;
    }
    
    @Override
    public JsonObject toJsonObject() {
        return null;
    }
    
    @Override
    public String toString() {
        return "BasicYouTubeChannel{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
}
