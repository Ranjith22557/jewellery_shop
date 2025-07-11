package com.rtech.jewellery.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PdfGeneratorService {

    public byte[] generatePdf(String customerName, String item, BigDecimal totalAmount) throws Exception{

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document,out);
        document.open();
        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN,12);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        //Header
        Paragraph header = new Paragraph("Murugan Jewellery",headerFont);
        header.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(header);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Name : "+customerName,font));
        document.add(new Paragraph("Date : "+currentDate,font));
        document.add(new Paragraph("Item : "+item,font));
        document.add(new Paragraph("Gram : "+item));
        document.add(new Paragraph("Amount : "+totalAmount,font));

        document.close();
        return out.toByteArray();
    }
}
