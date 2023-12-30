import java.util.ArrayList;
import java.util.Scanner;

class Kamar {
    private final int nomorKamar;
    private boolean tersedia;

    public Kamar(int nomorKamar) {
        this.nomorKamar = nomorKamar;
        this.tersedia = true;
    }

    public int getNomorKamar() {
        return nomorKamar;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }
}

class Pelanggan {
    private final String nama;
    private final String nomorTelepon;

    public Pelanggan(String nama, String nomorTelepon) {
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
    }

    public String getNama() {
        return nama;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }
}

class Reservasi {
    private final Pelanggan pelanggan;
    private final Kamar kamar;

    public Reservasi(Pelanggan pelanggan, Kamar kamar) {
        this.pelanggan = pelanggan;
        this.kamar = kamar;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public Kamar getKamar() {
        return kamar;
    }
}

class Pembayaran {
    private Reservasi reservasi;
    private double totalBiaya;

    public Pembayaran(Reservasi reservasi, double totalBiaya) {
        this.reservasi = reservasi;
        this.totalBiaya = totalBiaya;
    }

    public Reservasi getReservasi() {
        return reservasi;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }
}

class Hotel {
    private ArrayList<Kamar> daftarKamar = new ArrayList<>();
    private ArrayList<Reservasi> daftarReservasi = new ArrayList<>();

    public Hotel(int jumlahKamar) {
        for (int i = 1; i <= jumlahKamar; i++) {
            daftarKamar.add(new Kamar(i));
        }
    }

    public ArrayList<Kamar> getDaftarKamar() {
        return daftarKamar;
    }

    public ArrayList<Reservasi> getDaftarReservasi() {
        return daftarReservasi;
    }

    public void tambahReservasi(Reservasi reservasi) {
        daftarReservasi.add(reservasi);
    }
}

public class ReservasiHotel {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Hotel hotel = new Hotel(10);

        while (true) {
            System.out.println("\nMenu Reservasi Hotel:");
            System.out.println("1. Lihat Ketersediaan Kamar");
            System.out.println("2. Reservasi Kamar");
            System.out.println("3. Lihat Daftar Reservasi");
            System.out.println("4. Pembayaran");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1/2/3/4/5): ");

            int pilihan = scanner.nextInt();

