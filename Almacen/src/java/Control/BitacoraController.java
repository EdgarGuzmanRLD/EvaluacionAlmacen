/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Servicios.BitacoraService;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edgar
 */
@WebServlet(name = "BitacoraController", urlPatterns = {"/bitacora"})
public class BitacoraController extends HttpServlet{

    private final BitacoraService bitacoraService;
    private final Gson gson = new Gson();

    public BitacoraController() {
        this.bitacoraService = new BitacoraService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String jsonResponse;

            switch (action) {
                case "consultarBitacora":
                    jsonResponse = gson.toJson(bitacoraService.consultarBitacora(
                            request.getParameter("filtro"))
                    );
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    jsonResponse = "{\"error\":\"Acción no válida\"}";
            }

            response.getWriter().write(jsonResponse);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"Parámetros inválidos\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
