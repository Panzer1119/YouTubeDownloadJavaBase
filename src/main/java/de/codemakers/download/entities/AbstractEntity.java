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

import com.google.gson.JsonObject;
import de.codemakers.download.sources.Source;

import java.time.Instant;

public abstract class AbstractEntity<T extends AbstractEntity, S extends Source> {
    
    protected S source;
    protected String id;
    protected Instant timestamp = null;
    
    public AbstractEntity(String id) {
        this(null, id);
    }
    
    public AbstractEntity(S source) {
        this(source, source == null ? null : source.getId());
    }
    
    public AbstractEntity(S source, String id) {
        this.source = source;
        this.id = id;
    }
    
    public S getSource() {
        return source;
    }
    
    public T setSource(S source) {
        this.source = source;
        return (T) this;
    }
    
    public String getId() {
        return id;
    }
    
    public T setId(String id) {
        this.id = id;
        return (T) this;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public long getTimestampAsEpochMilli() {
        final Instant timestamp = getTimestamp();
        if (timestamp == null) {
            return -1;
        }
        return timestamp.toEpochMilli();
    }
    
    public T setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return (T) this;
    }
    
    public abstract JsonObject toJsonObject();
    
    @Override
    public String toString() {
        return "AbstractEntity{" + "source=" + source + ", id='" + id + '\'' + ", timestamp=" + timestamp + '}';
    }
    
}
