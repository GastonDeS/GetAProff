import styled from 'styled-components';

export const RangeContainer = styled.div `
    margin-top: 20px;
    position: relative;
    input {
    border-color: transparent;
}

input:focus {
    outline: none;
}

input[type="radio"]:checked + label {
    font-weight: bold;
}
input[type=range]:focus::-webkit-slider-runnable-track {
    background: #026670;
}

input[type=range]::-webkit-slider-thumb {
    height: 20px;
    width: 20px;
    border-radius: 50%;
    background: #fff;
    box-shadow: 0 0 4px 0 rgba(0, 0, 0, 1);
    cursor: pointer;
    -webkit-appearance: none;
    margin-top: -8px;
}

input[type=range] {
    -webkit-appearance: none;
    margin: 20px 0;
    width: 100%;
}

input[type=range]:focus {
    outline: none;
}

input[type=range]::-webkit-slider-runnable-track {
    width: 100%;
    height: 4px;
    cursor: pointer;
    /*animate: 0.2s;*/
    background: #026670;
    border-radius: 25px;
}

input[type=range]::-webkit-slider-thumb {
    height: 20px;
    width: 20px;
    border-radius: 50%;
    background: #fff;
    box-shadow: 0 0 4px 0 rgba(0, 0, 0, 1);
    cursor: pointer;
    -webkit-appearance: none;
    margin-top: -8px;
}

`

export const RangeTagContainer = styled.div `
    left: calc(69.8957% + -3.97914px);
    position: absolute;
    top: -50%;
    span {
    width: fit-content;
    height: 24px;
    line-height: 24px;
    text-align: center;
    background: #026670;
    color: #fff;
    font-size: 14px;
    display: block;
    position: absolute;
    left: 50%;
    transform: translate(-50%, 0);
    border-radius: 6px;
    min-width: 40px;

}

 span:before {
    content: "";
    position: absolute;
    width: 0;
    height: 0;
    border-top: 10px solid #026670;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    margin-top: -1px;
}
`