package com.coursera.sacbe.petagramaxelsegura.restApi.model;

/**
 * Created by axel on 28/10/2016.
 */

public class UsuarioInstagram {

    private String id;
    private String idDispositivoFrom;
    private String idUsuarioInstagramFrom;
    private String idDispositivoTo;
    private String idUsuarioInstagramTo;
    private String idFotoInstagram;
    private String idInstagramFrom;

    public UsuarioInstagram() {
    }

    public UsuarioInstagram(String id, String idDispositivoFrom, String idUsuarioInstagramFrom, String idDispositivoTo, String idUsuarioInstagramTo, String idFotoInstagram, String idInstagramFrom) {
        this.id = id;
        this.idDispositivoFrom = idDispositivoFrom;
        this.idUsuarioInstagramFrom = idUsuarioInstagramFrom;
        this.idDispositivoTo = idDispositivoTo;
        this.idUsuarioInstagramTo = idUsuarioInstagramTo;
        this.idFotoInstagram = idFotoInstagram;
        this.idInstagramFrom = idInstagramFrom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDispositivoFrom() {
        return idDispositivoFrom;
    }

    public void setIdDispositivoFrom(String idDispositivoFrom) {
        this.idDispositivoFrom = idDispositivoFrom;
    }

    public String getIdDispositivoTo() {
        return idDispositivoTo;
    }

    public void setIdDispositivoTo(String idDispositivoTo) {
        this.idDispositivoTo = idDispositivoTo;
    }

    public String getIdUsuarioInstagramFrom() {
        return idUsuarioInstagramFrom;
    }

    public void setIdUsuarioInstagramFrom(String idUsuarioInstagramFrom) {
        this.idUsuarioInstagramFrom = idUsuarioInstagramFrom;
    }

    public String getIdUsuarioInstagramTo() {
        return idUsuarioInstagramTo;
    }

    public void setIdUsuarioInstagramTo(String idUsuarioInstagramTo) {
        this.idUsuarioInstagramTo = idUsuarioInstagramTo;
    }

    public String getIdFotoInstagram() {
        return idFotoInstagram;
    }

    public void setIdFotoInstagram(String idFotoInstagram) {
        this.idFotoInstagram = idFotoInstagram;
    }

    public String getIdInstagramFrom() {
        return idInstagramFrom;
    }

    public void setIdInstagramFrom(String idInstagramFrom) {
        this.idInstagramFrom = idInstagramFrom;
    }
}
