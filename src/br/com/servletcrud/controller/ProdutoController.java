package br.com.servletcrud.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.servletcrud.dao.ProdutoDAO;
import br.com.servletcrud.model.Produto;

/**
 * Servlet implementation class ProdutoController
 */
@WebServlet("/ProdutoController")
public class ProdutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProdutoDAO dao;
	private Produto prod = new Produto();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProdutoController() {
		super();
		dao = new ProdutoDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();

		if (action.equalsIgnoreCase("Salvar")) {
			try {
				if (!dao.existe(request.getParameter("sku"))) {

					prod.setSku(request.getParameter("sku"));
					prod.setProduto(request.getParameter("produto"));
					prod.setDesc(request.getParameter("desc"));
					prod.setQtd(Integer.parseInt(request.getParameter("qtd")));
					prod.setPreco(Double.parseDouble(request.getParameter("preco")));
					dao.salvar(prod);
					out.print("<script>alert('Registro salvo com sucesso!');</script>");
					request.getRequestDispatcher("index.html").include(request, response);
				} else {
					out.print("<script>alert('Registro já existe!');</script>");
					request.getRequestDispatcher("index.html").include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equalsIgnoreCase("Alterar")) {
			try {
				if (dao.existe(request.getParameter("sku"))) {
					prod.setSku(request.getParameter("sku"));
					prod.setProduto(request.getParameter("produto"));
					prod.setDesc(request.getParameter("desc"));
					prod.setQtd(Integer.parseInt(request.getParameter("qtd")));
					prod.setPreco(Double.parseDouble(request.getParameter("preco")));
					dao.atualizar(prod);
					out.print("<script>alert('Registro atualizado com sucesso!');</script>");
					request.getRequestDispatcher("index.html").include(request, response);
				} else {
					out.print("<script>alert('Registro não encontrado para alteração.');</script>");
					request.getRequestDispatcher("index.html").include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (action.equalsIgnoreCase("Excluir")) {
			try {
				if (dao.existe(request.getParameter("sku"))) {
					dao.excluir(request.getParameter("sku"));
					out.print("<script>alert('Registro excluído com sucesso!');</script>");
					request.getRequestDispatcher("index.html").include(request, response);
				} else {
					out.print("<script>alert('Registro não encontrado para exclusão.');</script>");
					request.getRequestDispatcher("index.html").include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equalsIgnoreCase("Pesquisar")) {
			try {
				String sku = request.getParameter("sku");
				if (dao.existe(sku)) {
					Produto unico = dao.buscar(sku);
					out.print("<table border='1' width='100%'");
					out.print(
							"<tr><th>SKU - ID</th><th>Produto</th><th>Descrição</th><th>Quantidade</th><th>Preço</th>");
					out.print("<tr><td>" + unico.getSku() + "</td><td>" + unico.getProduto() + "</td><td>"
							+ unico.getDesc() + "</td><td>" + unico.getQtd() + "</td><td>" + unico.getPreco()
							+ "</td>");
					out.print("</table>");
					request.getRequestDispatcher("index.html").include(request, response);
				} else {
					out.print("<script>alert('Registro não encontrado.');</script>");
					request.getRequestDispatcher("index.html").include(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (action.equalsIgnoreCase("Listar")) {
			List<Produto> lista;
			try {
				lista = dao.listar();
				out.print(
						"<html><head><title>Produtos</title><style>th, td{padding: 8px;text-align: left;border-bottom: 1px solid #ddd;} table{border-collapse: collapse;width: 100%;}tr:hover {background-color:#f5f5f5;}th{background-color: #969696;color: white;}</style></head><body><table style='border-collapse: collapse; border: 1px solid black;'");
				out.print(
						"<tr><th>SKU - ID</th><th>PRODUTO</th><th>DESCRIÇÃO</th><th>QUANTIDADE</th><th>PREÇO</th><th>EXCLUIR</th></tr>");

				for (Produto e : lista) {
					out.print("<tr><td>" + e.getSku() + "</td><td>" + e.getProduto() + "</td><td>" + e.getDesc()
							+ "</td><td>" + e.getQtd() + "</td><td>" + e.getPreco()
							+ "</td><td><a href='ProdutoController?sku=" + e.getSku()
							+ "&action=Excluir'><img src='https://img.icons8.com/material/24/000000/delete-trash.png'/></a></td></tr>");
				}
				out.print("</table></br><br/><a href='index.html'>Voltar</a></body></html>");
				request.getRequestURI();
				out.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}
}