            switch (pilihan) {
                case 1:
                    lihatKetersediaanKamar(hotel);
                    break;
                case 2:
                    reservasiKamar(hotel);
                    break;
                case 3:
                    lihatDaftarReservasi(hotel);
                    break;
                case 4:
                    pembayaran(hotel);
                    break;
                case 5:
                    System.out.println("Terima kasih. Selamat tinggal!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih lagi.");
            }
        }
    }

    private static void lihatKetersediaanKamar(Hotel hotel) {
        System.out.println("\nKetersediaan Kamar:");
        for (Kamar kamar : hotel.getDaftarKamar()) {
            System.out.println("Kamar " + kamar.getNomorKamar() + ": " +
                    (kamar.isTersedia() ? "Tersedia" : "Tidak Tersedia"));
        }
    }

    private static void reservasiKamar(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan nomor kamar yang ingin dipesan: ");
        int nomorKamar = scanner.nextInt();

        ArrayList<Kamar> daftarKamar = hotel.getDaftarKamar();
        for (Kamar kamar : daftarKamar) {
            if (kamar.getNomorKamar() == nomorKamar) {
                if (kamar.isTersedia()) {
                    kamar.setTersedia(false);

                    // Input informasi pelanggan
                    System.out.print("Masukkan nama pelanggan: ");
                    String namaPelanggan = scanner.next();
                    System.out.print("Masukkan nomor telepon pelanggan: ");
                    String nomorTelepon = scanner.next();

                    // Membuat objek Pelanggan dan Reservasi
                    Pelanggan pelanggan = new Pelanggan(namaPelanggan, nomorTelepon);
                    Reservasi reservasi = new Reservasi(pelanggan, kamar);

                    // Menambahkan reservasi ke daftar reservasi hotel
                    hotel.tambahReservasi(reservasi);

                    System.out.println("Reservasi berhasil. Kamar " + nomorKamar + " telah dipesan.");
                } else {
                    System.out.println("Maaf, kamar tidak tersedia.");
                }
                return;
            }
        }

        System.out.println("Nomor kamar tidak valid. Silakan coba lagi.");
    }

    private static void lihatDaftarReservasi(Hotel hotel) {
        System.out.println("\nDaftar Reservasi:");
        ArrayList<Reservasi> daftarReservasi = hotel.getDaftarReservasi();
        for (Reservasi reservasi : daftarReservasi) {
            System.out.println("Pelanggan: " + reservasi.getPelanggan().getNama() +
                    ", Nomor Telepon: " + reservasi.getPelanggan().getNomorTelepon() +
                    ", Kamar: " + reservasi.getKamar().getNomorKamar());
        }
    }

    private static void pembayaran(Hotel hotel) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nMenu Pembayaran:");
        System.out.print("Masukkan nomor kamar yang ingin dibayar: ");
        int nomorKamar = scanner.nextInt();

        ArrayList<Reservasi> daftarReservasi = hotel.getDaftarReservasi();
        for (Reservasi reservasi : daftarReservasi) {
            if (reservasi.getKamar().getNomorKamar() == nomorKamar) {
                if (reservasi.getKamar().isTersedia()) {
                    System.out.println("Nomor kamar tidak valid atau belum dipesan. Silakan coba lagi.");
                    return;
                }

                double totalBiaya = hitungTotalBiaya(reservasi);

                System.out.println("Total biaya reservasi untuk kamar " + nomorKamar + ": $" + totalBiaya);

                // Pilihan jenis pembayaran
                System.out.println("\nPilih jenis pembayaran:");
                System.out.println("1. Kartu Kredit");
                System.out.println("2. Transfer Bank");
                System.out.print("Pilih jenis pembayaran (1/2): ");
                int jenisPembayaran = scanner.nextInt();

                if (jenisPembayaran == 1) {
                    System.out.println("Pembayaran dengan Kartu Kredit berhasil.");
                } else if (jenisPembayaran == 2) {
                    System.out.println("Pembayaran dengan Transfer Bank berhasil.");
                } else {
                    System.out.println("Pilihan tidak valid. Pembayaran dibatalkan.");
                    return;
                }

                // Membuat objek Pembayaran
                Pembayaran pembayaran = new Pembayaran(reservasi, totalBiaya);

                // Hapus kamar dari daftar reservasi setelah pembayaran berhasil
                reservasi.getKamar().setTersedia(true);
                daftarReservasi.remove(reservasi);

                System.out.println("Pembayaran berhasil. Terima kasih!");

                // Menampilkan struk pembayaran
                tampilkanStrukPembayaran(pembayaran);
                return;
            }
        }

        System.out.println("Nomor kamar tidak valid. Silakan coba lagi.");
    }

    private static double hitungTotalBiaya(Reservasi reservasi) {
        // Implementasi logika perhitungan total biaya (misalnya: biaya kamar per malam, pajak, dll.)
        // Disini kita asumsikan biaya kamar per malam adalah $100
        int jumlahMalam = 1; // Bisa diubah sesuai kebutuhan
        double biayaKamarPerMalam = 100.0;
        return jumlahMalam * biayaKamarPerMalam;
    }

    private static void tampilkanStrukPembayaran(Pembayaran pembayaran) {
        System.out.println("\nStruk Pembayaran:");
        System.out.println("Pelanggan: " + pembayaran.getReservasi().getPelanggan().getNama());
        System.out.println("Nomor Telepon: " + pembayaran.getReservasi().getPelanggan().getNomorTelepon());
        System.out.println("Nomor Kamar: " + pembayaran.getReservasi().getKamar().getNomorKamar());
        System.out.println("Total Biaya: $" + pembayaran.getTotalBiaya());
        System.out.println("Terima kasih atas pembayaran Anda.");
    }
}
