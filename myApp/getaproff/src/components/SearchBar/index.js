import React, { useState } from 'react'
import { Wrapper } from './SearchBar.styles'
import PropTypes from "prop-types";
// Icon
import SearchIcon from '../../assets/img/search_icon.png'

const SearchBar = ({register, name}) => {
  const [state, setState] = useState('');

  return (
    <Wrapper>
        <img src={SearchIcon} alt='search-icon' />
        <input
            {...register(name)}
              type='text'
              placeholder='Search'
              onChange={event => setState(event.currentTarget.value)}
              value={state}
        />
    </Wrapper>
  )
}

SearchBar.propTypes = {
    register : PropTypes.func,
    name: PropTypes.string,
}

SearchBar.defaultProps = {
    register: (x) => x,
    name: ""
}

export default SearchBar