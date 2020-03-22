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

public abstract class AbstractUploader<T extends AbstractUploader, S extends Source, V extends AbstractVideo, P extends AbstractPlaylist> extends AbstractEntity<T, S> {
    
    protected String name;
    
    public AbstractUploader(String uploaderId) {
        this(uploaderId, null);
    }
    
    public AbstractUploader(String uploaderId, String name) {
        super(uploaderId);
        this.name = name;
    }
    
    public String getUploaderId() {
        return getId();
    }
    
    public T setUploaderId(String uploaderId) {
        return setId(uploaderId);
    }
    
    public String getName() {
        return name;
    }
    
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }
    
    public abstract List<String> getUploadedVideoIds();
    
    public abstract List<V> getUploadedVideos();
    
    public abstract boolean hasVideoUploaded(String videoId);
    
    public abstract boolean hasVideoUploaded(V video);
    
    public abstract List<String> getCreatedPlaylistIds();
    
    public abstract List<P> getCreatedPlaylists();
    
    public abstract boolean hasPlaylistCreated(String playlistId);
    
    public abstract boolean hasPlaylistCreated(P playlist);
    
    @Override
    public String toString() {
        return "AbstractUploader{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
}
