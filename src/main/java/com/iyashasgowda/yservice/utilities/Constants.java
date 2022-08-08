package com.iyashasgowda.yservice.utilities;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Constants {
    public static final String[] VIDEO_EXT = {"mp4", "mov", "wmv", "avi", "mkv", "flv", "webm"};
    public static final String[] IMAGE_EXT = {"png", "jpg", "jpeg", "gif", "tiff"};
    public static final String MEDIA_PATH = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/uploads";
}
