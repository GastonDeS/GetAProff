//Tutors js
const
    range = document.getElementById('priceRange'),
    rangeV = document.getElementById('rangeV'),
    setValue = ()=>{
        const
            newValue = Number( (range.value - range.min) * 100 / (range.max - range.min) ),
            newPosition = 10 - (newValue * 0.2);
        rangeV.innerHTML = `<span>$${range.value}</span>`;
        rangeV.style.left = `calc(${newValue}% + (${newPosition}px))`;
    };
document.addEventListener("DOMContentLoaded", setValue);
range.addEventListener('input', setValue);


const resetFilters = () => {
    const urlParams = new URLSearchParams(location.search);
    let url = window.location.href;
    console.log(url);
    console.log(location.search);
    url = url.substr(0, url.indexOf('?') + 1);
    url += 'query=' + urlParams.get('query');
    window.location.href = url;
}


const keepPriceButtonFocused = () => {
    document.getElementById('priceDropdownButton').focus();
}

const showFilterButton = () => {
    document.getElementById('filter-button').style.display = 'block';

}

const elem = document.getElementsByTagName('input');
for(let item of elem)
    if(item.id !== 'query')
        item.addEventListener('change', showFilterButton);

document.getElementsByName('order')[0].addEventListener('change',showFilterButton);




const checkFilters = () => {
    const urlParams = new URLSearchParams(location.search);
    if(urlParams.get('order') !== null) {
        let orderSelectItems = document.getElementsByName('order')[0];
        for(let item of orderSelectItems)
            if(item.value === urlParams.get('order'))
                item.selected = true;
    }

    if(urlParams.get('price') !== null) {
        document.getElementById('priceRange').value = urlParams.get('price');
    }
    if(urlParams.get('level') !== null) {
        let levelRadioItems = document.getElementsByName('level');
        for(let item of levelRadioItems)
            if(item.value === urlParams.get('level'))
                item.checked = true;
    }
    if(urlParams.get('rating') !== null) {
        let ratingRadioItems = document.getElementsByName('rating');
        for(let item of ratingRadioItems)
            if(item.value === urlParams.get('rating'))
                item.checked = true;
    }
    if(urlParams.get('price') !== null || urlParams.get('level') !== null) {
        document.getElementById('clear-filter-button').style.display= "block";
    }
}

checkFilters();