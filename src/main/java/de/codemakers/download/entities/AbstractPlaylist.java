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

public abstract class AbstractPlaylist<T extends AbstractPlaylist, S extends Source, V extends AbstractVideo> extends AbstractEntity<T, S> {
    
    protected String uploaderId;
    protected String title;
    
    public AbstractPlaylist(S source) {
        this(source, null, null);
    }
    
    public AbstractPlaylist(S source, String uploaderId, String title) {
        super(source);
        this.uploaderId = uploaderId;
        this.title = title;
    }
    
    public String getPlaylistId() {
        return getId();
    }
    
    public T setPlaylistId(String playlistId) {
        return setId(playlistId);
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
    
    public abstract int getVideoCount();
    
    public abstract List<String> getVideoIds();
    
    public abstract List<V> getVideos();
    
    public abstract boolean containsVideo(String videoId);
    
    public abstract boolean containsVideo(V video);
    
    public abstract int getIndex(String videoId);
    
    public abstract int getIndex(V video);
    
    @Override
    public String toString() {
        return "AbstractPlaylist{" + "uploaderId='" + uploaderId + '\'' + ", title='" + title + '\'' + ", source=" + source + ", timestamp=" + timestamp + '}';
    }
    
}
