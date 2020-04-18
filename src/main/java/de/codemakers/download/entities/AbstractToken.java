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
import de.codemakers.download.YouTube;

import java.time.Instant;
import java.util.Objects;

public abstract class AbstractToken {
    
    public static final String KEY_TOKEN = "token";
    
    protected final String token;
    
    public AbstractToken(String token) {
        this.token = Objects.requireNonNull(token, "token");
    }
    
    public String getToken() {
        return token;
    }
    
    public boolean isValidNow() {
        return isValid(Instant.now());
    }
    
    public abstract boolean isValid(Instant timestamp);
    
    public abstract JsonObject toJsonObject();
    
    public String toJson() {
        return YouTube.GSON.toJson(toJsonObject());
    }
    
    public String toJsonPretty() {
        return YouTube.GSON_PRETTY.toJson(toJsonObject());
    }
    
    @Override
    public String toString() {
        return "AbstractToken{" + "token='" + token + '\'' + '}';
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final AbstractToken that = (AbstractToken) other;
        return Objects.equals(token, that.token);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
    
}
