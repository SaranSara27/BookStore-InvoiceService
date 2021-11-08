package com.hcl.bookorder.invoiceservice.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hcl.bookorder.invoiceservice.model.Order;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class InvoiceService {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

	/**
	 * Generate the Place Order PDF Invoice file and save it in the specified
	 * location.
	 * 
	 * @param order
	 * @throws DocumentException
	 * @throws IOException
	 */
	public ByteArrayInputStream generatePlaceOrderInvoice(Order order) throws DocumentException, IOException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);
		document.open();

		// Book Store
		Paragraph paragraph = new Paragraph("Book Store");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		paragraph = new Paragraph("-------------");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		// Invoice Header
		paragraph = new Paragraph("Invoice for Confirmed order");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		getBasicDetails(order, document);

		// Orders
		paragraph= new Paragraph("              ");
		document.add(paragraph);
		paragraph = new Paragraph("Ordered Books");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		paragraph= new Paragraph("              ");
		document.add(paragraph);
		
		// Order List
		PdfPTable table = new PdfPTable(4);
		generateTable(order, table);
		document.add(table);

		// Total Cost
		paragraph = new Paragraph("Total Cost : " + order.getTotal());
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		// Mode Of Payment
		paragraph = new Paragraph("Payment Mode : " + order.getPaymentType());
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);
		
		// Final Message
		paragraph= new Paragraph("              ");
		document.add(paragraph);
		paragraph = new Paragraph("Thank you for the Purchase!");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.close();

		return new ByteArrayInputStream(out.toByteArray());

	}

	private void getBasicDetails(Order order, Document document) throws DocumentException {
		Paragraph paragraph;
		// Order Details
		paragraph = new Paragraph("Order Id: " + order.getOrderId());
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		// Customer Details
		paragraph = new Paragraph("Customer Id : " + order.getUserId());
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		// Address Details
		paragraph = new Paragraph("Delivery Address: " + order.getDeliveryAddress());
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);
		
		// Date Details
		paragraph = new Paragraph("Ordered Date: " + order.getOrderDate());
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);
	}

	private void generateTable(Order order, PdfPTable table) {
		
		// Table Header
		Stream.of("Book Id", " Book Name", "Quantity", "Price").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(1.5f);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
		
		String[] productIds = order.getProductId().split(",");
		String[] productNames = order.getProductName().split(",");
		String[] productQtys = order.getQty().split(",");
		String[] productprices = order.getPrice().split(",");
		int size=productIds.length;
		if(size==productNames.length && size==productQtys.length && size==productprices.length) {
			for(int i=0;i<size;i++) {
				// Table Body
				table.addCell(productIds[i]);
				table.addCell(productNames[i]);
				table.addCell(productQtys[i]);
				table.addCell(productprices[i]);
			}
		}
		
	}

	/**
	 * Generate the Cancel Order PDF Invoice file and save it in the specified
	 * location.
	 * 
	 * @param order
	 * @throws DocumentException
	 * @throws IOException
	 */
	public ByteArrayInputStream generateCancelOrderInvoice(Order order) throws DocumentException, IOException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);
		document.open();

		// Book Store
		Paragraph paragraph = new Paragraph("Book Store");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		paragraph = new Paragraph("-----------");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		// Invoice Header
		paragraph = new Paragraph("Order Cancellation Notification");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		getBasicDetails(order, document);

		// Orders
		paragraph= new Paragraph("              ");
		document.add(paragraph);
		paragraph = new Paragraph("Cancelled Book Orders");
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);
		paragraph= new Paragraph("              ");
		document.add(paragraph);

		// Order List
		PdfPTable table = new PdfPTable(4);
		generateTable(order, table);
		document.add(table);

		// Final Message
		paragraph= new Paragraph("              ");
		document.add(paragraph);
		paragraph = new Paragraph("Sorry for the inconvenience. We hope to receive orders from you in the future.");
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		document.close();

		return new ByteArrayInputStream(out.toByteArray());

	}

}
