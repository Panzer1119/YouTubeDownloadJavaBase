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

package de.codemakers.download.entities;

import de.codemakers.download.sources.Source;

import java.util.List;

public abstract class AbstractVideo<T extends AbstractVideo, S extends Source, P extends AbstractPlaylist> extends AbstractEntity<T, S> {
    
    protected String uploaderId;
    protected String title;
    protected long durationMillis;
    
    public AbstractVideo(S source) {
        this(source, null, null, -1);
    }
    
    public AbstractVideo(S source, String uploaderId, String title, long durationMillis) {
        super(source);
        this.uploaderId = uploaderId;
        this.title = title;
        this.durationMillis = durationMillis;
    }
    
    public String getVideoId() {
        return getId();
    }
    
    public T setVideoId(String videoId) {
        return setId(videoId);
    }
    
    public String getUploaderId() {
        return uploaderId;
    }
    
    public T setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
        return (T) this;
    }
    
    public String getTitle() {
        return title;
    }
    
    public T setTitle(String title) {
        this.title = title;
        return (T) this;
    }
    
    public long getDurationMillis() {
        return durationMillis;
    }
    
    public T setDurationMillis(long durationMillis) {
        this.durationMillis = durationMillis;
        return (T) this;
    }
    
    public abstract List<String> getPlaylistIds();
    
    public abstract List<P> getPlaylists();
    
    public abstract boolean isInPlaylist(String playlistId);
    
    public abstract boolean isInPlaylist(P playlist);
    
    public abstract int getIndexInPlaylist(String playlistId);
    
    public abstract int getIndexInPlaylist(P playlist);
    
    @Override
    public String toString() {
        return "AbstractVideo{" + "uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", durationMillis=" + durationMillis + ", source=" + source + ", timestamp=" + timestamp + '}';
    }
    
}
