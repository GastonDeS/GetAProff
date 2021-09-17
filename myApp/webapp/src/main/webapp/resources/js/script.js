const keepButtonFocused = () => {
    document.getElementById('priceDropdownButton').focus();
}

const updatePrice  = (price) => {
    document.getElementById('priceDisplay').innerHTML = 'Precio: $' + price;
}

