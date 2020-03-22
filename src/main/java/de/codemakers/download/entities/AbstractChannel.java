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

public abstract class AbstractChannel<T extends AbstractChannel, S extends Source, V extends AbstractVideo> extends AbstractEntity<T, S> {
    
    protected String name;
    
    public AbstractChannel(String channelId) {
        this(channelId, null);
    }
    
    public AbstractChannel(String channelId, String name) {
        super(channelId);
        this.name = name;
    }
    
    public String getChannelId() {
        return getId();
    }
    
    public T setChannelId(String channelId) {
        return setId(channelId);
    }
    
    public String getName() {
        return name;
    }
    
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }
    
    public abstract List<String> getVideoIds();
    
    public abstract List<V> getVideos();
    
    public abstract boolean hasVideo(String videoId);
    
    public abstract boolean hasVideo(V video);
    
    @Override
    public String toString() {
        return "AbstractChannel{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
}
