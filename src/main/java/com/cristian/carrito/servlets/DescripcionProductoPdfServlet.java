package com.cristian.carrito.servlets;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.cristian.carrito.controllers.ProductoDao;
import com.cristian.carrito.models.Carrito;
import com.cristian.carrito.models.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/descripcion")
public class DescripcionProductoPdfServlet extends HttpServlet {
	

	private double acumulado = 0;
	private final String PATH_TO_IMGS_DIR = "C:\\Cristian portatil\\1. Documentos Cristian portátil\\DAW\\Desarrollo web en servidor\\ProyectosJSP\\CarritoDeCompra\\src\\main\\webapp\\imgs";
	private final String FILE_PATH = "C:\\pdfs"; 


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		ProductoDao productoDao = new ProductoDao();
		PDDocument documento = new PDDocument();
		PDPage pagina = new PDPage();
	   
	    String idStr = req.getParameter("id");  	
	    Producto producto = productoDao.getById(Integer.valueOf(idStr));
	    
	    String contextPath = req.getServletContext().getRealPath(".");
	    
	    //String documentFilePath = contextPath + "\\pdfs\\documento"+ LocalDate.now() + ".pdf"; 
	    String documentFilePath = FILE_PATH + "\\descripcion" + System.currentTimeMillis() + ".pdf"; 
	    
	    try {
			PDPageContentStream contentStream = new PDPageContentStream(documento, pagina);
		
	    	contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
	    	
	    	File file = new File(PATH_TO_IMGS_DIR + "\\logo.png");	    	
	    	PDImageXObject pdImage = PDImageXObject.createFromFileByContent(file, documento);
	    	contentStream.drawImage(pdImage, 10, 680);
	    	
	    	

	    	Random rand = new Random();
			int randomNumber = rand.nextInt(8 - 1 + 1) + 1;
	    		    	
	    	//Añadir página y trabajar con líneas
	    	documento.addPage(pagina);
	    	
	    	
	    	String[] textos2 = new String[5];
	    	textos2[0] = "Xogos Tradicionais Galegos";
	    	textos2[1] = "C/ Daniel Vázquez Boo, Nº1, 1ºB";
	    	textos2[2] = "C.P. 27500. Chantada (LUGO) España";
	    	textos2[3] = "tel: 622222396";
	    	textos2[4] = "email: cristianchantada@gmail.com";
	    	
	    	int y = 770;
	    	for(int i = 0; i < textos2.length; i++) {
	    		veñaEscribe(contentStream, 130, y, textos2[i] , 10);	
	    		y -= 20;
	    	}
	    	
	
	    	veñaEscribe(contentStream, 130, 630, "XOGOS TRADICIONAIS GALEGOS", 24);
	    	
	    	
	    	String[] textos = new String[5];
	    	textos[0] = "El juego tradicional " + producto.getNombre() + " y un precio de " + producto.getPrecio() + " € es un clásico en la cultura"; 
	    	textos[1] = "ancestral de Galicia con el que nuestros abuelos han jugado de niños desde tiempos";
	    	textos[2] = "inmemoriales. Es ideal tanto para ñiños como para adultos, requiriendo un mínimo de ";
	    	textos[3] = "cuatro jugadores y hata un máximo de 12. Está fabricado con madera de carballo y";
	    	textos[4] = "hecho a mano por artesanos locales";
	    	
	    	y = 600;
	    	for(int i = 0; i < textos.length; i++) {
	    		veñaEscribe(contentStream, 142, y, textos[i] , 10);	
	    		y -= 20;
	    	}
	    	
	    	veñaEscribe(contentStream, 142, 280, "Fotografía del producto " +  producto.getNombre() + ".", 10);	
	    	veñaEscribe(contentStream, 142, 260, "Precio: " + producto.getPrecio() + " €" ,10);
	    	
	    	File juegoImagenFile = new File(PATH_TO_IMGS_DIR + "\\" + randomNumber + ".png");
	    	PDImageXObject pdImageJuego = PDImageXObject.createFromFileByContent(juegoImagenFile, documento);
	    	contentStream.drawImage(pdImageJuego, 142, 300);
	    	
	    	contentStream.close();
	    	documento.save(documentFilePath);
	    	documento.close();
	    	
	    	HttpSession session = req.getSession();
	    	Carrito carrito = new Carrito();
	    	session.setAttribute("carrito", carrito);
	    	
	    	getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

	    } catch (IOException e) {
	    	e.printStackTrace();
	   }
  

	}
	
	public void veñaEscribe(PDPageContentStream contentStream, int x, int y, String texto, int tamañoLetra) {
    	try {
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, tamañoLetra);
	    	contentStream.beginText();
	    	contentStream.newLineAtOffset(x, y);
	    	contentStream.showText(texto);
	    	contentStream.endText();
    	} catch (IOException e) {
    	 	System.err.println("Error al crear el documento PDF: " + e.getMessage());
    	    
			e.printStackTrace();
		}

	}
	
	public void veñaEscribe(PDPageContentStream contentStream, int x, int y, Double dinero, int tamañoLetra) {
    	try {
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, tamañoLetra);
	    	contentStream.beginText();
	    	contentStream.newLineAtOffset(x, y);
	    	contentStream.showText(String.valueOf(dinero));
	    	contentStream.endText();
    	} catch (IOException e) {
    	 	System.err.println("Error al crear el documento PDF: " + e.getMessage());
    	    
			e.printStackTrace();
		}

	}
		
	}
	

