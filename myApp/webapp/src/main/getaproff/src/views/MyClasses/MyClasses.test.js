import React from 'react';
import ReactDOM from 'react-dom';
import MyClasses from './MyClasses';

it('It should mount', () => {
  const div = document.createElement('div');
  ReactDOM.render(<MyClasses />, div);
  ReactDOM.unmountComponentAtNode(div);
});