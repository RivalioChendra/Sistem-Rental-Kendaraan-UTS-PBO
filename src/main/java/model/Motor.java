package model;

public class Motor extends Kendaraan {
    private int kapasitasCC;
    private static final double DISKON_MINGGUAN = 0.9; // diskon 10% jika sewa >= 7 hari

    public Motor(String namaKendaraan, String platNomor, double hargaSewaPerHari, int kapasitasCC) {
        super(namaKendaraan, platNomor, hargaSewaPerHari);
        this.kapasitasCC = kapasitasCC;
    }

    public int getKapasitasCC() { return kapasitasCC; }

    // OVERRIDING: total biaya motor punya aturan diskon sendiri (beda dari Mobil)
    @Override
    public double hitungTotalBiaya(int jumlahHari) {
        double total = super.hitungTotalBiaya(jumlahHari);
        if (jumlahHari >= 7) {
            total *= DISKON_MINGGUAN;
        }
        return total;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | Kapasitas: %dcc\n", kapasitasCC);
    }
}