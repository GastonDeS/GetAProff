package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.LectureDao;
import ar.edu.itba.paw.models.Lecture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LectureServiceImplTest {

    private static final String USERNAME = "John Doe";
    private static final String DESCRIPTION = "soy un muy buen profesor de la facultad ITBA";
    private static final Long USER_ID_ONE = 1L;
    private static final Long USER_ID_TWO = 2L;
    private static final String SUBJECT_ONE = "MATE 1";
    private static final String SUBJECT_TWO = "MATE 2";
    private static final Long SUBJECT_ID_ONE = 1L;
    private static final Long SUBJECT_ID_TWO = 2L;
    private static final Integer PRICE = 500;
    private static final Integer LEVEL = 3;
    private static final Integer MAXPRICE = 1400;
    private static final Integer MINPRICE = 550;
    private static final float RATE = 3.99f ;
    private static final Integer ANY_LEVEL = 0, ANY_RATING = 0, RAND_ORDER = 0, MAX_LEVEL = 3, GET_ALL = 0, OVER_PRICE = 1500;

    @InjectMocks
    private LectureServiceImpl lectureService = new LectureServiceImpl();

    @Mock
    private LectureDao mockDao;

    @Test
    public void testFindClassesByStudentAndStatus() {
//        final List<Lecture> lectureList = new ArrayList<>();
//        lectureList.add(new Lecture())
    }
}
