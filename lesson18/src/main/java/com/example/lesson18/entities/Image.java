package com.example.lesson18.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {

    private List<Images> images;

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public class Images {

        @SerializedName(value = "display_sizes")
        private List<DisplaySizes> displaySizes;

        public List<DisplaySizes> getDisplaySizes() {
            return displaySizes;
        }

        public void setDisplaySizes(List<DisplaySizes> displaySizes) {
            this.displaySizes = displaySizes;
        }

        public class DisplaySizes {

            private String uri;

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }
        }
    }
}
