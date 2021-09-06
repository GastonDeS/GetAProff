package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.MateriaDao;
import ar.edu.itba.paw.interfaces.MateriaService;
import ar.edu.itba.paw.models.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaDao materiaDao;

    @Override
    public Materia findById(int id) {
        return this.materiaDao.get(id);
    }

    @Override
    public List<Materia> list() {
        return this.materiaDao.list();
    }

    @Override
    public Materia create(String materiaName) {
        return materiaDao.create(materiaName);
    }
}
