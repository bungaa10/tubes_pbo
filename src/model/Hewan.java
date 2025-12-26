package model;

public class Hewan {

    private int idHewan;
    private String nama;
    private String jenis;
    private String umur;
    private String pemilik;
    

    public Hewan() {}

    public Hewan(int idHewan, String nama, String jenis, String umur,
                 String pemilik) {
        this.idHewan = idHewan;
        this.nama = nama;
        this.jenis = jenis;
        this.umur = umur;
        this.pemilik = pemilik;
    }

    // 🔥 INI WAJIB BENAR
    public int getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(int idHewan) {
        this.idHewan = idHewan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }
}