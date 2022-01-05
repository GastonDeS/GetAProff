import React from 'react';
import ReactDOM from 'react-dom';
import NavbarCust from './navbarCust';

it('It should mount', () => {
  const div = document.createElement('div');
  ReactDOM.render(<NavbarCust/>, div);
  ReactDOM.unmountComponentAtNode(div);
});