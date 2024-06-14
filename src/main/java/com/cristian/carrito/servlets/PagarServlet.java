package com.cristian.carrito.servlets;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.cristian.carrito.controllers.ClienteDao;
import com.cristian.carrito.controllers.ProductoDao;
import com.cristian.carrito.models.Carrito;
import com.cristian.carrito.models.Cliente;
import com.cristian.carrito.models.ElementoDeCarrito;
import com.cristian.carrito.models.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/pagar.jsp")
public class PagarServlet extends HttpServlet {

	private ProductoDao productoDao = new ProductoDao();
	private ClienteDao clienteDao = new ClienteDao();
	private PDDocument documento = new PDDocument();
	private PDPage pagina = new PDPage();
	private double acumulado = 0;
	
	private final String PATH_TO_IMGS_DIR = "C:\\Cristian portatil\\1. Documentos Cristian portátil\\DAW\\Desarrollo web en servidor\\ProyectosJSP\\CarritoDeCompra\\src\\main\\webapp\\imgs";
	private final String FILE_PATH = "C:\\pdfs\\"; 

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] codigosProducto = req.getParameterValues("productosCarrito[]");
		String[] cantidades = req.getParameterValues("cantidades[]");

		List<ElementoDeCarrito> elementosCarrito = new ArrayList<>();
		if (codigosProducto != null && cantidades != null && codigosProducto.length == cantidades.length) {
			for (int i = 0; i < codigosProducto.length; i++) {
				ElementoDeCarrito elemento = new ElementoDeCarrito();
				int codigoProducto = Integer.parseInt(codigosProducto[i]);
				int catidadProducto = Integer.parseInt(cantidades[i]);
				Producto producto = productoDao.getById(codigoProducto);
				elemento.setCantidad(catidadProducto);
				elemento.setProducto(producto);
				elementosCarrito.add(elemento);
			}
		}

		Cookie[] cookies = req.getCookies();
		String userEmail = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userEmail")) {
					userEmail = cookie.getValue();
				}
			}
		}

		Cliente cliente = clienteDao.getClientByEmail(userEmail);

	    String contextPath = req.getServletContext().getRealPath(".");
	    
	   // String documentFilePath = contextPath + "\\pdfs\\documento" + System.currentTimeMillis() + ".pdf";
	    String documentFilePath = FILE_PATH + "factura" +  System.currentTimeMillis() + ".pdf";
		
	    try {
			PDPageContentStream contentStream = new PDPageContentStream(documento, pagina);

			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

			File file = new File(PATH_TO_IMGS_DIR + "\\logo.png");
			PDImageXObject pdImage = PDImageXObject.createFromFileByContent(file, documento);
			contentStream.drawImage(pdImage, 10, 680);

			// Añadir página y trabajar con líneas
			documento.addPage(pagina);

			String[] textos2 = new String[5];
			textos2[0] = "Xogos Tradicionais Galegos";
			textos2[1] = "C/ Daniel Vázquez Boo, Nº1, 1ºB";
			textos2[2] = "C.P. 27500. Chantada (LUGO) España";
			textos2[3] = "tel: 622222396";
			textos2[4] = "email: cristianchantada@gmail.com";

			int y = 770;
			for (int i = 0; i < textos2.length; i++) {
				veñaEscribe(contentStream, 130, y, textos2[i], 10);
				y -= 20;
			}

			String[] textos3 = new String[5];
			textos2[0] = cliente.getNombre() + ", NIF " + cliente.getNif();
			textos2[1] = "C/ Inventada, Nº2, 3ºB";
			textos2[2] = "C.P. 27500. Chantada (LUGO) España";
			textos2[3] = "tel: " + cliente.getTelefono();
			textos2[4] = "email: " + cliente.getEmail();

			y = 770;
			for (int i = 0; i < textos3.length; i++) {
				veñaEscribe(contentStream, 420, y, textos2[i], 10);
				y -= 20;
			}

			veñaEscribe(contentStream, 142, 630, "XOGOS TRADICIONAIS GALEGOS", 24);

			int separacion = 50;

			veñaEscribe(contentStream, 142, 600, "Factura: ", 14);
			veñaEscribe(contentStream, 310, 600, "Precio", 12);
			veñaEscribe(contentStream, 400, 600, "Cantidad", 12);
			veñaEscribe(contentStream, 520, 600, "Total", 12);

			y = 560;
			int x = 110;
			separacion = 100;
			for (ElementoDeCarrito elementoCarro : elementosCarrito) {

				double precio = elementoCarro.getProducto().getPrecio();
				int cantidad = elementoCarro.getCantidad();
				double totalProducto = precio * cantidad;
				acumulado += totalProducto;

				veñaEscribe(contentStream, (x += separacion), y, elementoCarro.getProducto().getNombre(), 12);
				veñaEscribe(contentStream, (x += separacion), y, String.valueOf(precio) + " €", 12);
				veñaEscribe(contentStream, (x += separacion), y, "x " + String.valueOf(cantidad), 12);
				veñaEscribe(contentStream, (x += separacion), y, String.valueOf(totalProducto) + " €", 12);
				x = 110;
				y -= 20;
			}

			veñaEscribe(contentStream, x += separacion, y, "-".repeat(80), 12);
			y -= 20;
			x = 210;
			veñaEscribe(contentStream, x += (separacion * 2), y, "Total sin IVA:", 12);
			veñaEscribe(contentStream, x += (separacion), y,
					String.format("%.2f", (acumulado - (acumulado * 0.21))) + " €", 12);

			y -= 20;
			x = 210;
			veñaEscribe(contentStream, x += (separacion * 2), y, "IVA:", 12);
			veñaEscribe(contentStream, x += (separacion), y, String.format("%.2f", (acumulado * 0.21)) + " €", 12);
			y -= 20;
			x = 210;
			veñaEscribe(contentStream, x += (separacion * 2), y, "Total:", 12);
			veñaEscribe(contentStream, x += (separacion), y, String.format("%.2f", acumulado) + " €", 12);

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
