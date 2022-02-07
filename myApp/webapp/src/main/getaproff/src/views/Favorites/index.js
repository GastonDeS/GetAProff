import Navbar from "../../components/Navbar";
import React from "react";
import {
    Wrapper,
    PageContainer
} from "./Favorites.styles";
import TutorCard from "../../components/TutorCard";

const Favorites = () => {
    const favorites = ["banana"];

    return (<Wrapper>
        <Navbar/>
        <PageContainer>
            { (favorites.length === 0)?
                <h1>No tutors added to favourites yet!</h1>  
            :
                <TutorCard 
                    key="0"
                    name="Gaston"
                    description="Soy el mejor profesor"
                    rating="5"
                    maxPrice="1000"
                    minPrice="2000"
                    // image={item.image}
                />
            }
        </PageContainer>
    </Wrapper>);
}

export default Favorites;