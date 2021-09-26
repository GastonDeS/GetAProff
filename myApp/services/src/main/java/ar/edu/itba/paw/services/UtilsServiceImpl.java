package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UtilsService;
import org.springframework.stereotype.Service;

@Service
public class UtilsServiceImpl implements UtilsService {

    @Override
    public String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
