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

package de.codemakers.download.sources.impl;

import de.codemakers.download.YouTube;
import de.codemakers.download.sources.Source;

import java.net.MalformedURLException;
import java.net.URL;

public class YouTubeSource implements Source<YouTubeSourceType> {
    
    private final String id;
    private String url;
    private transient YouTubeSourceType sourceType;
    
    protected YouTubeSource(String id) {
        this(id, (String) null);
    }
    
    protected YouTubeSource(String id, String url) {
        this.id = id;
        setUrl(url);
    }
    
    protected YouTubeSource(String id, YouTubeSourceType sourceType) {
        this(id, null, sourceType);
    }
    
    private YouTubeSource(String id, String url, YouTubeSourceType sourceType) {
        this.id = id;
        setUrl(url);
        this.sourceType = sourceType;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public URL toUrl() throws MalformedURLException {
        final String url = getUrl();
        if (url == null) {
            return null;
        }
        return new URL(url);
    }
    
    public YouTubeSource setUrl(String url) {
        this.url = url;
        if (url == null) {
            setSourceType(YouTubeSourceType.UNKNOWN);
        } else {
            setSourceType(YouTubeSourceType.ofUrl(url));
        }
        return this;
    }
    
    @Override
    public YouTubeSourceType getSourceType() {
        if (sourceType == null) {
            return YouTubeSourceType.UNKNOWN;
        }
        return sourceType;
    }
    
    public YouTubeSource setSourceType(YouTubeSourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }
    
    @Override
    public String getSource() {
        final String url = getUrl();
        if (url == null) {
            return getId();
        }
        return url;
    }
    
    @Override
    public String toString() {
        return "YouTubeSource{" + "id='" + id + '\'' + ", url='" + url + '\'' + ", sourceType=" + sourceType + '}';
    }
    
    public static final YouTubeSource ofId(String id) {
        return new YouTubeSource(id);
    }
    
    public static final YouTubeSource ofUrl(String url) {
        return new YouTubeSource(YouTube.getIdFromYouTubeUrl(url), url);
    }
    
    public static final YouTubeSource ofString(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        if (YouTube.PATTERN_YOUTUBE_URL.matcher(source).matches()) {
            return ofUrl(source);
        }
        return ofId(source);
    }
    
    public static final YouTubeSource videoOfId(String videoId) {
        return new YouTubeSource(videoId, YouTubeSourceType.VIDEO);
    }
    
    public static final YouTubeSource playlistOfId(String playlistId) {
        return new YouTubeSource(playlistId, YouTubeSourceType.PLAYLIST);
    }
    
    public static final YouTubeSource channelOfId(String channelId) {
        return new YouTubeSource(channelId, YouTubeSourceType.CHANNEL);
    }
    
    public static final YouTubeSource userOfId(String channelId) {
        return new YouTubeSource(channelId, YouTubeSourceType.USER);
    }
    
}
