import React, {useRef} from "react";
import {RangeContainer, RangeTagContainer} from "./RangeSlider.styles";
import PropTypes from "prop-types";

const RangeSlider = ({register, value, minValue, maxValue,name, ...rest}) => {

    const rangeV = useRef();

    const handleInput = (e) => {
        const newValue = Number((e.target.value - minValue) * 100 / (maxValue - minValue)), newPosition = 10 - (newValue * 0.2);
            rangeV.current.innerHTML = `<span>$${e.target.value}</span>`;
            rangeV.current.style.left = `calc(${newValue}% + (${newPosition}px))`;
    }

    return (
        <div style={{display: 'flex', flexDirection: 'column'}}>
        <RangeContainer>
            <RangeTagContainer ref={rangeV}/>
            <input type="range" id="priceRange" min="1" max={maxValue}
                   {...register(name, {
                       onChange: handleInput,
                   })}
                   />
        </RangeContainer>
        </div>
);};

RangeSlider.propTypes = {
    register: PropTypes.func,
    value: PropTypes.number,
    maxValue: PropTypes.number,
    name: PropTypes.string,
    getValues: PropTypes.func,

};

export default RangeSlider;