package br.com.k19.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(servletNames = {"Faces Servlet"})
public class JPAFilter implements Filter {

	private EntityManagerFactory factory;

	@Override
	public void destroy() {
		this.factory.close();

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// CHEGADA
		EntityManager manager = this.factory.createEntityManager();
		request.setAttribute("EntityManager", manager);
		manager.getTransaction().begin();

		// Faces Servlet
		chain.doFilter(request, response);

		// saida

		try {
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
		} finally {
			manager.close();
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.factory = Persistence.createEntityManagerFactory("K19-Futebol-PU");

	}

}
