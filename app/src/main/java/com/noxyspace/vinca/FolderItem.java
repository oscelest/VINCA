package com.noxyspace.vinca;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FolderItem {
    private String title, editor, createdAt, updatedAt, lastEditString;
    private int id = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy 'at' hh.mm.ss");

    public FolderItem(String title, String editor) {
        this.title = title;
        this.editor = editor;
        this.createdAt = dateFormat.format(new Date());
        this.updatedAt = dateFormat.format(new Date());
    }

    public void updateLastEdit(String editor) {
        lastEditString = editor + " (" + dateFormat.format(new Date() + ")");
    }

}
