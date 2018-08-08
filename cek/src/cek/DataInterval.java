package cek;

public class DataInterval
{
    private float frekuensi, batasAtas, batasBawah;

    public float getBatasAtas() {
        return batasAtas;
    }

    public float getBatasBawah() {
        return batasBawah;
    }

    public float getFrekuensi() {
        return frekuensi;
    }

    public void setBatasAtas(float batasAtas) {
        this.batasAtas = batasAtas;
    }

    public void setBatasBawah(float batasBawah) {
        this.batasBawah = batasBawah;
    }

    public void setFrekuensi(float frekuensi) {
        this.frekuensi += frekuensi;
    }
}
