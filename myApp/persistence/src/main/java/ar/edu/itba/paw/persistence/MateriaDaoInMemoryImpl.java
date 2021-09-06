package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.MateriaDao;
import ar.edu.itba.paw.models.Materia;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MateriaDaoInMemoryImpl implements MateriaDao {
// Mock of a db. Just for show :)
    private final Map<Integer, Materia> materias = new ConcurrentHashMap<Integer, Materia>();

    public MateriaDaoInMemoryImpl() {
        Materia materia = new Materia("Matematica 1", 1);
        materias.put(1,materia);
        Materia materia2 = new Materia("Matematica 2", 2);
        materias.put(2,materia2);
        Materia materia3 = new Materia("Fisica 1", 3);
        materias.put(3,materia3);
        Materia materia4 = new Materia("Quimica", 4);
        materias.put(4,materia4);
        Materia materia5 = new Materia("Algebra", 5);
        materias.put(5,materia5);
        Materia materia6 = new Materia("Arte", 6);
        materias.put(6,materia6);
        Materia materia7 = new Materia("Fisica 2", 7);
        materias.put(7,materia7);
        Materia materia8 = new Materia("Fisica 2", 8);
        materias.put(8,materia8);
        Materia materia9 = new Materia("Fisica 2", 9);
        materias.put(9,materia9);
        Materia materia10 = new Materia("Fisica 2", 10);
        materias.put(10,materia10);
        Materia materia11 = new Materia("Fisica 2", 11);
        materias.put(11,materia11);
        Materia materia12 = new Materia("Fisica 2", 12);
        materias.put(12,materia12);
        Materia materia13 = new Materia("Fisica 2", 13);
        materias.put(13,materia13);
        Materia materia14 = new Materia("Fisica 2", 14);
        materias.put(14,materia14);
    }
    public Materia get(int id) {
        return materias.get(id);
    }
    public List<Materia> list() {
        return new ArrayList<>(this.materias.values());
    }
    public Materia save(Materia materia){
        return this.materias.put(materia.getId(), materia);
    }

    @Override
    public Materia create(String materiaName) {
        return null;
    }

    ;
}
