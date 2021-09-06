package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Materia;

import java.util.List;

public interface MateriaDao {
    Materia get(int id);
    List<Materia> list();
    Materia save(Materia materia);

    Materia create (String materiaName);
}
