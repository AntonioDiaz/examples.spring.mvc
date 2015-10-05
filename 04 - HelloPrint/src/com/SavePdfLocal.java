package com;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class SavePdfLocal {

	public static void main(String[] args) throws Exception {
		System.out.println("working with documents...");
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("c:/Users/toni/Desktop/salida.pdf"));
		Rectangle one = new Rectangle(226.72f, 340.08f);
//		Rectangle two = new Rectangle(700, 400);
		document.setPageSize(one);
		document.setMargins(2, 2, 2, 2);
		document.open();
		Paragraph p = new Paragraph("Hi");
		document.add(p);
//		document.setPageSize(two);
//		document.setMargins(20, 20, 20, 20);
//		document.newPage();
//		document.add(p);
		document.close();
	}
}
