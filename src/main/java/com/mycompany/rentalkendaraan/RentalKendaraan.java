package com.mycompany.rentalkendaraan;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Kendaraan;
import model.Mobil;
import model.Motor;

public class RentalKendaraan {
    private static List<Kendaraan> daftarKendaraan = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("              SISTEM RENTAL KENDARAAN            ");
        System.out.println("=================================================\n");

        Scanner input = new Scanner(System.in);

        daftarKendaraan.add(new Mobil("Toyota Avanza", "B1234AB", 300000, 7));
        daftarKendaraan.add(new Motor("Honda Beat", "B5678CD", 100000, 110));

        int pilihan;
        do {
            System.out.println("\n=========== MENU ===========");
            System.out.println("1. Tambah Kendaraan");
            System.out.println("2. Lihat Semua Kendaraan");
            System.out.println("3. Update Harga Sewa Kendaraan");
            System.out.println("4. Hapus Kendaraan");
            System.out.println("5. Sewa Kendaraan");
            System.out.println("6. Kembalikan Kendaraan");
            System.out.println("7. Keluar");
            System.out.print("Pilih menu (1-7): ");

            pilihan = Integer.parseInt(input.nextLine());

            switch (pilihan) {
                case 1:
                    tambahKendaraan(input);
                    break;
                case 2:
                    tampilkanSemuaKendaraan();
                    break;
                case 3:
                    updateHarga(input);
                    break;
                case 4:
                    hapusKendaraan(input);
                    break;
                case 5:
                    sewaKendaraan(input);
                    break;
                case 6:
                    kembalikanKendaraan(input);
                    break;
                case 7:
                    System.out.println("Keluar dari program...");
                    break;
                default:
                    System.out.println(">> Pilihan tidak valid, coba lagi.");
            }
        } while (pilihan != 7);

        input.close();
    }

    // ---------- CREATE ----------
    private static void tambahKendaraan(Scanner input) {
        System.out.print("Jenis kendaraan (1=Mobil, 2=Motor): ");
        int jenis = Integer.parseInt(input.nextLine());

        System.out.print("Nama kendaraan: ");
        String nama = input.nextLine();
        System.out.print("Plat nomor: ");
        String plat = input.nextLine();
        System.out.print("Harga sewa per hari: ");
        double harga = Double.parseDouble(input.nextLine());

        if (jenis == 1) {
            System.out.print("Jumlah kursi: ");
            int kursi = Integer.parseInt(input.nextLine());
            daftarKendaraan.add(new Mobil(nama, plat, harga, kursi));
        } else if (jenis == 2) {
            System.out.print("Kapasitas CC: ");
            int cc = Integer.parseInt(input.nextLine());
            daftarKendaraan.add(new Motor(nama, plat, harga, cc));
        } else {
            System.out.println(">> Jenis kendaraan tidak valid.");
            return;
        }
        System.out.println(">> Kendaraan berhasil ditambahkan.");
    }

    // ---------- READ ----------
    // POLYMORPHISM: cukup panggil k.tampilkanInfo() tanpa perlu cek tipe/instanceof.
    // Java otomatis menjalankan versi Mobil atau Motor sesuai objek aslinya.
    private static void tampilkanSemuaKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println(">> Belum ada data kendaraan.");
            return;
        }
        int no = 1;
        for (Kendaraan k : daftarKendaraan) {
            System.out.print("#" + no + " ");
            k.tampilkanInfo();
            no++;
        }
    }

    // Cari kendaraan berdasarkan plat nomor
    private static Kendaraan cariKendaraan(String platNomor) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getPlatNomor().equalsIgnoreCase(platNomor)) {
                return k;
            }
        }
        return null;
    }

    // ---------- UPDATE ----------
    private static void updateHarga(Scanner input) {
        System.out.print("Masukkan plat nomor: ");
        Kendaraan k = cariKendaraan(input.nextLine());
        if (k == null) {
            System.out.println(">> Kendaraan tidak ditemukan.");
            return;
        }
        System.out.print("Harga sewa baru: ");
        k.setHargaSewaPerHari(Double.parseDouble(input.nextLine()));
        System.out.println(">> Harga sewa berhasil diupdate.");
    }

    // ---------- DELETE ----------
    private static void hapusKendaraan(Scanner input) {
        System.out.print("Masukkan plat nomor yang mau dihapus: ");
        Kendaraan k = cariKendaraan(input.nextLine());
        if (k == null) {
            System.out.println(">> Kendaraan tidak ditemukan.");
            return;
        }
        daftarKendaraan.remove(k);
        System.out.println(">> Kendaraan berhasil dihapus.");
    }

    // ---------- SEWA ----------
    // Menggunakan method sewa(nama, jumlahHari) yang OVERLOADING dari sewa(nama).
    // Perhitungan totalnya OVERRIDING: beda rumus antara Mobil dan Motor,
    // tapi dipanggil lewat cara yang sama (polymorphism).
    private static void sewaKendaraan(Scanner input) {
        System.out.print("Plat nomor kendaraan: ");
        Kendaraan k = cariKendaraan(input.nextLine());
        if (k == null) {
            System.out.println(">> Kendaraan tidak ditemukan.");
            return;
        }
        if (k.getStatus().equals("Disewa")) {
            System.out.println(">> Maaf, kendaraan ini sedang disewa orang lain.");
            return;
        }
        System.out.print("Nama penyewa: ");
        String nama = input.nextLine();
        System.out.print("Jumlah hari sewa: ");
        int jumlahHari = Integer.parseInt(input.nextLine());

        double totalBiaya;
        // Opsi pakai supir hanya berlaku untuk Mobil, memanfaatkan method
        // overload hitungTotalBiaya(int, boolean) yang ada di class Mobil.
        if (k instanceof Mobil) {
            System.out.print("Pakai supir? (y/n): ");
            boolean pakaiSupir = input.nextLine().trim().equalsIgnoreCase("y");
            Mobil mobil = (Mobil) k;
            totalBiaya = mobil.hitungTotalBiaya(jumlahHari, pakaiSupir);
            k.sewa(nama);
        } else {
            totalBiaya = k.sewa(nama, jumlahHari);
        }

        System.out.println(">> Kendaraan berhasil disewa oleh " + nama + ".");
        System.out.printf(">> Total biaya untuk %d hari: Rp%.0f\n", jumlahHari, totalBiaya);
    }

    // ---------- KEMBALIKAN ----------
    private static void kembalikanKendaraan(Scanner input) {
        System.out.print("Masukkan plat nomor yang dikembalikan: ");
        Kendaraan k = cariKendaraan(input.nextLine());
        if (k == null) {
            System.out.println(">> Kendaraan tidak ditemukan.");
            return;
        }
        if (k.getStatus().equals("Tersedia")) {
            System.out.println(">> Kendaraan ini sedang tidak disewa.");
            return;
        }
        k.kembalikan();
        System.out.println(">> Kendaraan berhasil dikembalikan.");
    }
}