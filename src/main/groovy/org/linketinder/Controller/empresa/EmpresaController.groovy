package org.linketinder.Controller.empresa

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.linketinder.Model.EmpresaModel
import org.linketinder.dao.EmpresaDao

import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.google.gson.Gson

@WebServlet("/empresa")
class EmpresaController extends HttpServlet{

    private static final long serialVersionUID = 1L
    private Gson gson = new Gson()

    EmpresaDao empresaDao = new  EmpresaDao()

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EmpresaModel> empresas = empresaDao.listar();

            String jsonEmpresas = convertToJSON(empresas);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonEmpresas);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao obter a lista de empresas.");
        }
    }

    private static String convertToJSON(List<EmpresaModel> empresas) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(empresas);
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

            EmpresaModel empresaModel = gson.fromJson(jsonInput.toString(), EmpresaModel.class)

            empresaDao.inserir(empresaModel)

            response.setContentType("application/json")
            response.setCharacterEncoding("UTF-8")
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("Empresa inserida com sucesso")
        }catch ( Exception e) {
            e.printStackTrace()
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            response.getWriter().write("Erro ao inserir empresa")
        }
    }



    List listaEmpresas(){
        return empresaDao.listar()
    }

    int inserirEmpresaController(newEmpresa, idPais) {
        return empresaDao.inserir(newEmpresa, idPais)
    }

    boolean deletarEmpresaController(String nomeEmpresa) {
        return empresaDao.deletar(nomeEmpresa)
    }
}
