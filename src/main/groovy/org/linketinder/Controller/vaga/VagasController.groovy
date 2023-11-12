package org.linketinder.Controller.vaga

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.linketinder.Model.VagaModel
import org.linketinder.dao.VagasDao

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/vagas")
class VagasController extends HttpServlet{


    private static final long serialVersionUID = 1L
    private Gson gson = new Gson()

    VagasDao vagasDao = new VagasDao()


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<VagaModel> vagas = vagasDao.listarVagas()

            String jsonVagas = convertToJSON(vagas)

            response.setContentType("application/json")
            response.setCharacterEncoding("UTF-8")
            response.getWriter().write(jsonVagas)
        } catch (Exception e) {
            e.printStackTrace()
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao obter a lista de vagas.");
        }
    }

    private static String convertToJSON(List<VagaModel> vagas) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(vagas);
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

            VagaModel vagaModel = gson.fromJson(jsonInput.toString(), VagaModel.class)

            vagasDao.inserirVaga(vagaModel)

            response.setContentType("application/json")
            response.setCharacterEncoding("UTF-8")
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("Vaga inserida com sucesso")
        }catch ( Exception e) {
            e.printStackTrace()
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.getWriter().write("Erro ao inserir vaga")
        }
    }


    List listaVagas(){
        return vagasDao.listarVagas()
    }

    int inserirVagaController(newVaga) {
        return vagasDao.inserirVaga(newVaga)
    }

    boolean deletarVagaController(String nomeVaga) {
        return vagasDao.deletarVaga(nomeVaga);
    }
}
