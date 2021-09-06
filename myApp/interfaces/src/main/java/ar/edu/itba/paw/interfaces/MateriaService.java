package ar.edu.itba.paw.interfaces;



import ar.edu.itba.paw.models.Materia;


import java.util.List;

public interface MateriaService {
    Materia findById(int id);
    List<Materia> list();

    Materia create(String materiaName);
}
