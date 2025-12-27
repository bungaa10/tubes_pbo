package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.util.List;

import model.Hewan;

public class HewanPdfGenerator {

    public static void generate(List<Hewan> hewans, String filePath) throws Exception {

    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(filePath));

    document.open();

    document.add(new Paragraph("Laporan Data Hewan Klinik"));
    document.add(new Paragraph(" "));

    PdfPTable table = new PdfPTable(5);
    table.setWidthPercentage(100);

    table.addCell("ID");
    table.addCell("Nama");
    table.addCell("Jenis");
    table.addCell("Umur");
    table.addCell("Pemilik");

    for (Hewan h : hewans) {
        table.addCell(String.valueOf(h.getIdHewan()));
        table.addCell(h.getNama());
        table.addCell(h.getJenis());
        table.addCell(h.getUmur());
        table.addCell(h.getPemilik());
    }

    document.add(table);
    document.close();
}

}
