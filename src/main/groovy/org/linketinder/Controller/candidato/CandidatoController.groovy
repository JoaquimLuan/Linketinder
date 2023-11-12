package org.linketinder.Controller.candidato

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.linketinder.Model.CandidatoModel
import org.linketinder.dao.CandidatoDao

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.google.gson.Gson

@WebServlet("/candidato")
class CandidatoController extends HttpServlet{

    private static final long serialVersionUID = 1L
    private Gson gson = new Gson()

    CandidatoDao candidatoDao = new CandidatoDao()

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CandidatoModel> candidatos = candidatoDao.listar();

            String jsonCandidatos = convertToJSON(candidatos);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonCandidatos);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao obter a lista de candidatos.");
        }
    }

    private static String convertToJSON(List<CandidatoModel> candidatos) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(candidatos);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

        @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try (BufferedReader reader = request.getReader()) {
            StringBuilder jsonInput = new StringBuilder()
            String line

            while ((line = reader.readLine()) != null) {
                jsonInput.append(line)
            }

            CandidatoModel candidatoModel = gson.fromJson(jsonInput.toString(), CandidatoModel.class)

            candidatoDao.inserir(candidatoModel)

            response.setContentType("application/json")
            response.setCharacterEncoding("UTF-8")
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("Candidato inserido com sucesso")
        }catch ( Exception e) {
            e.printStackTrace()
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.getWriter().write("Erro ao inserir candidato")
        }
    }

    List listaUsuarios(){
        return candidatoDao.listar()
    }

    int inserirCandidatoController(newCandidate, idPais) {
        return candidatoDao.inserir(newCandidate, idPais)
    }

    boolean deletarCandidatoController(String nomeCandidato) {
        return candidatoDao.deletar(nomeCandidato);
    }

}
