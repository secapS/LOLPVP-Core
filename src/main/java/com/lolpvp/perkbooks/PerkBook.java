package com.lolpvp.perkbooks;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class PerkBook {

    @Getter
    private String title;
    @Getter
    private String author;
    @Getter
    @Setter
    private List<String> pages;
    @Getter
    @Setter
    private List<String> permissions;
    @Getter
    @Setter
    private Type type;

    public PerkBook(String title, String author) {
        this.title = title;
        this.author = author;
    }

    enum Type {
        PERMISSION,
        GROUP
    }
}
