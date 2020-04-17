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

package de.codemakers.download;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTube {
    
    // // YouTube URL Pattern
    // All YouTube URL Pattern
    public static final String STRING_PATTERN_YOUTUBE_URL = "(?:http(?:s)?:\\/\\/)?(?:www.)?(?:youtube\\.com\\/(?:channel\\/|playlist\\?list=)([a-zA-Z0-9_-]+)|(?:youtu\\.be\\/|youtube\\.com(?:\\/embed\\/|\\/v\\/|\\/watch\\?v=|\\/ytscreeningroom\\?v=|\\/feeds\\/api\\/videos\\/|\\/user\\\\S*[^\\w\\-\\s]|\\S*[^\\w\\-\\s]))([\\w\\-\\_]{11}))[a-z0-9;:@#?&%=+\\/\\$_.-]*";
    /**
     * Group 1: Maybe Playlist ID
     * Group 2: Maybe Video ID
     */
    public static final Pattern PATTERN_YOUTUBE_URL = Pattern.compile(STRING_PATTERN_YOUTUBE_URL);
    // YouTube Video URL Pattern
    public static final String STRING_PATTERN_YOUTUBE_VIDEO_URL = "(?:http(?:s)?:\\/\\/)?(?:www.)?(?:youtu\\.be\\/|youtube\\.com(?:\\/embed\\/|\\/v\\/|\\/watch\\?v=|\\/ytscreeningroom\\?v=|\\/feeds\\/api\\/videos\\/|\\/user\\\\S*[^\\w\\-\\s]|\\S*[^\\w\\-\\s]))([\\w\\-\\_]{11})[a-z0-9;:@#?&%=+\\/\\$_.-]*";
    /**
     * Group 1: Video ID
     */
    public static final Pattern PATTERN_YOUTUBE_VIDEO_URL = Pattern.compile(STRING_PATTERN_YOUTUBE_VIDEO_URL);
    // YouTube Playlist URL Pattern
    public static final String STRING_PATTERN_YOUTUBE_PLAYLIST_URL = "(?:http|https|)(?::\\/\\/|)(?:www.|)youtube.com\\/playlist\\?list=([a-zA-Z0-9_-]+)[a-z0-9;:@#?&%=+\\/\\$_.-]*";
    /**
     * Group 1: Playlist ID
     */
    public static final Pattern PATTERN_YOUTUBE_PLAYLIST_URL = Pattern.compile(STRING_PATTERN_YOUTUBE_PLAYLIST_URL);
    // YouTube Video in Playlist URL Pattern
    public static final String STRING_PATTERN_YOUTUBE_VIDEO_IN_PLAYLIST_URL = "(?:http|https|)(?::\\/\\/|)(?:www.|)youtube.com\\/watch\\?v=([\\w\\-\\_]{11})&list=([a-zA-Z0-9_-]+)(?:&index=(\\d+))?(?:&t=(\\d+)s)?[a-z0-9;:@#?&%=+\\/\\$_.-]*";
    /**
     * Group 1: Video ID
     * Group 2: Playlist ID
     */
    public static final Pattern PATTERN_YOUTUBE_VIDEO_IN_PLAYLIST_URL = Pattern.compile(STRING_PATTERN_YOUTUBE_VIDEO_IN_PLAYLIST_URL);
    // YouTube User URL Pattern
    public static final String STRING_PATTERN_YOUTUBE_USER_URL = "(?:http|https|)(?::\\/\\/|)(?:www.|)youtube.com\\/user\\/([a-zA-Z0-9]+)[a-z0-9;:@#?&%=+\\/\\$_.-]*";
    /**
     * Group 1: Username
     */
    public static final Pattern PATTERN_YOUTUBE_USER_URL = Pattern.compile(STRING_PATTERN_YOUTUBE_USER_URL);
    // YouTube Channel URL Pattern
    public static final String STRING_PATTERN_YOUTUBE_CHANNEL_URL = "(?:http|https|)(?::\\/\\/|)(?:www.|)youtube.com\\/channel\\/([a-zA-Z0-9_-]+)[a-z0-9;:@#?&%=+\\/\\$_.-]*";
    /**
     * Group 1: Channel ID
     */
    public static final Pattern PATTERN_YOUTUBE_CHANNEL_URL = Pattern.compile(STRING_PATTERN_YOUTUBE_CHANNEL_URL);
    // //
    
    public static final String getIdFromYouTubeUrl(String url) {
        return getIdFromYouTubeUrl(url, "");
    }
    
    public static final String getIdFromYouTubeUrl(String url, String defaultValue) {
        if (url == null || url.isEmpty()) {
            return defaultValue;
        }
        final Matcher matcher = PATTERN_YOUTUBE_URL.matcher(url);
        if (!matcher.matches()) {
            return defaultValue;
        }
        if (matcher.group(1) == null || matcher.group(1).isEmpty()) {
            return matcher.group(2);
        }
        return matcher.group(1);
    }
    
}
