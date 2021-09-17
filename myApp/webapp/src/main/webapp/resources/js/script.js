
const resetFilters = () => {
    let url = window.location.href;
    console.log(url);
    console.log(location.search);
    url = url.substr(0, url.indexOf('&'));
    window.location.href = url;
}


const keepPriceButtonFocused = () => {
    document.getElementById('priceDropdownButton').focus();
}

const showFilterButton = () => {
    document.getElementById('filter-button').style.display = 'block';

}

const updateLevel = (value) => {
    if(value === "1")
        value = 'Primario';
    else if (value === "2")
        value = 'Secundario';
    else
        value = 'Universitario';
    document.getElementById('levelDropdownButton').firstChild.data = 'Nivel: ' + value;

}

const elem = document.getElementsByTagName('input');
    for( i = 0 ; i < elem.length ; i++)
        elem[i].addEventListener('change', showFilterButton);


const updatePrice  = (price, defaultValue) => {
    if(price === null)
        price = defaultValue;
    console.log(price);
    document.getElementById('priceDisplay').innerHTML = 'Precio: $' + price;
    document.getElementById('priceDropdownButton').firstChild.data = 'Precio: $' + price;
}

const checkFilters = () => {
    const urlParams = new URLSearchParams(location.search);
    if(urlParams.get('price') !== null) {
        updatePrice(urlParams.get('price'),1);
    }
    if(urlParams.get('level') !== null) {
        updateLevel(urlParams.get('level'));
    }
    if(urlParams.get('price') !== null || urlParams.get('level') !== null) {
        document.getElementById('clear-filter-button').style.display= "block";
    }
}
window.onload = checkFilters;



