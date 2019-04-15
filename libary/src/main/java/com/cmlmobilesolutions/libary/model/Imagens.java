package com.cmlmobilesolutions.libary.model;

public class Imagens {


    private   String filial;
    private   String preemb;
    private   String container;
    private   String nomeImagem;
    private   String tipoImagem;
    private   String bufferImagem;
    private  String  romaneio;
    public Imagens(String filial,
                   String preemb,
                   String container,
                   String nomeImagem,
                   String tipoImagem,
                   String bufferImagem,
                   String romaneio) {
        this.filial = filial;
        this.preemb = preemb;
        this.container = container;
        this.nomeImagem = nomeImagem;
        this.tipoImagem = tipoImagem;
        this.bufferImagem = bufferImagem;
        this.romaneio=romaneio;
    }

    public String getFilial() {
        return filial;
    }

    public String getRomaneio() {
        return romaneio;
    }

    public void setRomaneio(String romaneio) {
        this.romaneio = romaneio;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getPreemb() {
        return preemb;
    }

    public void setPreemb(String preemb) {
        this.preemb = preemb;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public String getTipoImagem() {
        return tipoImagem;
    }

    public void setTipoImagem(String tipoImagem) {
        this.tipoImagem = tipoImagem;
    }

    public String getBufferImagem() {
        return bufferImagem;
    }

    public void setBufferImagem(String bufferImagem) {
        this.bufferImagem = bufferImagem;
    }
}
