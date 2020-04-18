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
import de.codemakers.download.entities.AbstractToken;

import java.time.Instant;
import java.util.Objects;

public class BasicToken extends AbstractToken {
    
    public static final String KEY_CREATED = "created";
    public static final String KEY_EXPIRATION = "expiration";
    
    protected final Instant created;
    protected final Instant expiration;
    
    public BasicToken(String token, Instant created, Instant expiration) {
        super(token);
        this.created = Objects.requireNonNull(created, "created");
        this.expiration = expiration;
    }
    
    public Instant getCreated() {
        return created;
    }
    
    public Instant getExpiration() {
        return expiration;
    }
    
    public boolean isCreationValidNow() {
        return isCreationValid(Instant.now());
    }
    
    public boolean isCreationValid(Instant timestamp) {
        return timestamp != null && timestamp.isAfter(created);
    }
    
    public boolean isExpiredNow() {
        return isExpired(Instant.now());
    }
    
    public boolean isExpired(Instant timestamp) {
        return expiration != null && timestamp.isAfter(expiration);
    }
    
    @Override
    public boolean isValid(Instant timestamp) {
        return timestamp != null && isCreationValid(timestamp) && !isExpired(timestamp);
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(KEY_TOKEN, getToken());
        jsonObject.addProperty(KEY_CREATED, getCreated().toEpochMilli());
        final Instant expiration = getExpiration();
        jsonObject.addProperty(KEY_EXPIRATION, expiration == null ? -1 : expiration.toEpochMilli());
        return jsonObject;
    }
    
    @Override
    public String toString() {
        return "BasicToken{" + "created=" + created + ", expiration=" + expiration + ", token='" + token + '\'' + '}';
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        final BasicToken that = (BasicToken) other;
        return Objects.equals(created, that.created) && Objects.equals(expiration, that.expiration);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), created, expiration);
    }
    
}
