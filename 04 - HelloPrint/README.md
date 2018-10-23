## Print from Spring MVC 3.2.
Example of use iText to generate a PDF and print it from browse in Chrome in Spring MVC 3.2.  

**Important points**
* [Project Structure](#project-structure)  
* [pom.xml](#pomxml)  
* [Standalone example](#standalone-example)
* [applicationContext.xml](#applicationcontextxml)
* [views.properties](#viewsproperties)
* [PrintController.java](#printcontrollerjava)
* [PDFBuilder.java](#pdfbuilderjava)
* [AbstractITextPdfView.java](#abstractitextpdfviewjava)


***
##### Project Structure
![](https://antoniodiaz.github.io/images/spring_mvc/04_structure.jpg)

***
##### pom.xml
``` xml  
<dependency>
	<groupId>com.itextpdf</groupId>
	<artifactId>itextpdf</artifactId>
	<version>${itext.version}</version>
</dependency>
```  
***

##### Standalone example  
standalone iText java application:
``` java
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
		document.setPageSize(one);
		document.setMargins(2, 2, 2, 2);
		document.open();
		Paragraph p = new Paragraph("Hi");
		document.add(p);
		document.close();
	}
}
```
***
##### applicationContext.xml  
``` xml
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:context="http://www.springframework.org/schema/context" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xsi:schemaLocation="
            http://www.springframework.org/schema/beans     
            http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
            http://www.springframework.org/schema/mvc
            http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context-3.2.xsd">
	<context:component-scan base-package="com" />
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="order" value="2"></property>
		<property name="prefix">
			<value>/WEB-INF/jsp/</value>
		</property>
		<property name="suffix">
			<value>.jsp</value>
		</property>
	</bean>
	<bean id="viewResolver" class="org.springframework.web.servlet.view.ResourceBundleViewResolver">
		<property name="order" value="1"></property>
		<property name="basename" value="views"></property>
	</bean>
</beans>
```
***

##### views.properties
``` java
pdfView.(class)=com.PDFBuilder
```

##### PrintController.java
``` java
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrintController {
	@RequestMapping(value = "/getPdf", method = RequestMethod.GET)
	public ModelAndView getPdf() {
		ModelAndView modelAndView = new ModelAndView("pdfView");
		String[] libros = new String[]{"El Quijote", "La celestina"};		
		modelAndView.addObject("libros_list", Arrays.asList(libros));
		return modelAndView;
	}
}
```
***
##### PDFBuilder.java
``` java  
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
```
***
##### AbstractITextPdfView.java
``` java
package com;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *  * This class is a work around for working with iText 5.x in Spring.  * The
 * code here is almost identical to the AbstractPdfView class.  *  
 */
public abstract class AbstractITextPdfView extends AbstractView {

	public AbstractITextPdfView() {
		setContentType("application/pdf");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();
		// Apply preferences and build metadata.
		Document document = newDocument();
		PdfWriter writer = newWriter(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);
		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();
		// Flush to HTTP response.
		writeToResponse(response, baos);
	}

	protected Document newDocument() {
		Document document = new Document();
		Rectangle one = new Rectangle(226.72f, 340.08f);
		document.setPageSize(one);
		document.setMargins(2, 2, 2, 2);
		return document;
	}

	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}

	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request) throws DocumentException {
		writer.setViewerPreferences(getViewerPreferences());
	}

	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}

	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}

	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
```
