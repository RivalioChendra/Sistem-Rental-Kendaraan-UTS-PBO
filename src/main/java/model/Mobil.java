package model;

public class Mobil extends Kendaraan {
    private int jumlahKursi;
    private static final double BIAYA_SUPIR_PER_HARI = 100000;

    public Mobil(String namaKendaraan, String platNomor, double hargaSewaPerHari, int jumlahKursi) {
        super(namaKendaraan, platNomor, hargaSewaPerHari);
        this.jumlahKursi = jumlahKursi;
    }

    public int getJumlahKursi() { return jumlahKursi; }

    // OVERRIDING: total biaya mobil = sewa harian x hari (mengikuti aturan parent)
    @Override
    public double hitungTotalBiaya(int jumlahHari) {
        return super.hitungTotalBiaya(jumlahHari);
    }

    // OVERLOADING: total biaya mobil dengan opsi pakai supir
    public double hitungTotalBiaya(int jumlahHari, boolean pakaiSupir) {
        double total = hitungTotalBiaya(jumlahHari);
        if (pakaiSupir) {
            total += BIAYA_SUPIR_PER_HARI * jumlahHari;
        }
        return total;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | Kursi: %d\n", jumlahKursi);
    }
}