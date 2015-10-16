package com;

import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PDFBuilder extends AbstractITextPdfView {

	public static final String RESOURCE = "http://testcorporal.appspot.com/resources/images/page.jpg";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		document.add(new Paragraph("Recommended books for Spring framework"));
		Image img = Image.getInstance(new URL(RESOURCE));
		img.scalePercent(50);
		document.add(img);
		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);
		@SuppressWarnings("unchecked")
		List<String> myList = (List<String>)model.get("libros_list");
		for (String libro : myList) {
			document.add(new Paragraph(libro));
			document.add(Chunk.NEWLINE);			
		}
		LineSeparator lineSeparator = new LineSeparator();
		document.add(lineSeparator);
	}
}
