package ar.gob.onti.ventanilla.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.gob.onti.ventanilla.dao.ParametrizacionDAO;
import ar.gob.onti.ventanilla.model.Parametrizacion;

@Repository
public class ParametrizacionDAOImpl implements ParametrizacionDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Parametrizacion getParametrizacionByPK(Long idParametrizacion) {
		Parametrizacion parametrizacion = null;
		List<Parametrizacion> parametrizacionList = new ArrayList<Parametrizacion>();
		
		parametrizacionList = sessionFactory.getCurrentSession()
		.createQuery("select p from Parametrizacion p where p.idParametrizacion = :idParametrizacion")
		.setParameter("idParametrizacion", idParametrizacion)
		
		.list();
		
		if (parametrizacionList != null && parametrizacionList.size() > 0){
			parametrizacion = parametrizacionList.get(0);
		}
		return parametrizacion;
	}

	
}
