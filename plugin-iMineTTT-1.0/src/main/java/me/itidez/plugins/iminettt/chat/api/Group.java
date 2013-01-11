package me.itidez.plugins.iminettt.chat.api;

/**
* @author itidez
*/
public interface Group {

    public String getName();

    public String getPrefix();

    public String getSuffix();

    public int maxChannels();

}