import React from 'react';
import ReactDOM from 'react-dom';
import ClassCard from './classCard';

it('It should mount', () => {
  const div = document.createElement('div');
  ReactDOM.render(<ClassCard/>, div);
  ReactDOM.unmountComponentAtNode(div);
});