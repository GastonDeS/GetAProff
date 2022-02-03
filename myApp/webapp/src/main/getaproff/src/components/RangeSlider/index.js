import React, {useRef} from "react";
import {RangeContainer, RangeTagContainer} from "./RangeSlider.styles";
import PropTypes from "prop-types";

const RangeSlider = ({value, maxValue,name, onChange}) => {

    const range = useRef();
    const rangeV = useRef();

    const handleInput = (e) => {
        onChange((e) => {
            const
                newValue = Number((range.current.value - range.current.min) * 100 / (range.current.max - range.current.min)),
                newPosition = 10 - (newValue * 0.2);
            rangeV.current.innerHTML = `<span>$${range.current.value}</span>`;
            rangeV.current.style.left = `calc(${newValue}% + (${newPosition}px))`;
        });
    }
    document.addEventListener("DOMContentLoaded", handleInput);

    return (
        <div style={{display: 'flex', flexDirection: 'column'}}>
        <RangeContainer>
            <RangeTagContainer ref={rangeV}/>
            <input ref ={range} onInput={handleInput} type="range" id="priceRange" min="1" max={maxValue}
                   value={value}
                   name={name}/>
        </RangeContainer>
        </div>
);};

RangeSlider.propTypes = {
    value: PropTypes.number,
    maxValue: PropTypes.number,
    name: PropTypes.string,
    onChange: PropTypes.func
};

export default RangeSlider;