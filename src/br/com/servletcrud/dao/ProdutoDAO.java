package br.com.servletcrud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.servletcrud.connection.Conexao;
import br.com.servletcrud.model.Produto;

public class ProdutoDAO {

	private Connection connection;

	public ProdutoDAO() {

		connection = Conexao.getConnection();
	}

	public void salvar(Produto produto) {
		try {

			String sql = "insert into tb_produto(sku, nome, descricao, quantidade, preco) value (?,?,?,?,?)";

			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, produto.getSku());
			insert.setString(2, produto.getProduto());
			insert.setString(3, produto.getDesc());
			insert.setInt(4, produto.getQtd());
			insert.setDouble(5, produto.getPreco());

			insert.execute();
			insert.close();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public List<Produto> listar() throws Exception {
		List<Produto> list = new ArrayList<Produto>();

		String sql = "select * from tb_produto";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			Produto produto = new Produto();
			produto.setSku(resultado.getString("sku"));
			produto.setProduto(resultado.getString("nome"));
			produto.setDesc(resultado.getString("descricao"));
			produto.setQtd(resultado.getInt("quantidade"));
			produto.setPreco(resultado.getDouble("preco"));

			list.add(produto);
		}
		return list;
	}

	public Produto buscar(String sku) throws Exception {
		Produto produto = new Produto();

		String sql = "select * from tb_produto where sku = " + sku;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) {
			produto.setSku(resultado.getString("sku"));
			produto.setProduto(resultado.getString("nome"));
			produto.setDesc(resultado.getString("descricao"));
			produto.setQtd(resultado.getInt("quantidade"));
			produto.setPreco(resultado.getDouble("preco"));

		}
		return produto;
	}

	public void atualizar(Produto prod) {
		try {
			String sql = "update tb_produto set nome = '" + prod.getProduto() + "' , descricao = '" + prod.getDesc()
					+ "' , quantidade = '" + prod.getQtd() + "' , preco = '" + prod.getPreco() + "' where sku = "
					+ prod.getSku();

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public void excluir(String sku) {
		try {
			String sql = "delete from tb_produto where sku = " + sku;

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public boolean existe(String sku) throws SQLException {
		boolean result = false;
		String sql = "SELECT * FROM tb_produto WHERE sku = " + sku;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		if (resultado.next()) {
			result = true;
		}
		statement.close();
		return result;

	}

}
