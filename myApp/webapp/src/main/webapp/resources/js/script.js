//Tutors js

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
for(let i = 0 ; i < elem.length ; i++)
    if(elem[i].id !== 'query')
        elem[i].addEventListener('change', showFilterButton);


const updateLevel = (value) => {
    var names = ["Todos","Primario", "Secundario", "Universitario"];
    document.getElementById('levelDropdownButton').firstChild.data = 'Nivel: ' + names[parseInt(value)];

}



const updatePrice  = (price) => {
    document.getElementById('priceDisplay').innerHTML = 'Precio: $' + price;
    document.getElementById('priceDropdownButton').firstChild.data = 'Precio: $' + price;
}

const checkFilters = () => {
    const urlParams = new URLSearchParams(location.search);
    if(urlParams.get('price') !== null) {
        updatePrice(urlParams.get('price'),1);
        document.getElementById('priceRange').value = urlParams.get('price');
    }
    if(urlParams.get('level') !== null) {
        updateLevel(urlParams.get('level'));
        let levelRadioButtons = document.getElementsByName('level');
        for(let button of levelRadioButtons)
            if(button.value === urlParams.get('level'))
                button.checked = true;
    }
    if(urlParams.get('price') !== null || urlParams.get('level') !== null) {
        document.getElementById('clear-filter-button').style.display= "block";
    }
}
window.onload = checkFilters;
