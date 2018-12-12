package edu.ncsu.csc.itrust.action;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AuthDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.UltrasoundDAO;

/**
 * Servlet implementation class DisplayServlet
 */
@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UltrasoundDAO ultrasoundDAO;
	private AuthDAO authDAO;

	public void init() throws ServletException {
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 * response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uid = request.getParameter("id");
		InputStream sImage;

		// Check if ID is supplied to the request.
		if (uid == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404. id not supplied
			return;
		}

		try {
			DAOFactory prodDAO = DAOFactory.getProductionInstance();
			ultrasoundDAO = prodDAO.getUltrasoundDAO();
			sImage = ultrasoundDAO.getImageData(Long.parseLong(uid));
			String imageType = ultrasoundDAO.getImageType(Long.parseLong(uid));

			byte[] byteArray = new byte[8090];
			int size = 0;
			response.reset();
			response.setContentType(imageType);

			BufferedOutputStream fos1 = new BufferedOutputStream(response.getOutputStream());
			while ((size = sImage.read(byteArray)) != -1) {
				fos1.write(byteArray);
			}
			fos1.flush();
			fos1.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}