package br.cesjf.lppo;

import br.cesjf.lppo.Reclamacao;
import br.cesjf.lppo.ListaReclamacoesServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Adriano
 */
@WebServlet(name = "EditaReclamacao", urlPatterns = {"/edita.html"})
public class EditaReclamacao extends HttpServlet {

       @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Reclamacao reclamacao = new Reclamacao();
        Long id = Long.parseLong(request.getParameter("id"));

        try {
            //Pegar os dados do banco
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection conexao = DriverManager.getConnection("jdbc:derby://localhost:1527/lppo-2017-1", "usuario", "usuario");
            Statement operacao = conexao.createStatement();
            ResultSet resultado = operacao.executeQuery("SELECT * FROM reclamacao WHERE id = " + id);
            if (resultado.next()) {
                reclamacao.setId(resultado.getLong("id"));
                reclamacao.setNome(resultado.getString("nome"));
                reclamacao.setEmail(resultado.getString("email"));
                reclamacao.setDescricao(resultado.getString("descricao"));
                reclamacao.setStatus(resultado.getInt("status"));
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListaReclamacoesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ListaReclamacoesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("reclamacao", reclamacao);
        request.getRequestDispatcher("WEB-INF/editaReclamacao.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
