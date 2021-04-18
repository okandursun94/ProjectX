package com.example.project.Models;

public class Episode {
    private int id;
    private String name;
    private String air_date;
    private String episode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirdate() {
        return air_date;
    }

    public void setAirdate(String airdate) {
        this.air_date = airdate;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }
}