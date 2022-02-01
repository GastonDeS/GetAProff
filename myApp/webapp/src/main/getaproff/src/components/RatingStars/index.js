import React from 'react';
import PropTypes from "prop-types";


const RatingStars = ({markedStars}) => {
    const array = [1,2,3,4,5];
    return (
        <div className="rating-stars">
            {array.map(value => {
                if(value <= markedStars)
                    return <span key={value} style={{color: '#ffa500'}} className="fa fa-star checked"/>;
                else return <span key={value} style={{color: '#ffa500'}} className="fa fa-star-o checked"/>;
            })}

        </div>
);};

RatingStars.propTypes = {
    markedStars: PropTypes.number,
}

export default RatingStars;