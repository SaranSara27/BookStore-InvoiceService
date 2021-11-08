package com.hcl.bookorder.invoiceservice.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.bookorder.invoiceservice.model.Order;
import com.hcl.bookorder.invoiceservice.service.InvoiceService;

import com.itextpdf.text.DocumentException;
import java.io.ByteArrayInputStream;


@RestController("/book")
public class BookInvoiceController {
	private static final Logger logger = LoggerFactory.getLogger(BookInvoiceController.class);
	@Autowired
	private InvoiceService service;
	
	/*
	 * @RequestMapping(value = "/invoice/test", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(value=HttpStatus.OK) public void testMethod() {
	 * logger.info("Inside test method"); }
	 */

	@RequestMapping(value = "/invoice/placeOrder", method = RequestMethod.POST,produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource>  placeOrder(@RequestBody Order order) {
		try {
        	ByteArrayInputStream bis = service.generatePlaceOrderInvoice(order);
        	HttpHeaders headers = new HttpHeaders();
        	headers.add("Content-Disposition", "inline; filename=PlaceOrderInvoice"+order.getOrderId()+".pdf");
			logger.info("Report has been generated for {} ", order.getOrderId());
			return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
		} catch (DocumentException | IOException e) {
			logger.error("Exception occured in BookInvoiceController - placeOrder",e);
		}
		return null;
	}

	@RequestMapping(value = "/invoice/cancelOrder", method = RequestMethod.POST,produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> cancelOrder(@RequestBody Order order) {
		try {
			ByteArrayInputStream bis = service.generateCancelOrderInvoice(order);
        	HttpHeaders headers = new HttpHeaders();
        	headers.add("Content-Disposition", "inline; filename=CancelOrderInvoice"+order.getOrderId()+".pdf");
			logger.info("Report has been generated for {} ", order.getOrderId());
			return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
		} catch (DocumentException | IOException e) {
			logger.error("Exception occured in BookInvoiceController - cancelOrder",e);
		}
		return null;
		
	}

}
