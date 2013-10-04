package ar.gob.onti.ventanilla.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.gob.onti.ventanilla.dao.FirmanteDAO;
import ar.gob.onti.ventanilla.model.Firmante;

@Repository
public class FirmanteDAOImpl implements FirmanteDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Firmante getFirmanteByPK(Integer idFirmante) {
		Firmante firmante = null;
		List<Firmante> firmanteList = new ArrayList<Firmante>();
		
		firmanteList = sessionFactory.getCurrentSession()
		.createQuery("select f from Firmante f where f.idFirmante = :idFirmante ")
		.setParameter("idFirmante", idFirmante)
		.list();
		
		if (firmanteList != null && firmanteList.size() > 0){
			firmante = firmanteList.get(0);
		}
		return firmante;
	}

}
