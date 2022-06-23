package com.nttdata.util.reporte.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nttdata.model.dao.Product;
import com.nttdata.util.reporte.GeneratePdfReport;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
@Component
public class GeneratePdfReportImpl implements GeneratePdfReport {


    public  ByteArrayInputStream clientReport(Product product){
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(80);
        table.setWidths(new int[]{25, 20, 20,18});

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("Tipo de cuenta", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Tipo de producto", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Numero de tarjeta", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Monto", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        product.getAccounts().stream().forEach(s->{
            PdfPCell cell = new PdfPCell(new Phrase(s.getAccountType().toString()));
            cell.setPaddingLeft(2);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(s.getProdcutType().toString()));
            cell.setPaddingLeft(2);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(s.getCardNumber())));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingRight(2);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(s.getAmount().toString())));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingRight(2);
            table.addCell(cell);
        });
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(table);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

}
